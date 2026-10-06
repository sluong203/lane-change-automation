public class SimulatorCar {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration;
    private double length; //measured in meters
    private double width;

    public SimulatorCar(Vector2D position, Vector2D velocity, Vector2D acceleration, double length) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.length = length;
        width = 1.8;
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

    public void update() {
        position.add(velocity);
        velocity.add(acceleration);
    }

}
