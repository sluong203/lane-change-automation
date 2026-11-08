import java.util.ArrayList;

public class ScenarioBuilder {
    public static ArrayList<Scenario> buildScenarios() {
        ArrayList<Scenario> scenarios = new ArrayList<Scenario>();
        for (int i = 0; i < 10; i++) {
            int numCars = 2;
            ArrayList<Car> cars = new ArrayList<Car>();
            Car attemptingCar = new Car(0, (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
            cars.add(attemptingCar);
            double displayTime = quadForm(.5 * attemptingCar.getAcceleration(), attemptingCar.getVelocity(), -50);
            String test = "lalalalalalalalalalaaaa";
            if(numCars == 1) {
                Car newCar = new Car((int) (Math.random() * 8) - 8, (int) (Math.random() * 36) + 25, (int) (Math.random() * 5) - 5, 5);
                cars.add(newCar);
                scenarios.add(new Scenario(cars));
            } else {
                int precedingCarDistance = (int) (Math.random() * 8) + 1;
                int precedingCarVelocity = (int) (Math.random() * 36) + 25;
                int precedingCarAcceleration = (int) (Math.random() * 5) - 5;
                Car precedingCar = new Car(precedingCarDistance, precedingCarVelocity, precedingCarAcceleration, 5);

                int followingCarDistance = (int) (Math.random() * 8) + 1;
                int followingCarVelocity = (int) (Math.random() * 36) + 25;
                int followingCarAcceleration = (int) (Math.random() * 5) - 5;
                Car followingCar = new Car(-followingCarDistance, followingCarVelocity, followingCarAcceleration, 5);

                boolean collision = false;
                int j = 0;

                while(collision == false && j < displayTime) {
                    double followingCarFrontFinalPosition = (followingCarVelocity * j) + (0.5 * followingCarAcceleration * Math.pow(j, 2)) + (0.5 * followingCar.getLength()) - followingCarDistance;
                    double precedingCarBackFinalPosition = (precedingCarVelocity * j) + (0.5 * precedingCarAcceleration * Math.pow(j, 2)) - (0.5 * precedingCar.getLength()) + precedingCarDistance;
                    if(followingCarFrontFinalPosition >= precedingCarBackFinalPosition) {
                        collision = true;
                    }
                    j++;
                }

                if (collision == false){
                    cars.add(precedingCar);
                    cars.add(followingCar);
                    scenarios.add(new Scenario(cars));
                }

            }
        }

        return scenarios;
    }
    private static double quadForm(double a, double b, double c){
        double solutionOne = (-b + Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);
        double solutionTwo = (-b - Math.sqrt(Math.pow(b, 2) - 4 * a * c)) / (2 * a);

        if(solutionOne < solutionTwo) {
            return solutionOne;

        } else {
            return solutionTwo;
        }
    }
}
