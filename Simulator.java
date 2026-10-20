import processing.core.PApplet;
import java.util.ArrayList;
public class Simulator extends PApplet{
    private int canvasWidth = 800;
    private int canvasHeight = 1000;
    private int numCars = 3;
    double timeScale = 0.005;
    double time = 0;
    private ArrayList<SimulatorCar> simulatorCars;
    private ArrayList<Scenario> scenarios;

    public static void main(String[] args){
        PApplet.main("Simulator");
    }

    public void settings() {
        size(canvasWidth, canvasHeight);
    }

    public void setup() {
        drawLanes();

        scenarios = buildScenarioList();

        simulatorCars = new ArrayList<SimulatorCar>();

        // drawScenario();

        SimulatorCar carOne = new SimulatorCar((new Vector2D(300, 0)), new Vector2D(50, 400), new Vector2D(0, 0), 200);
        simulatorCars.add(carOne);

        SimulatorCar carTwo = new SimulatorCar((new Vector2D(500, 0)), new Vector2D(0, 400), new Vector2D(0, 0), 200);
        simulatorCars.add(carTwo);

        drawCars();
    }

    public void draw() {
        background(255);

        drawLanes();
        time++;

        for(SimulatorCar car : simulatorCars) {
            displayStats();
            car.update((time / 60.0) * timeScale);
            drawCars();
        }
    }

//    public void drawScenario() {
//
//    }

    public void drawLanes () {
        line(200, 0, 200, 1000);
        line(600, 0, 600, 1000);
        for(int i = 0; i < 1000; i += 20) {
            line(400, i, 400, i + 10);
        }
    }

    public void drawCars() {
        for(SimulatorCar car : simulatorCars) {
            rect((float) (car.getPosition().getX() - (car.getWidth() / 2.0)), (float) (car.getPosition().getY()), (float) car.getWidth(), (float) car.getLength());
        }
    }

    public void displayStats() {
        textSize(20);
        fill(0, 0, 0);
        int y = 30;
        text("Time: " + time, 10, y);
        for(int i = 0; i < simulatorCars.size(); i++) {
            y += 40;
            text("Car " + (i + 1) + ": ", 10, y);
            y += 20;
            text("Position: " + simulatorCars.get(i).getPosition(), 10, y);
            y += 20;
            text("Velocity: " + simulatorCars.get(i).getVelocity(), 10, y);
            y += 20;
            text("Acceleration: " +  simulatorCars.get(i).getAcceleration(), 10, y);

        }
    }

}


