import java.util.ArrayList;

public class SimulatorCar {
    private Vector2D position; // measured in centimeters
    private Vector2D velocity; // measured in centimeters per second
    private Vector2D acceleration; //measured in centimeters per second squared
    private double length; //measured in meters
    private double width;

    public SimulatorCar(Vector2D position, Vector2D velocity, Vector2D acceleration, double length) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.length = length;
        width = length / 2.0;
    }

    public void update(double time) {
        double positionX = position.getX() + (velocity.getX() * time) + (0.5 * Math.pow(acceleration.getX(), 2) * time);
        double positionY = position.getY() + (velocity.getY() * time) + (0.5 * Math.pow(acceleration.getY(), 2) * time);
        double velocityX = velocity.getX() + (acceleration.getX() * time);
        double velocityY = velocity.getY() + (acceleration.getY() * time);
        velocity.setX(velocityX);
        velocity.setY(velocityY);
        position.setX(positionX);
        position.setY(positionY);

    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public Vector2D getAcceleration() {
        return acceleration;
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public String toString() {
        return "Position: " + position + ", Velocity: " + velocity + ", Acceleration: " + acceleration + ", Length: " + length;
    }


}
