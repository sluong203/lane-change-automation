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
        for(int i = 0; i < 100000; i++) {
            double PCdistance = (Math.random() * (10+ 1));
            double PCvelocity = (Math.random() * (33 - 30 + 1)) + 30;
            double PCacceleration = (Math.random() * (2 + 1));
            double PClength = (Math.random() * (5 - 4 + 1)) + 4;
            Car preceding = new Car(PCdistance, PCvelocity, PCacceleration, PClength);
            
            double FCdistance = (Math.random() * (10+ 1));
            double FCvelocity = (Math.random() * (33 - 30 + 1)) + 30;
            double FCacceleration = (Math.random() * (2 + 1));
            double FClength = (Math.random() * (5 - 4 + 1)) + 4;
            Car following = new Car(FCdistance, FCvelocity, FCacceleration, FClength);
            
            double CCvelocity = (Math.random() * (33 - 30 + 1)) + 30;
            double CClength = (Math.random() * (5 - 4 + 1)) + 4;
            LaneChange laneChange = new LaneChange(preceding, following, CCvelocity, CClength);
            
            laneChangeList.add(laneChange);
        }
        
        for(int i = 0; i < 5; i++) {
            System.out.println(laneChangeList.get(i));
        }
        
        for(LaneChange change : laneChangeList) {
            System.out.println(change);
        }
        
        /*
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        
        ArrayList<Car> carList = new ArrayList<Car>();
        for(CSVRecord row : parser){
            if(Integer.parseInt(row.get(0)) == 1) {
                Car car = new Car(Integer.parseInt(row.get(1)),
                                    Double.parseDouble(row.get(12)), 
                                    Double.parseDouble(row.get(6)), 
                                    Double.parseDouble(row.get(8)), 
                                    Double.parseDouble(row.get(4)),
                                    Integer.parseInt(row.get(18)),
                                    Integer.parseInt(row.get(20)));
                carList.add(car);
            }
        }
        
        ArrayList<laneChange> laneChangeList = new ArrayList<laneChange>();
        for(Car car : carList) {
            laneChange change;
            if(car.precedingId != 0 && car.followingId == 0) {
                change = new laneChange(carList.get(car.precedingId - 1), null,
                                                    car.velocity,
                                                    car.length);
            } else if (car.precedingId == 0 && car.followingId != 0) {
                change = new laneChange(null, carList.get(car.followingId - 1),
                                                    car.velocity,
                                                    car.length);
            } else if (car.precedingId != 0 && car.followingId != 0){
                change = new laneChange(carList.get(car.precedingId - 1), 
                                                    carList.get(car.followingId - 1),
                                                    car.velocity,
                                                    car.length);
            } else {
                change = new laneChange(null, null, car.velocity, car.length);
            }
            laneChangeList.add(change);
        }
        
        for(int i = 0; i < 5; i++) {
            System.out.println(laneChangeList.get(i));
        }
        */
    }
}