import java.util.ArrayList;

public class Scenario {
    private ArrayList<Car> cars; // first car is the one that is changing lane, must have at least 1 car, second car should be PC
    private int accelerationSignPC; // 1 for positive, -1 for negative, 0 for 0
    private int accelerationSignFC;
    private boolean inTheWay;
    private int targetLaneNumCars;

    public Scenario(ArrayList<Car> cars) {
        this.cars = cars;
        targetLaneNumCars = cars.size() - 1;

        if(cars.get(1).getAcceleration() == 0) {
            accelerationSignPC = 0;
        } else {
            accelerationSignPC = (int) (cars.get(1).getAcceleration() / Math.abs(cars.get(1).getAcceleration()));
        }

        if(cars.get(1).getAcceleration() == 0) {
            accelerationSignFC = 0;
        } else {
            accelerationSignFC = (int) (cars.get(1).getAcceleration() / Math.abs(cars.get(1).getAcceleration()));
        }

        inTheWay = false;
    }

    public ArrayList<SimulatorCar> getCars() {
        ArrayList<SimulatorCar> simulatorCars = new ArrayList<SimulatorCar>();
        Vector2D positionAC = new Vector2D(300, 0);
        Vector2D velocityAC = new Vector2D(0, cars.get(0).getVelocity() * 20);
        Vector2D accelerationAC = new Vector2D(0, cars.get(0).getAcceleration() * 20);
        double lengthAC = cars.get(0).getLength() * 20;
        SimulatorCar attemptingCar = new SimulatorCar(positionAC, velocityAC, accelerationAC, lengthAC);
        simulatorCars.add(attemptingCar);

        for (int i = 1; i <= cars.size() - 1; i++) {
            Vector2D position = new Vector2D(500, 20 * (positionAC.getY() + cars.get(i).getDistance()));
            Vector2D velocity = new Vector2D(0, 20 * (cars.get(i).getVelocity()));
            Vector2D acceleration = new Vector2D(0, 20 * (cars.get(i).getAcceleration()));
            double length = 20 * cars.get(i).getLength();
            SimulatorCar car = new SimulatorCar(position, velocity, acceleration, length);
            simulatorCars.add(car);
        }

        return simulatorCars;

    }
}
