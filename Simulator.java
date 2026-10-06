import processing.core.PApplet;
import java.util.ArrayList;
public class Simulator extends PApplet{
    private int canvasWidth = 800;
    private int canvasHeight = 600;
    private int numCars = 3;

    public static void main(String[] args) {
        PApplet.main("Simulator");
    }

    public void settings() {
        size(canvasWidth, canvasHeight);
    }

    public void draw() {
        line(200, 0, 200, 600);
        line(600, 0, 600, 600);
        for(int i = 0; i < 600; i += 20) {
            line(400, i, 400, i + 10);
        }

        ArrayList<SimulatorCar> simulatorCars = new ArrayList<SimulatorCar>();

        SimulatorCar carOne = new SimulatorCar((new Vector2D(500, 400)), new Vector2D(500, 400), new Vector2D(500, 400), 4.5);
        simulatorCars.add(carOne);

        SimulatorCar carTwo = new SimulatorCar((new Vector2D(200, 200)), new Vector2D(500, 400), new Vector2D(500, 400), 4.5);
        simulatorCars.add(carTwo);

        drawCars(simulatorCars);
    }

    public void drawCars(ArrayList<SimulatorCar> carArr) {
        for(SimulatorCar car : carArr) {
            double pixelWidth = car.getWidth() * 50;
            double pixelLength = car.getLength() * 50;
            rect((float) (car.getPosition().getX() - (pixelWidth / 2)),
                    (float) (car.getPosition().getY() + (pixelLength / 2)),
                    (float) pixelWidth, (float) pixelLength);
        }
    }


}


