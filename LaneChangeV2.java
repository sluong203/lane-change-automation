//1234567890123456789012345001234567890123456789012345001234567890123456789012345001234567890623456789012345
import java.lang.Math;
/**
 * Write a description of class Car here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class LaneChangeV2 {
    private Car precedingCar; //distance should be positive
    private Car followingCar; //distance should be negative
    private double velocity;
    private double length;
    
    public LaneChangeV2(Car precedingCar, Car followingCar, double velocity, double length) {
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
        Car[] cars = carList();
        
    }
    
    //precondition: car is not blocking and is closing in
    

    public Car[] carList() {
        Car[] cars = new Car[2];
        
        int i = 0;
        if(precedingCar != null) {
            cars[i] = precedingCar;
            i++;
        }
        
        if(followingCar != null) {
            cars[i] = followingCar;
            i++;
        }
        
        return cars;
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
    
    public boolean closingIn(Car car) {
        return signsMatch(car.acceleration, car.distance);
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
        if(signsMatch(otherCar.distance, otherCar.acceleration)) {
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
    
    /**
     * Getter method for the car preceding the attempting car in the target lane
     * 
     * @return  the car in the target lane whose distance to the attempting car is positive
     */
    public Car getPrecedingCar() {
        return precedingCar;
    }
    
    /**
     * Getter method for the car following the attempting car in the target lane
     * 
     * @return  the car in the target lane whose distance to the attempting car is negative
     */
    public Car getFollowingCar() {
        return followingCar;
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
