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
    
    /**
     * Outputs the factors needed for a safe and successful lane change
     * 
     * Precondition: there are no relevant cars ahead or behind the attempting car (this.car) in its lane
     * 
     * @return  a String containing the acceleration (m/s^2) and amount of time (s) needed and before 
     * changing lanes
     */
    public String factorsNeeded() {
        if(precedingCar == null && followingCar == null) {
            return("Time: 0\nAcceleration: 0");
        } else if (precedingCar != null || followingCar != null) {
            Car presentCar;
            if(precedingCar != null) {
                presentCar = precedingCar;
            } else {
                presentCar = followingCar;
            }
            if(carInWay(presentCar)) { //the present car is in the way
                if(presentCar.acceleration == 0) {
                    return("Time: 0\nAcceleration: " + accelerationNeeded(presentCar, 6));
                } else {
                    return("Time: " + timeTillSafe(presentCar) + "\nAcceleration: 0");
                    //need to wait for preceding car clears current car
                }
            } else { // the present car is not in the way
                if(closingIn(presentCar)) {
                    if(staysOutOfWay(presentCar, this.velocity * 3, 6)) {
                        return("Time: 0\nAcceleration: 0");
                    } else {
                        return ("The other car(s) need to change acceleration.");
                    }
                } else {
                   return("Time: 0\nAcceleration: 0"); 
                }
            }
        } else { //both cars are present
            if (carInWay(precedingCar) && carInWay(followingCar)) { //both cars are in the way
                if (precedingCar.acceleration == 0 && followingCar.acceleration == 0) {
                    return ("The other car(s) need to change acceleration.");
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
                        return("Time: " + timeTillSafe(acceleratingCar) + 
                               "\nAcceleration: " + accelerationNeeded(nonAcceleratingCar, 
                                                                       timeTillSafe(acceleratingCar)));
                    }
                } else {
                    if(!closingIn(precedingCar) && !closingIn(followingCar)) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        if (timePC > timeFC) {
                            return("Time: " + timePC + "\nAcceleration: 0");
                        } else {
                            return("Time: " + timeFC + "\nAcceleration: 0");
                        }
                    } else if(closingIn(precedingCar) && !closingIn(followingCar)) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0"); 
                    } else if (!closingIn(precedingCar) && closingIn(followingCar)) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
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
                        return("Time: 0" + "\nAcceleration: " + accelerationNeeded(blockingCar, 6));
                    }
                } else if (blockingCar.acceleration != 0 && nonBlockingCar.acceleration == 0) {
                    if(!closingIn(blockingCar)) {
                        return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                    } else {
                        if(gap >= blockingCar.length + 2 * (this.velocity * 3)) {
                            return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                        }
                    }
                } else if (blockingCar.acceleration == 0 && nonBlockingCar.acceleration != 0) {
                    if(staysOutOfWay(nonBlockingCar, overlap + this.velocity * 3, 6)) {
                        return("Time: 0" + "\nAcceleration: " + accelerationNeeded(blockingCar, 6));
                    }
                } else {
                    if(signsMatch(blockingCar.acceleration, nonBlockingCar.acceleration)) {
                        if(!closingIn(nonBlockingCar)) {
                            return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                        } else {
                            if(staysOutOfWay(nonBlockingCar, this.velocity * 3, timeTillSafe(blockingCar))) {
                                return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                            }
                        }
                    } else {
                        if(!closingIn(nonBlockingCar)) {
                            return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                        } else {
                            if(staysOutOfWay(nonBlockingCar, blockingCar.length + 2 * (this.velocity * 3), 
                                             timeTillSafe(blockingCar))) {
                                return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                            }
                        }
                    }
                }
            } else {
                if(precedingCar.acceleration == 0 && followingCar.acceleration == 0) {
                    return("Time: 0\nAcceleration: 0");
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
                        return("Time: 0\nAcceleration: 0");
                    } else {
                        if(staysOutOfWay(acceleratingCar, this.velocity * 3, 6)) {
                            return("Time: 0\nAcceleration: 0"); 
                        }
                    }
                } else {
                    if(!closingIn(precedingCar) && !closingIn(followingCar)) {
                            return("Time: 0\nAcceleration: 0");
                    } else if (closingIn(precedingCar) && closingIn(followingCar)) {
                        if(staysOutOfWay(followingCar, this.velocity * 3, 6) && 
                           staysOutOfWay(precedingCar, this.velocity * 3, 6)) {
                            return("Time: 0\nAcceleration: 0");
                        }
                    } else {
                        if(staysOutOfWay(followingCar, this.velocity * 3, 6)) {
                            return("Time: 0\nAcceleration: 0");
                        }
                    }
                }
            }
        }
        
        return ("The other car(s) need to change acceleration."); 
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