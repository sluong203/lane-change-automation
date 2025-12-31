import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.util.ArrayList;
import java.lang.Math;

/**
 * Write a description of class tester here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */

//all units in m/s, m, etc.
//avg car length: 4-5 meters
//avg car velocity: 70-75 mph = 30-33 m/s
//avg car acceleration: 1.5-2 m/s^2
//avg car distances: 0-10 meters
public class tester
{
    public static void tester() {
        ArrayList<LaneChange> laneChangeList = new ArrayList<LaneChange>();
        System.out.println("PC present, in the way, accelerating");
        Car precedingCar = new Car(1, 30, 0.5, 4.5);
        LaneChange laneChange = new LaneChange(precedingCar, null, 30, 5);
        System.out.println(laneChange);
        System.out.println(laneChange.factorsNeeded());
        
        System.out.println("PC present, in the way, decelerating");
        
        System.out.println("PC present, in the way, not accelerating");
        
        System.out.println("PC present and not in the way");
        
        System.out.println("FC present and in the way");
        
        System.out.println("FC present and not in the way");
        
        System.out.println("Both present and PC in the way");
        
        System.out.println("Both present and FC in the way");
        
        System.out.println("Both present and neither in the way");
        
        System.out.println("Both present and Both in the way");
    }
}