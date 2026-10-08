import processing.core.PApplet;
import java.util.ArrayList;
public class Simulator extends PApplet{
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private int numCars = 3;
    double timeScale = 0.05;
    double time = 0;
    private ArrayList<SimulatorCar> simulatorCars;

    public static void main(String[] args){
        PApplet.main("Simulator");
    }

    public void settings() {
        size(canvasWidth, canvasHeight);
    }

    public void setup() {
        drawLanes();

        simulatorCars = new ArrayList<SimulatorCar>();

        SimulatorCar carOne = new SimulatorCar((new Vector2D(450, 600)), new Vector2D(0, 400), new Vector2D(0, 0), 200);
        simulatorCars.add(carOne);

        SimulatorCar carTwo = new SimulatorCar((new Vector2D(250, 600)), new Vector2D(0, 400), new Vector2D(0, 0), 200);
        simulatorCars.add(carTwo);

        drawCars();
    }

    public void draw() {
        background(255);

        drawLanes();
        time++;

        for(SimulatorCar car : simulatorCars) {
            car.update((time / 60.0) * timeScale);
            drawCars();
        }
    }

    public void drawLanes () {
        line(200, 0, 200, 600);
        line(600, 0, 600, 600);
        for(int i = 0; i < 600; i += 20) {
            line(400, i, 400, i + 10);
        }
    }

    public void drawCars() {
        for(SimulatorCar car : simulatorCars) {
            rect((float) (car.getPosition().getX()), (float) (car.getPosition().getY()), (float) car.getWidth(), (float) car.getLength());
        }
    }


}


