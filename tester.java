import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;
/**
 * Write a description of class tester here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class tester
{
    public static void test() {
        FileResource overview = new FileResource();
        FileResource cars = new FileResource();
        
        CSVParser overviewParser = overview.getCSVParser();
        CSVParser carsParser = cars.getCSVParser();
        
        ArrayList<LaneChange> scenarios = new ArrayList<LaneChange>();
        
        for (CSVRecord scenarioRow : overviewParser) {
            if(scenarioRow.get(15).equals("1") && scenarioRow.get(7).equals("1")) {
                double attemptingVelocity;
                double attemptingLength;
                Car precedingCar;
                Car followingCar;
                
                for (CSVRecord carsRowO : carsParser) {
                    int initialLane = carsRowO.get(24);
                    int finalLane;
                    //while loop to figure out if its change lanes to left or right
                    if(carsRowO.get(1).equals(scenarioRow.get(0)) && carsRowO.get(0).equals("1")) {
                        attemptingVelocity = Double.parseDouble(carsRowO.get(6)) / 3.6;
                        attemptingLength = Double.parseDouble(carsRowO.get(1));
                        if() { //if change lanes to the left
                            for (CSVRecord carsRowI : carsParser) {
                                if(carsRowI.get(1).equals(carsRowO.get(18))) {
                                    double precedingDistance = Double.parseDouble(carsRowI.get(2)) - Double.parseDouble(carsRowO.get(2));
                                    double precedingVelocity = Double.parseDouble(carsRowI.get(6)) / 3.6;
                                    double precedingAcceleration = Double.parseDouble(carsRowI.get(6)) / 12960;
                                    double precedingLength = Double.parseDouble(carsRowI.get(4));
                                    precedingCar = new Car(precedingDistance, precedingVelocity, precedingAcceleration, precedingLength);
                                }
                                if(carsRowI.get(1).equals(carsRowO.get(20))) {
                                    double followingDistance = Double.parseDouble(carsRowI.get(2)) - Double.parseDouble(carsRowO.get(2));
                                    double followingVelocity = Double.parseDouble(carsRowI.get(6)) / 3.6;
                                    double followingAcceleration = Double.parseDouble(carsRowI.get(6)) / 12960;
                                    double followingLength = Double.parseDouble(carsRowI.get(4));
                                    followingCar = new Car(followingDistance, followingVelocity, followingAcceleration, followingLength);
                                }
                            }
                        } else { //if change lanes to the right
                            for (CSVRecord carsRowI : carsParser) {
                                if(carsRowI.get(1).equals(carsRowO.get(21))) {
                                    double precedingDistance = Double.parseDouble(carsRowI.get(2)) - Double.parseDouble(carsRowO.get(2));
                                    double precedingVelocity = Double.parseDouble(carsRowI.get(6)) / 3.6;
                                    double precedingAcceleration = Double.parseDouble(carsRowI.get(6)) / 12960;
                                    double precedingLength = Double.parseDouble(carsRowI.get(4));
                                    precedingCar = new Car(precedingDistance, precedingVelocity, precedingAcceleration, precedingLength);
                                }
                                if(carsRowI.get(1).equals(carsRowO.get(23))) {
                                    double followingDistance = Double.parseDouble(carsRowI.get(2)) - Double.parseDouble(carsRowO.get(2));
                                    double followingVelocity = Double.parseDouble(carsRowI.get(6)) / 3.6;
                                    double followingAcceleration = Double.parseDouble(carsRowI.get(6)) / 12960;
                                    double followingLength = Double.parseDouble(carsRowI.get(4));
                                    followingCar = new Car(followingDistance, followingVelocity, followingAcceleration, followingLength);
                                }
                            }
                        }
                    }
                }
                
                LaneChange scenario = new LaneChange(precedingCar, followingCar, attemptingVelocity, attemptingLength);
                scenarios.add(scenario);
            }
        }
        
        int correctPredictions = 0;
        int incorrectPredictions = 0;
        for(LaneChange scenario : scenarios) {
            if(!scenario.factorsNeeded.equals("The other car(s) need to change acceleration.")) {
                correctPredictions++;
            } else {
                incorrectPredictions++;
            }
        }
        
        System.out.println("Correct predictions: " + correctPredictions);
        System.out.println("Incorrect predictions: " + incorrectPredictions);
        System.out.println("Total predictions: " + scenarios.size());
    }
}