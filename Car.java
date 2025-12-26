
/**
 * Write a description of class Car here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Car
    {
        // instance variables - replace the example below with your ow
        public double distance; //distance from middle of this.car to middle of current car
        public double velocity;
        public double acceleration;
        public double length;
    
        /**
         * Constructor for objects of class Car
         */
        //give distance = bumper to bumper distance
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
        
        public double getLength() {
            return length;
        }
        
        public String toString() {
            return "Distance: " + distance + ", Velocity: " + velocity + ", Acceleration: " + acceleration + ", Length: " + length;
        }
    }