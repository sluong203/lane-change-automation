//1234567890123456789012345001234567890123456789012345001234567890123456789012345001234567890623456789012345
import java.lang.Math;
/**
 * Write a description of class Car here.
 *
 * @author (your name)
 * @version (a version number or a date)
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
                    return("Acceleration: " + accelerationNeeded(presentCar));
                } else {
                    return("Time: " + timeTillSafe(presentCar) + "\nAcceleration: 0");
                    //need to wait for preceding car clears current car
                }
            } else { // the present car is not in the way
                if(presentCar.acceleration == 0) {
                    return("Time: 0\nAcceleration: 0");
                } else {
                    double displacement = (presentCar.velocity * 6) + (0.5 * presentCar.acceleration * 6);
                    double gap = presentCar.distance - (0.5 * length + 0.5 * presentCar.length);
                    if(signsMatch(displacement, gap)) {
                        return("Time: 0\nAcceleration: 0");
                    } else {
                        if(displacement + gap >= this.velocity * 3) {
                            return("Time: 0\nAcceleration: 0");
                        } else {
                            return ("The other car(s) need to change acceleration.");
                        }
                    }
                }
            }
        } else { //both cars are present
            if (carInWay(precedingCar) && carInWay(followingCar)) {
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
                    if(signsMatch(acceleratingCar.acceleration, acceleratingCar.distance)) {
                        return("Time: " + timeTillSafe(acceleratingCar) + 
                                "\nAcceleration: " + accelerationNeeded(nonAcceleratingCar));
                    } else {
                        return ("The other car(s) need to change acceleration.");
                    }
                } else {
                    if(signsMatch(precedingCar.distance, precedingCar.acceleration) ||
                                signsMatch(followingCar.distance, followingCar.acceleration)) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        if (timePC > timeFC) {
                            return("Time: " + timePC + "\nAcceleration: 0");
                        } else {
                            return("Time: " + timeFC + "\nAcceleration: 0");
                        }
                    } else {
                        return ("The other cars need to change acceleration."); 
                    }
                }
            } else if(carInWay(precedingCar) || carInWay(followingCar)) {
                Car blockingCar = carPositions(precedingCar, followingCar)[0];
                Car nonBlockingCar = carPositions(precedingCar, followingCar)[1];
                double gap = Math.abs(nonBlockingCar.distance) - 
                                (0.5 * length + 0.5 * nonBlockingCar.length);
                double overlap = 0.5*(blockingCar.length + this.length) - Math.abs(blockingCar.distance);
                if(blockingCar.acceleration == 0 && nonBlockingCar.acceleration == 0) {
                    if(gap >= overlap + this.velocity * 3) {
                        return("Time: 0" + "\nAcceleration: " + accelerationNeeded(blockingCar));
                    } else {
                        return ("The other cars need to change acceleration."); 
                    }
                } else if (blockingCar.acceleration != 0 && nonBlockingCar.acceleration == 0) {
                    if(signsMatch(blockingCar.distance, blockingCar.acceleration)) {
                        return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                    } else {
                        if(gap >= blockingCar.length + this.velocity * 3) {
                            return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                        } 
                        return ("The other cars need to change acceleration."); 
                    }
                } else if (blockingCar.acceleration == 0 && nonBlockingCar.acceleration != 0) {
                    if(signsMatch(nonBlockingCar.distance, nonBlockingCar.acceleration)) {
                        return("Time: 0" + "\nAcceleration: " + accelerationNeeded(blockingCar));
                    } else {
                        double displacementCC = this.velocity * 6;
                        double displacement = (nonBlockingCar.velocity * 6) + 
                                                (0.5 * nonBlockingCar.acceleration * 6);
                        double newGap;
                        if(nonBlockingCar.equals(precedingCar)) {
                            newGap = displacement - displacementCC;
                        } else {
                            newGap = displacementCC - displacement;
                        }
                        if(newGap >= overlap + this.velocity * 3) {
                            return("Time: 0" + "\nAcceleration: " + accelerationNeeded(blockingCar));
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    }
                } else {
                    double displacementCC = this.velocity * timeTillSafe(blockingCar);
                    double displacement = (nonBlockingCar.velocity * timeTillSafe(blockingCar)) + 
                                            (0.5 * nonBlockingCar.acceleration * 
                                            timeTillSafe(blockingCar));
                    double newGap;
                    if(nonBlockingCar.equals(precedingCar)) {
                        newGap = displacement - displacementCC;
                    } else {
                        newGap = displacementCC - displacement;
                    }
                    if(signsMatch(blockingCar.acceleration, nonBlockingCar.acceleration)) {
                        if(signsMatch(nonBlockingCar.distance, nonBlockingCar.acceleration)) {
                            if(newGap >= blockingCar.length + this.velocity * 3) {
                                return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration."); 
                            }
                        } else {
                            if(newGap >=  this.velocity * 3) {
                                return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration."); 
                            }
                        }
                    } else {
                        if(!signsMatch(blockingCar.distance, blockingCar.acceleration)) {
                            if(newGap >= blockingCar.length + this.velocity * 3) {
                                return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration."); 
                            }
                        } else {
                            return("Time: " + timeTillSafe(blockingCar) + "\nAcceleration: 0");
                        }
                    }
                }
            } else {
                double gapPC = Math.abs(precedingCar.distance) - (0.5 * length + 0.5 * precedingCar.length);
                double gapFC = Math.abs(followingCar.distance) - (0.5 * length + 0.5 * followingCar.length);
                double displacementPC = (precedingCar.velocity * 6) + (0.5 * precedingCar.acceleration * 6);
                double displacementFC = (followingCar.velocity * 6) + (0.5 * followingCar.acceleration * 6);
                double displacementCC = this.velocity * 6;
                double newGapFC = displacementCC - displacementFC;
                double newGapPC = displacementPC - displacementCC;
                if(precedingCar.acceleration == 0 && followingCar.acceleration == 0) {
                    return("Time: 0\nAcceleration: 0");
                } else if (precedingCar.acceleration == 0 || followingCar.acceleration == 0) {
                    Car acceleratingCar;
                    Car nonAcceleratingCar;
                    double newGapAC;
                    double gapNAC;
                    double gapAC;
                    if(precedingCar.acceleration != 0) {
                        acceleratingCar = precedingCar;
                        nonAcceleratingCar = followingCar;
                        newGapAC = newGapPC;
                        gapAC = gapPC;
                        gapNAC = gapFC;
                    } else {
                        acceleratingCar = followingCar;
                        nonAcceleratingCar = precedingCar;
                        newGapAC = newGapFC;
                        gapAC = gapFC;
                        gapNAC = gapPC;
                    }
                    if(signsMatch(acceleratingCar.acceleration, acceleratingCar.distance)) {
                        return("Time: 0\nAcceleration: 0");
                    } else {
                        if(newGapAC >= this.velocity * 3) {
                            return("Time: 0\nAcceleration: 0");
                        } else {
                            if(gapNAC >= acceleratingCar.length + this.velocity * 3) {
                                double time = quadForm(0.5 * acceleratingCar.acceleration, 
                                                        acceleratingCar.velocity,
                                                        -(gapAC + gapNAC + this.length + 
                                                        this.velocity * 3));
                                return("Time: " + time + "\nAcceleration: 0");
                            } else {
                                return ("The other cars need to change acceleration."); 
                            }
                        }
                    }
                } else {
                    if(signsMatch(precedingCar.distance, precedingCar.acceleration) && 
                        signsMatch(followingCar.distance, followingCar.acceleration)) {
                            return("Time: 0\nAcceleration: 0");
                    } else if (!signsMatch(precedingCar.distance, precedingCar.acceleration) && 
                                !signsMatch(followingCar.distance, followingCar.acceleration)) {
                        if(newGapPC >= this.velocity * 3 && newGapFC >= this.velocity * 3) {
                            return("Time: 0\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    } else {
                        if(precedingCar.acceleration < 0) {
                            double time = quadForm(0.5 * precedingCar.acceleration, 
                                                    precedingCar.velocity,
                                                    -(gapPC + this.length + this.velocity * 3));
                            return("Time: " + time + "\nAcceleration: 0");
                        } else {
                            double time = quadForm(0.5 * followingCar.acceleration, 
                                                    followingCar.velocity,
                                                    -(gapFC + this.length + this.velocity * 3));
                            return("Time: " + time + "\nAcceleration: 0");
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Determines whether a given car is in the way of the attempting car in the target lane
     * 
     * @param  otherCar  the car whose position is to be determined
     * @return  true if the car is in the way and false if otherwise
     */
    public boolean carInWay(Car otherCar) {
        return Math.abs(otherCar.distance) < (this.velocity * 3) + (0.5 * (this.length + otherCar.length)); 
    }
    
    /**
     * Gives an array where the car in the way is at the 0th index and 
     * the car not in the way is at the 1st index
     * 
     * Precondition: only one of the cars is in the way
     * 
     * @param  precedingCar  the car in the target lane that is ahead of the attempting car
     * @param  followingCar  the car in the target lane that is behind of the attempting car
     * @return  an array whose indices indicate which car is in the way
     */
    public Car[] carPositions(Car precedingCar, Car followingCar) {
        Car[] array = new Car[2];
        if (0.5*(precedingCar.length + this.length) - Math.abs(precedingCar.distance) > 0) {
            array[0] = precedingCar;
            array[1] = followingCar;
        } else {
            array[0] = followingCar;
            array[1] = precedingCar;
        }
        return array;
    }
    
    /**
     * Determines if the signs of two given values match or both values are 0
     * 
     * @param  valueOne  the first value to be compared
     * @param  valueTwo  the second value to be compared
     * @return  true if the signs match and false if otherwise
     */
    public boolean signsMatch(double valueOne, double valueTwo) {
        return (valueOne > 0 && valueTwo > 0) || (valueOne < 0 && valueTwo < 0 ) || valueOne == valueTwo;
    }
    
    /*returns the time until it is safe to change lanes
     * precondition: otherCar is the preceding or following car
     */
    /**
     * Calculates how long in seconds it will take for a given car to be out of an attempting car's way
     * 
     * @param  otherCar  the car in the way of the attempting car in the target lane
     * @return  
     */
    public double timeTillSafe(Car otherCar) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        double frontToBack = 0.5*(otherCar.length + this.length) + Math.abs(otherCar.distance);
        if(signsMatch(otherCar.distance, otherCar.acceleration)) {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, 
                            -overlap - (this.velocity  * 3));
        } else {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, 
                            -frontToBack - (this.velocity * 3));
        }
    }
    
    /*
     * precondition: otherCar is the preceding or following car
     * can return positive or negative acceleration value
     */
    public double accelerationNeeded(Car otherCar) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        if(otherCar.equals(precedingCar)) {
            return (2*(-(overlap + velocity * 3) - velocity * 6)) / (Math.pow(6, 2));
        } else {
            return (2*((overlap + velocity * 3) - velocity * 6)) / (Math.pow(6, 2));
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
    
    public Car getPrecedingCar() {
        return precedingCar;
    }
    
    public Car getFollowingCar() {
        return followingCar;
    }
    
    public String toString() {
        return "PC: " + precedingCar + ", FC: " + followingCar + 
                ", Velocity: " + velocity +", Length: " + length;
    }
}