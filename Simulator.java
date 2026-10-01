import processing.core.PApplet;
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
        SimulatorCar attempting = new SimulatorCar(new Vector2D(0))
    }

    public void drawCars(SimulatorCar attempting, SimulatorCar otherCarOne, SimulatorCar otherCarTwo) {

    }

    public void drawCars(SimulatorCar attempting, SimulatorCar otherCar) {

    }


}


