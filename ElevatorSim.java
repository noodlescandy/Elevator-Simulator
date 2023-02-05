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
        try (BufferedReader r = new BufferedReader(new FileReader(fileName))){ // poss issue
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
        final int ELEVATOR_CAPACITY = 5;

        int time = 1;
        int currentFloor = 0;
        Floor[] building = new Floor[numFloors];
        building[0] = new Floor(toLoad);
        for (int i = 1; i < building.length; i++) { // init building
            building[i] = new Floor();
        }
        Passenger[] elevator = new Passenger[ELEVATOR_CAPACITY];
        boolean hasAction;
        boolean goingUp = true;
        boolean[] buttonCalled = new boolean[numFloors];
        
        // stats vars
        int numRides = 0;
        int totalWaits = 0;
        int longestWait = -1;
        int longestWaitID = -1;

        while(buildingNotEmpty(building, elevator)){
            hasAction = true;
            PriQue<Passenger> thisFloor = building[currentFloor].getLine();
            
            if(buttonCalled[currentFloor]){
                // doors open
                buttonCalled[currentFloor] = false;
                // unload elevator
                for(int i = 0; i < elevator.length; i++){
                    if(elevator[i] != null && elevator[i].getDestinationFloor() == currentFloor){
                        hasAction = false;
                        Passenger tmp = elevator[i];
                        if(currentFloor != 0){
                            thisFloor.insert(time + tmp.getTimeOnFloor(), elevator[i]);
                        }
                        elevator[i] = null;
                        System.out.println("at time " + time + " person " + tmp.getId() + " got off at floor " + currentFloor);
                        tmp.setDestinationFloor(0);
                        tmp.setArrivalTime(time + tmp.getTimeOnFloor());
                        numRides++;
                    }
                    
                }
                // load elevator
                for (int i = 0; i < elevator.length && !thisFloor.isEmpty() && thisFloor.peek().getArrivalTime() <= time; i++) {
                    if(elevator[i] == null){
                        elevator[i] = thisFloor.remove();
                        Passenger tmp = elevator[i];
                        System.out.println("at time " + time + " person " + tmp.getId() + " got on at floor " + currentFloor + " destination is floor " + tmp.getDestinationFloor());
                        hasAction = false;
                        buttonCalled[tmp.getDestinationFloor()] = true;
                        // stats
                        int wait = time - tmp.getArrivalTime();
                        totalWaits+= wait;
                        if(wait > longestWait){
                            longestWait = wait;
                            longestWaitID = tmp.getId();
                        }
                    }
                }
            }
            
            // elevator moves
            if(hasAction){
                if(currentFloor == numFloors-1){
                    goingUp = false;
                }
                else if(currentFloor == 0){
                    goingUp = true;
                }
                currentFloor += (goingUp ? 1 : -1);
            }

            // turn on button if passenger at front of any line needs the elevator
            for (int i = 0; i < building.length; i++) {
                PriQue<Passenger> floor = building[i].getLine();
                if(!floor.isEmpty() && floor.peek().getArrivalTime() <= time){
                    buttonCalled[i] = true;
                }
            }
            
            time++;
        }
        // print statistics
        System.out.println("*** Statistics ***");
        double avgWait = ((double) totalWaits) / ((double) numRides);
        System.out.println("For " + numRides + " rides, the avg wait was " + avgWait);
        System.out.println("longest wait was " + longestWait + " customer " + longestWaitID);
    }


    /**
     * Determines whether there is anyone still present in the building, whether on any of the floors or in the elevator.
     */
    private boolean buildingNotEmpty(Floor[] building, Passenger[] elevator){
        for(int i = 0; i < building.length; i++){
            if(!building[i].getLine().isEmpty())
                return true;
        }
        return elevator[0] != null;
    }
}