public class SimulatorCar {
    private Vector2D position;
    private Vector2D velocity;
    private Vector2D acceleration
    private double length;

    public SimulatorCar(double positionY, Vector2D velocity, Vector2D acceleration, double length) {
        position = new Vector2D(50, positionY);
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.length = length;
    }

}
