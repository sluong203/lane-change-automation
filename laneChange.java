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
    
    /*returns the time until it is safe to change lanes
     * precondition: velocity of current car is constant
     */
    public String timeTillSafe() {
        double precedingBackToCurrentFront = precedingCar.distance - (0.5 * precedingCar.length) - (0.5 * length);
        double currentBackToFollowingFront = followingCar.distance - (0.5 * length) - (0.5 * followingCar.length); 
        
        if(precedingCar.acceleration < 0 && followingCar == null) { // when preceding car is slowing down and there's no following car
            return timePrecedingSlowing(precedingBackToCurrentFront) + " seconds";
        } else if(followingCar.acceleration > 0 && precedingCar == null) { // when following car is speeding up and there's no preceding car
            return timeFollowingSpeedingUp(currentBackToFollowingFront) + " seconds";
        } else {
            return "Unsafe situation. You should go to a different lane."; //ohter cars are gonna crash
        }
    }
    
    /*
     * precondition: velocities of preceding and following cars are constant
     * can return positive or negative acceleration value
     */
    public double accelerationNeeded() {
        double currentFrontToPrecedingBack = (0.5 * length) + (0.5 * precedingCar.length) - precedingCar.distance;
        double followingFrontToCurrentBack = (0.5 * length) + (0.5 * followingCar.length) - followingCar.distance;
        if(followingCar == null && !(precedingCar == null)) {
            return (currentFrontToPrecedingBack - (1.5 * velocity)) / (Math.pow(1.5, 2) * 0.5);
        } else if (precedingCar == null && !(followingCar == null)) {
            return (followingFrontToCurrentBack - (1.5 * velocity)) / (Math.pow(1.5, 2) * 0.5);
        } else if (precedingCar == null && followingCar == null){
            return
        } else {
            
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