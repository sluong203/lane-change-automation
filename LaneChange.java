//1234567890123456789012345001234567890123456789012345001234567890123456789012345001234567890623456789012345
import java.lang.Math;
/**
 * Models a free-flowing highway lane change scenario
 *
 * @author Sofia Luong
 * @version February 2026
 */
public class LaneChange {
    private Car precedingCar; //distance should be positive
    private Car followingCar; //distance should be negative
    private double velocity;
    private double length;
    
    public LaneChange(Car precedingCar, Car followingCar, double velocity, double length) {
        this.precedingCar = precedingCar;
        this.followingCar = followingCar;
        this.velocity = velocity;
        this.length = length;
    }

    public LaneChange(double velocity, double length) {
        this.precedingCar = null;
        this.followingCar = null;
        this.velocity = velocity;
        this.length = length;
    }
    
    /**
     * Outputs the factors needed for a safe and successful lane change
     * 
     * Precondition: there are no relevant cars ahead or behind the attempting car (this.car) in its lane
     * 
     * @return  a String containing the acceleration (m/s^2) and amount of time (s) needed and before 
     * changing lanes
     *
     * if the class returns null, then the lane change is impossible with the current conditions
     */
    public FactorSet getFactorsNeeded() {
        if(precedingCar == null && followingCar == null) {
            FactorSet factorsNeeded = new FactorSet(0,0.0);
            return factorsNeeded;
        } else if (precedingCar != null || followingCar != null) {
            Car presentCar;
            if(precedingCar != null) {
                presentCar = precedingCar;
            } else {
                presentCar = followingCar;
            }
            if(carInWay(presentCar)) { //the present car is in the way
                if(presentCar.acceleration == 0) {
                    FactorSet factorsNeeded = new FactorSet(0.0,accelerationNeeded(presentCar, 6));
                    return factorsNeeded;
                } else {
                    FactorSet factorsNeeded = new FactorSet(timeTillSafe(presentCar),0.0);
                    return factorsNeeded;
                    //need to wait for preceding car clears current car
                }
            } else { // the present car is not in the way
                if(closingIn(presentCar)) {
                    if(staysOutOfWay(presentCar, this.velocity * 3, 6)) {
                        FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                        return factorsNeeded;
                    } else {
                        FactorSet factorsNeeded = new FactorSet();
                        return factorsNeeded;
                    }
                } else {
                    FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                    return factorsNeeded;
                }
            }
        } else { //both cars are present
            if (carInWay(precedingCar) && carInWay(followingCar)) { //both cars are in the way
                if (precedingCar.acceleration == 0 && followingCar.acceleration == 0) {
                    FactorSet factorsNeeded = new FactorSet();
                    return factorsNeeded;
                } else if (precedingCar.acceleration == 0 || followingCar.acceleration == 0) {
                    Car acceleratingCar;
                    Car nonAcceleratingCar;
                    if(precedingCar.acceleration != 0) {
                        acceleratingCar = precedingCar;
                        nonAcceleratingCar = followingCar;
                    } else {
                        acceleratingCar = followingCar;
                        nonAcceleratingCar = precedingCar;
                    }
                    if(!closingIn(acceleratingCar)){
                        FactorSet factorsNeeded = new FactorSet(timeTillSafe(acceleratingCar),accelerationNeeded(nonAcceleratingCar, timeTillSafe(acceleratingCar)));
                        return factorsNeeded;
                    }
                } else {
                    if(!closingIn(precedingCar) && !closingIn(followingCar)) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        if (timePC > timeFC) {
                            FactorSet factorsNeeded = new FactorSet(timePC,0.0);
                            return factorsNeeded;
                        } else {
                            FactorSet factorsNeeded = new FactorSet(timeFC,0.0);
                            return factorsNeeded;
                        }
                    } else if(closingIn(precedingCar) && !closingIn(followingCar)) {
                        FactorSet factorsNeeded = new FactorSet(timeTillSafe(precedingCar),0.0);
                        return factorsNeeded;
                    } else if (!closingIn(precedingCar) && closingIn(followingCar)) {
                        FactorSet factorsNeeded = new FactorSet(timeTillSafe(followingCar),0.0);
                        return factorsNeeded;
                    }
                }
            } else if(carInWay(precedingCar) || carInWay(followingCar)) { // one car is in the way
                Car blockingCar = precedingCar; 
                Car nonBlockingCar = followingCar;
                if (!carInWay(precedingCar)) {
                    blockingCar = followingCar; 
                    nonBlockingCar = precedingCar;
                }
                double gap = Math.abs(nonBlockingCar.distance) - 
                             (0.5 * length + 0.5 * nonBlockingCar.length);
                double overlap = (0.5 * (blockingCar.length + this.length)) - 
                                 Math.abs(blockingCar.distance);
                if(blockingCar.acceleration == 0 && nonBlockingCar.acceleration == 0) {
                    if(gap >= overlap + this.velocity * 3) {
                        FactorSet factorsNeeded = new FactorSet(0.0,accelerationNeeded(blockingCar, 6));
                        return factorsNeeded;
                    }
                } else if (blockingCar.acceleration != 0 && nonBlockingCar.acceleration == 0) {
                    if(!closingIn(blockingCar)) {
                        FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                        return factorsNeeded;
                    } else {
                        if(gap >= blockingCar.length + 2 * (this.velocity * 3)) {
                            FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                            return factorsNeeded;
                        }
                    }
                } else if (blockingCar.acceleration == 0 && nonBlockingCar.acceleration != 0) {
                    if(staysOutOfWay(nonBlockingCar, overlap + this.velocity * 3, 6)) {
                        FactorSet factorsNeeded = new FactorSet(0.0,accelerationNeeded(blockingCar, 6));
                        return factorsNeeded;
                    }
                } else {
                    if(signsMatch(blockingCar.acceleration, nonBlockingCar.acceleration)) {
                        if(!closingIn(nonBlockingCar)) {
                            FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                            return factorsNeeded;
                        } else {
                            if(staysOutOfWay(nonBlockingCar, this.velocity * 3, timeTillSafe(blockingCar))) {
                                FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                                return factorsNeeded;
                            }
                        }
                    } else {
                        if(!closingIn(nonBlockingCar)) {
                            FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                            return factorsNeeded;
                        } else {
                            if(staysOutOfWay(nonBlockingCar, blockingCar.length + 2 * (this.velocity * 3), 
                                             timeTillSafe(blockingCar))) {
                                FactorSet factorsNeeded = new FactorSet(timeTillSafe(blockingCar),0.0);
                                return factorsNeeded;
                            }
                        }
                    }
                }
            } else {
                if(precedingCar.acceleration == 0 && followingCar.acceleration == 0) {
                    FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                    return factorsNeeded;
                } else if (precedingCar.acceleration == 0 || followingCar.acceleration == 0) {
                    Car acceleratingCar;
                    Car nonAcceleratingCar;
                    if(precedingCar.acceleration != 0) {
                        acceleratingCar = precedingCar;
                        nonAcceleratingCar = followingCar;
                    } else {
                        acceleratingCar = followingCar;
                        nonAcceleratingCar = precedingCar;
                    }
                    if(!closingIn(acceleratingCar)) {
                        FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                        return factorsNeeded;
                    } else {
                        if(staysOutOfWay(acceleratingCar, this.velocity * 3, 6)) {
                            FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                            return factorsNeeded;
                        }
                    }
                } else {
                    if(!closingIn(precedingCar) && !closingIn(followingCar)) {
                        FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                        return factorsNeeded;
                    } else if (closingIn(precedingCar) && closingIn(followingCar)) {
                        if(staysOutOfWay(followingCar, this.velocity * 3, 6) && 
                           staysOutOfWay(precedingCar, this.velocity * 3, 6)) {
                            FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                            return factorsNeeded;
                        }
                    } else {
                        if(staysOutOfWay(followingCar, this.velocity * 3, 6)) {
                            FactorSet factorsNeeded = new FactorSet(0.0,0.0);
                            return factorsNeeded;
                        }
                    }
                }
            }
        }
        
        FactorSet factorsNeeded = new FactorSet();
        return factorsNeeded;
    }
    
    /**
     * Determines whether a given car is in the way of the attempting car in the target lane
     * 
     * @param  otherCar  the car whose position is to be determined
     * @return  true if the car is in the way and false if otherwise
     */
    public boolean carInWay(Car otherCar) {
        return Math.abs(otherCar.distance) < 
               (this.velocity * 3) + (0.5 * (this.length + otherCar.length)); 
    }
    
    public boolean staysOutOfWay(Car car, double distanceNeeded, double time) {
        double displacementCC = this.velocity * time;
        double displacementNBC = (0.5 * car.acceleration * Math.pow(time, 2)) + (car.velocity * time);
        double newGap = 0;
        
        if(car.equals(precedingCar)) {
            newGap = displacementNBC - displacementCC;
        } else {
            newGap = displacementCC - displacementNBC;
        }
        
        return newGap >= distanceNeeded;
    }
    
    /**
     * Determines if the signs of two given values match or both values are 0
     * 
     * @param  valueOne  the first value to be compared
     * @param  valueTwo  the second value to be compared
     * @return  true if the signs match and false if otherwise
     */
    public boolean signsMatch(double valueOne, double valueTwo) {
        return (valueOne > 0 && valueTwo > 0) || 
               (valueOne < 0 && valueTwo < 0 ) || 
                valueOne == valueTwo;
    }
    
    /**
     * Determineds whether the given car is closing in (the gap is decreasing or the overlap is increasing)
     * 
     * @return  true if the car is closing in and false if otherwise
     */
    public boolean closingIn(Car car) {
        return !signsMatch(car.acceleration, car.distance);
    }
    
    /**
     * Calculates how long in seconds it will take for a given car to be out of an attempting 
     * car's way
     * 
     * @param  otherCar  the car in the way of the attempting car in the target lane
     * @return  the time in seconds needed for otherCar to move out of the attempting car's way
     */
    public double timeTillSafe(Car otherCar) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        double frontToBack = 0.5*(otherCar.length + this.length) + Math.abs(otherCar.distance);
        if(closingIn(otherCar)) {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, 
                            -overlap - (this.velocity  * 3));
        } else {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, 
                            -frontToBack - (this.velocity * 3));
        }
    }
    
    /**
     * Calculates the acceleration of the attempting car needed to pass the car blocking it
     * 
     * @param  otherCar  the car in the way of the attempting car in the target lane
     * @return  the acceleration needed for the attempting car to slow down or speed up past otherCar
     */
    public double accelerationNeeded(Car otherCar, double time) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        if(otherCar.equals(precedingCar)) {
            return (2*(-(overlap + velocity * 3) - velocity * time)) / (Math.pow(time, 2));
        } else {
            return (2*((overlap + velocity * 3) - velocity * time)) / (Math.pow(time, 2));
        }
    }
    
    //helper method, returns non-negative solution
    private double quadForm(double a, double b, double c){
        double solutionOne = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        double solutionTwo = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        
        if(solutionOne > solutionTwo) {
            return solutionOne;
        } else {
            return solutionTwo;
        }
    }

    public void setPrecedingCar(Car precedingCar) {
        this.precedingCar = precedingCar;
    }

    public void setFollowingCar(Car followingCar) {
        this.followingCar = followingCar;
    }

    /**
     * Gives the properties of the attempting car and the other relevant cars in the lane change
     * 
     * @return  the properties of the lane change as one string
     */
    public String toString() {
        return "PC: " + precedingCar + ", FC: " + followingCar + 
                ", Velocity: " + velocity +", Length: " + length;
    }
}