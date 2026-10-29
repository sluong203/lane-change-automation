import java.util.ArrayList;

public class ScenarioBuilder {
    public static ArrayList<Scenario> buildScenarios() {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
        for (int i = 0; i < 10; i++) {
            int numCars = 2;
            ArrayList<Car> cars = new ArrayList<Car>();
            Car attemptingCar = new Car(0, (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
            cars.add(attemptingCar);
            if(numCars == 1) {
                Car newCar = new Car((int) (Math.random() * 8) - 8, (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
                cars.add(newCar);
            } else {
                Car precedingCar = new Car((int) (Math.random() * 8) + 1, (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
                cars.add(precedingCar);
                Car followingCar = new Car(-((int) (Math.random() * 8) + 1), (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
                cars.add(followingCar);
            }
            scenarios.add(new Scenario(cars));
        }

        return scenarios;
    }
}
