import java.io.BufferedReader;
import java.io.FileReader;

// reads in file and tests the elevator simulation. args[0] is num floors, args[1] is text file.
public class ElevatorSim {
    public static void main(String[] args) {
        int numFloors;
        String fileName;
        try {
            numFloors = Integer.parseInt(args[0]);
            fileName = args[1];
        } catch (Exception e) {
            System.err.println("Usage: ElevatorSim <number of floors> <file name>");
            return;
        }
        PriQue<Passenger> passengerList = new PriorityQue<Passenger>();
        try {
            BufferedReader r = new BufferedReader(new FileReader(fileName));
            String current = null;
            while((current = r.readLine()) != null){
                current = current.trim();
                if(current.charAt(0) != '/'){
                    int firstSpace = current.indexOf(" ");
                    int secondSpace = current.lastIndexOf(" ");

                    int arrivalTime = Integer.parseInt(current.substring(0, firstSpace));
                    int destinationFloor = Integer.parseInt(current.substring(firstSpace+1, secondSpace));
                    int timeOnFloor = Integer.parseInt(current.substring(secondSpace+1));
                    
                    if(destinationFloor < 1 || destinationFloor > numFloors){
                        System.err.println("Destination Floor " + destinationFloor + " ground floor or too high, ignoring passenger.");
                    }
                    else{
                        passengerList.insert(arrivalTime, new Passenger(arrivalTime, destinationFloor, timeOnFloor));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Trouble reading " + fileName);
            System.err.println("Please provide a .txt file");
            return;
        }
        
        ElevatorSim sim = new ElevatorSim();
        sim.run(passengerList, numFloors);
    }

    public void run(PriQue<Passenger> toLoad, int numFloors){
        // clock cycles
        int clock = 0;
        int currentFloor = 0;
        while(!toLoad.isEmpty()){
            System.out.println(toLoad.remove()); // debug
            
            // check if passengers on elevator need to get off on this floor
                // if passengers unload, skip to end
            
            // check current floor for passengers waiting for elevator
            // if passengers, load them in order of time waited longest, up to 5
                // it takes one unit of time to unload or load passengers, so skip to end
            
            // if nothing else, continue moving on path
                // go up a floor until reaching top of building and then start going down until reaching ground in loop.

            clock++;
        }
    }
}