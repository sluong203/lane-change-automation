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
     * returns the factors (time and acceleration) needed for a successful lane change
     */
    public String laneChangeFactorsNeeded() {
        if(precedingCar == null && followingCar == null) {
            return("Time: 0\nAcceleration: 0");
        } else if (precedingCar != null || followingCar != null) {
            Car presentCar;
            if(precedingCar != null) {
                presentCar = precedingCar;
            } else {
                presentCar = followingCar;
            }
            if(carInWay(presentCar).length != 0) { //the present car is in the way
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
                    double displacement = (presentCar.velocity * 1.5) + (0.5 * presentCar.acceleration * 1.5);
                    double distanceBetween = presentCar.distance - (0.5 * length + 0.5 * presentCar.length);
                    if(displacement > 0 && distanceBetween > 0 || displacement < 0 && distanceBetween < 0) {
                        return("Time: 0\nAcceleration: 0");
                    } else {
                        if(displacement + distanceBetween >= 0) {
                            return("Time: 0\nAcceleration: 0");
                        } else {
                            return ("The other car(s) need to change acceleration.");
                        }
                    }
                }
            }
        } else { //both cars are present
            if(carInWay(precedingCar).length == 0 && carInWay(followingCar).length == 0) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        //need to wait for FC to slow down THEN decelerate
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        if (timePC > timeFC) {
                            return("Time: " + timePC + "\nAcceleration: 0");
                        } else {
                            return("Time: " + timeFC + "\nAcceleration: 0");
                        } // return larger time
                        //need to wait for both to be out of way, a = 0;
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: " + accelerationNeeded(followingCar));
                        //need to wait for PC to speed up THEN accelerate
                    } else { //followingCar.acceleration > 0
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    }
                } else { //precedingCar.acceleration < 0
                    if(followingCar.acceleration < 0) {
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                }
            } else if (carInWay(precedingCar).length == 0 || carInWay(followingCar).length == 0) {
                Car carInTheWay = carInWay(precedingCar);
                Car carNotInTheWay = followingCar;
                if(carInTheWay == null) {
                    carInTheWay = carInWay(followingCar);
                    carNotInTheWay = precedingCar;
                }
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        //need to wait for FC to slow down out of the way and also need to decelerate
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                } else { //precedingCar.acceleration < 0
                    if(followingCar.acceleration < 0) {
                        if (followingCar.acceleration <= precedingCar.acceleration) {
                            return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                }
            } else if (carInWay(precedingCar).length == 0 && carInWay(followingCar).length != 0) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        //need to wait for followingCar to be out of the way, a = 0
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        //need to wait for followingCar to be out of the way, a = 0
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: " + accelerationNeeded(followingCar));
                        //need to accelerate past followingCar once precedingCar has accelerated far enough away
                    } else { //followingCar.acceleration > 0
                        if (precedingCar.acceleration >= followingCar.acceleration) {
                            return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    }
                }  else { //precedingCar.acceleration < 0
                    if(followingCar.acceleration < 0) {
                        if (followingCar.acceleration <= precedingCar.acceleration) {
                            return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        } else {
                            return ("The other cars need to change acceleration."); 
                        }
                    } else { //followingCar.acceleration >= 0
                        return ("The other cars need to change acceleration.");
                    }
                }
            } else { //!isInTheWay(precedingCar) && !isInTheWay(followingCar)
                return ("Time: 0\nAcceleration: 0");            
            }
        }
    }
    /**
     * Returns whether the given Car is in the way
     */
    private Car carInWay(Car otherCar) {
        if (0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance) > 0) {
            return otherCar;
        }
        return new Car(0, 0, 0, 0);
    }
    
    /*returns the time until it is safe to change lanes
     * precondition: otherCar is the preceding or following car
     */
    public double timeTillSafe(Car otherCar) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        double frontToBack = 0.5*(otherCar.length + this.length) + Math.abs(otherCar.distance);
        if((otherCar.equals(precedingCar) && otherCar.acceleration > 0) || (otherCar.equals(followingCar) && otherCar.acceleration < 0)) {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, -overlap);
        } else {
            return quadForm (0.5*Math.abs(otherCar.acceleration), otherCar.velocity, -frontToBack);
        }
    }
    
    /*
     * precondition: otherCar is the preceding or following car
     * can return positive or negative acceleration value
     */
    public double accelerationNeeded(Car otherCar) {
        double overlap = 0.5*(otherCar.length + this.length) - Math.abs(otherCar.distance);
        if(otherCar.equals(precedingCar)) {
            return (2*(-overlap - velocity * 1.5)) / (Math.pow(1.5, 2));
        } else {
            return (2*(overlap - velocity * 1.5)) / (Math.pow(1.5, 2));
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
        return "PC: " + precedingCar + ", FC: " + followingCar + ", Velocity: " + velocity +", Length: " + length;
    }
}