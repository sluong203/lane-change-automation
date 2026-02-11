//1234567890123456789012345001234567890123456789012345001234567890123456789012345001234567890623456789012345

import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;
import java.util.List;

/**
 * Write a description of class tester here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class tester
{
    public static void test() throws java.io.IOException {
        FileResource overview = new FileResource();
        FileResource cars = new FileResource();
        
        CSVParser overviewParser = overview.getCSVParser();
        CSVParser carsParser = cars.getCSVParser();
        List<CSVRecord> carsList = carsParser.getRecords();
        
        ArrayList<LaneChange> scenarios = new ArrayList<LaneChange>();
        ArrayList<String> finalFactors = new ArrayList<String>();      
        
        for (CSVRecord scenarioRow : overviewParser) {
            if(scenarioRow.get(15).equals("1")) { //only cars that made one lane change
                double attemptingVelocity = 0;
                double attemptingLength = 0;
                Car precedingCar = null;
                Car followingCar = null;
                double duration = 0;
                double finalAcceleration = 0;
                int initialFrame = Integer.parseInt(scenarioRow.get(3));
                for (CSVRecord carsRow0 : carsList) {
                    if(carsRow0.get(0).equals(scenarioRow.get(3)) && carsRow0.get(1).equals(scenarioRow.get(0))) { //check that this is the correct change and is first frame
                        int initialLane = Integer.parseInt(carsRow0.get(24));
                        int finalLane = Integer.parseInt(carsRow0.get(24));
                        int frameBeforeChange = 0;
                        boolean accelerationRecorded = false;
                        for (CSVRecord carsRow1 : carsList) { // finds final lane and acceleration
                            if(carsRow1.get(1).equals(scenarioRow.get(0)) && 
                                carsRow1.get(0).equals(scenarioRow.get(4))) {
                                finalLane = Integer.parseInt(carsRow1.get(24));
                            }
                            
                            if(!accelerationRecorded && carsRow1.get(1).equals(scenarioRow.get(0)) && 
                                Integer.parseInt(carsRow1.get(24)) != initialLane) {
                                finalAcceleration = Double.parseDouble(carsRow1.get(8));
                                frameBeforeChange = Integer.parseInt(carsRow1.get(0));
                                accelerationRecorded = true;
                            }
                        }
                        
                        duration = (frameBeforeChange - initialFrame) / 25.0;
                        
                        attemptingVelocity = Double.parseDouble(carsRow0.get(6));
                        attemptingLength = Double.parseDouble(scenarioRow.get(1));
                        if(finalLane == initialLane + 1) { //if change lanes to the left
                            for (CSVRecord carsRow2 : carsList) {
                                if(carsRow2.get(1).equals(carsRow0.get(18))) { //builds precedingCar
                                    double precedingDistance = Double.parseDouble(carsRow2.get(2)) - 
                                                                Double.parseDouble(carsRow0.get(2));
                                    double precedingVelocity = Double.parseDouble(carsRow2.get(6));
                                    double precedingAccel = Double.parseDouble(carsRow2.get(6));
                                    double precedingLength = Double.parseDouble(carsRow2.get(4));
                                    precedingCar = new Car(precedingDistance, precedingVelocity, 
                                                            precedingAccel, precedingLength);
                                }
                                if(carsRow2.get(1).equals(carsRow0.get(20))) { //builds followingCar
                                    double followingDistance = Double.parseDouble(carsRow2.get(2)) - 
                                                                Double.parseDouble(carsRow0.get(2));
                                    double followingVelocity = Double.parseDouble(carsRow2.get(6));
                                    double followingAccel = Double.parseDouble(carsRow2.get(6));
                                    double followingLength = Double.parseDouble(carsRow2.get(4));
                                    followingCar = new Car(followingDistance, followingVelocity, 
                                                            followingAccel, followingLength);
                                }
                            }
                        } else { //if change lanes to the right
                            for (CSVRecord carsRow2 : carsList) {
                                if(carsRow2.get(1).equals(carsRow0.get(21))) { //builds precedingCar
                                    double precedingDistance = Double.parseDouble(carsRow2.get(2)) - 
                                                                Double.parseDouble(carsRow0.get(2));
                                    double precedingVelocity = Double.parseDouble(carsRow2.get(6));
                                    double precedingAccel = Double.parseDouble(carsRow2.get(6));
                                    double precedingLength = Double.parseDouble(carsRow2.get(4));
                                    precedingCar = new Car(precedingDistance, precedingVelocity, 
                                                            precedingAccel, precedingLength);
                                }
                                if(carsRow2.get(1).equals(carsRow0.get(23))) { //builds followingCar
                                    double followingDistance = Double.parseDouble(carsRow2.get(2)) - 
                                                                Double.parseDouble(carsRow0.get(2));
                                    double followingVelocity = Double.parseDouble(carsRow2.get(6));
                                    double followingAccel = Double.parseDouble(carsRow2.get(6));
                                    double followingLength = Double.parseDouble(carsRow2.get(4));
                                    followingCar = new Car(followingDistance, followingVelocity, 
                                                            followingAccel, followingLength);
                                }
                            }
                        }
                    }
                }
                
                LaneChange scenario = new LaneChange(precedingCar, followingCar, 
                                                        attemptingVelocity, attemptingLength); //builds scenario    
                scenarios.add(scenario);
                finalFactors.add("Time: " + duration + "\nAcceleration: " + finalAcceleration);
            }
        }
        
        int correctPossibility = 0;
        int incorrectPossibility = 0;
        int correctFactors = 0;
        int incorrectFactors = 0;
        int i = 0;
        
        for(LaneChange scenario : scenarios) {
            if(!scenario.factorsNeeded().equals("The other car(s) need to change acceleration.")) {
                correctPossibility++;
                
                if(scenario.factorsNeeded().equals(finalFactors.get(i))) {
                    correctFactors++;
                } else {
                    incorrectFactors++;
                    System.out.println(scenario);
                    System.out.println("Predicted factors:");
                    System.out.println(scenario.factorsNeeded());
                    System.out.println("Actual factors:");
                    System.out.println(finalFactors.get(i));
                }
            } else {
                incorrectPossibility++;
            }
            i++;
        }
        
        System.out.println("Correct possibility predictions: " + correctPossibility);
        System.out.println("Incorrect possibilty predictions: " + incorrectPossibility);
        System.out.println("Correct factor predictions: " + correctFactors);
        System.out.println("Incorrect factor predictions: " + incorrectFactors);
        System.out.println("Total predictions: " + scenarios.size());
    }
}