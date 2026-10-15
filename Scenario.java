import java.util.ArrayList;

public class Scenario {
    private ArrayList<Car> cars;
    private int accelerationSignFC; // 1 for positive, -1 for negative, 0 for 0
    private int accelerationSignPC;
    private boolean inTheWay;
    private int targetLaneNumCars;

    public Scenario(ArrayList<Car> cars) {
        this.cars = cars;

    }
}
