import java.lang.Math;
/**
 * Write a description of class Car here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class laneChange {
    private Car precedingCar; //distance should be negative
    private Car followingCar; //distance should be positive
    private double velocity;
    private double length;
    
    public laneChange(Car precedingCar, Car followingCar, double velocity, double length) {
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
        } else if (precedingCar != null && followingCar == null) {
            if(isInTheWay(precedingCar)) { //the preceding car is in the way
                if(precedingCar.acceleration == 0) {
                    return("Acceleration: " + accelerationNeeded(precedingCar));
                    //need to decelerate
                } else if (precedingCar.acceleration > 0) {
                    return("Time: 0\nAcceleration: 0");
                    //can change lanes whenever
                } else {
                    return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                    //need to wait for preceding car clears current car
                }
            } else { // the preceding car is not in the way
                if (precedingCar.acceleration >= 0) {
                    return ("Time: 0\nAcceleration: 0");
                    //can change lanes whenever
                } else {
                    return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                }
            }
        } else if (precedingCar == null && followingCar != null) {
            if(isInTheWay(followingCar)) {
                if(followingCar.acceleration == 0) {
                    return("Acceleration: " + accelerationNeeded(followingCar));
                } else if (followingCar.acceleration > 0) {
                    return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                } else {
                    return ("Time: 0\nAcceleration: 0");
                }
            } else {
                if (followingCar.acceleration <= 0) {
                    return ("Time: 0\nAcceleration: 0");
                } else {
                    return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                }
            }
        } else {
            if(followingCar.acceleration > 0 || precedingCar.acceleration < 0) {
                return ("The other cars need to change acceleration.");
            }
            if(isInTheWay(precedingCar) && isInTheWay(followingCar)) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        //need to wait for FC to slow down and also need to decelerate
                    } else if (followingCar.acceleration == 0){
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        double timeFC = timeTillSafe(followingCar);
                        double timePC = timeTillSafe(precedingCar);
                        double largerTime = timeFC;
                        if (timePC > timeFC) {
                            largerTime = timePC;
                        }
                        return("Time: " + largerTime + "\nAcceleration: 0");
                        //need to wait for both to be out of way, a = 0;
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: " + accelerationNeeded(followingCar));
                        //need to wait for PC to speed up and also need to accelerate 
                    }
                }
            } else if (isInTheWay(precedingCar) && !isInTheWay(followingCar)) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: " + accelerationNeeded(precedingCar));
                        //need to wait for FC to slow down out of the way and also need to decelerate
                    } else if (followingCar.acceleration == 0){
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        //need to wait for precedingCar to be out of the way, a = 0
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: 0");
                        //need to wait for precedingCar to be out of the way, a = 0
                    }
                }                
            } else if (!isInTheWay(precedingCar) && isInTheWay(followingCar)) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        //need to wait for followingCar to be out of the way, a = 0
                    } else if (followingCar.acceleration == 0){
                        return ("The other cars need to change acceleration.");
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        return("Time: " + timeTillSafe(followingCar) + "\nAcceleration: 0");
                        //need to wait for followingCar to be out of the way, a = 0
                    } else if (followingCar.acceleration == 0) {
                        return("Time: " + timeTillSafe(precedingCar) + "\nAcceleration: " + accelerationNeeded(followingCar));
                        //need to accelerate past followingCar once precedingCar has accelerated far enough away
                    }
                }                
            } else if (!isInTheWay(precedingCar) && !isInTheWay(followingCar)) {
                if(precedingCar.acceleration == 0) {
                    if(followingCar.acceleration < 0) {
                        return ("Time: 0\nAcceleration: 0");
                        //can change lanes instantly, a = 0
                    } else if (followingCar.acceleration == 0){
                        return ("Time: 0\nAcceleration: 0");
                        //can change lanes instantly, a = 0
                    }
                } else if (precedingCar.acceleration > 0) {
                    if(followingCar.acceleration < 0) {
                        return ("Time: 0\nAcceleration: 0");
                        //can change lanes instantly, a = 0
                    } else if (followingCar.acceleration == 0) {
                        return ("Time: 0\nAcceleration: 0");
                        //can change lanes instantly, a = 0
                    }
                }                
            }
        }
    }
    /**
     * Returns whether the given Car is in the way
     */
    private boolean isInTheWay(Car car) {
        return car.distance < ((0.5 * car.length) + (0.5 * length));
    }
    /*returns the time until it is safe to change lanes
     * precondition: otherCar is the preceding or following car
     */
    public double timeTillSafe(Car otherCar) {
        if(otherCar.equals(precedingCar)) {
            double precedingBackToCurrentFront = precedingCar.distance - (0.5 * precedingCar.length) - (0.5 * length);
            return timePrecedingSlowing(precedingBackToCurrentFront);
        } else if (otherCar.equals(followingCar)) {
            double currentBackToFollowingFront = followingCar.distance - (0.5 * length) - (0.5 * followingCar.length); 
            return timeFollowingSpeedingUp(currentBackToFollowingFront);
        }
    }
    
    /*
     * precondition: otherCar is the preceding or following car
     * can return positive or negative acceleration value
     */
    public double accelerationNeeded(Car otherCar) {
        if(otherCar.equals(precedingCar)) {
            double currentFrontToPrecedingBack = (0.5 * length) + (0.5 * precedingCar.length) - precedingCar.distance;
            return (currentFrontToPrecedingBack - (1.5 * velocity)) / (Math.pow(1.5, 2) * 0.5);
        } else if (otherCar.equals(followingCar)){
            double followingFrontToCurrentBack = (0.5 * length) + (0.5 * followingCar.length) - followingCar.distance;
            return (followingFrontToCurrentBack - (1.5 * velocity)) / (Math.pow(1.5, 2) * 0.5);
        }
    }
    
    
    //calculate time till safe when the preceding car is slowing down
    public double timePrecedingSlowing(double precedingBackToCurrentFront) {
        double distanceToTravel = -(precedingBackToCurrentFront + length + precedingCar.length); //negative distance because traveling backwards!
        return quadForm(0.5 * precedingCar.acceleration, precedingCar.velocity, distanceToTravel);
    }
    
    public double timeFollowingSpeedingUp(double currentBackToFollowingFront) {
        double distanceToTravel = currentBackToFollowingFront + length + followingCar.length;
        return quadForm(0.5 * precedingCar.acceleration, precedingCar.velocity, distanceToTravel);
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
    
    private class Car
    {
        // instance variables - replace the example below with your own
        private double distance; //distance from middle of this.car to middle of current car
        private double velocity;
        private double acceleration;
        private double length;
    
        /**
         * Constructor for objects of class Car
         */
        public Car(double distance, double velocity, double acceleration, double length) {
            // initialise instance variables
            this.distance = distance;
            this.velocity = velocity;
            this.acceleration = acceleration;
            this.length = length;
        }
        
        public double getDistance() {
            return distance;
        }
    
        public double getVelocity() {
            return velocity;
        }
        
        public double getAcceleration() {
            return acceleration;
        }
        
        public void getLength() {
            return length;
        }
    }
}