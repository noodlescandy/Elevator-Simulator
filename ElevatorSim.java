import java.io.BufferedReader;
import java.io.FileReader;

// reads in file and tests the elevator simulation. args[0] is num floors, args[1] is text file.
public class ElevatorSim {
    public static void main(String[] args) {
        // tmp debug TODO DELETE ME
        args = new String[2];
        args[0] = "9";
        args[1] = "elevatorAllTogether.txt";
        
        
        
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

    // possible issue, using returned prique instead of actual queue, assuming it'll work but it could cause problemsos.
    public void run(PriQue<Passenger> toLoad, int numFloors){
        // clock cycles
        int time = 0;
        int currentFloor = 0;
        Floor[] building = new Floor[numFloors+1];
        for (int i = 1; i < building.length; i++) { // initialize building
            building[i] = new Floor();
        }

        building[0] = new Floor(toLoad);
        Passenger[] elevator = new Passenger[5]; // elevator with capacity 5
        boolean hasAction;
        int moving = 1;
        while(buildingNotEmpty(building, elevator)){
            //System.out.println(toLoad.remove()); // debug
            hasAction = true;
            PriQue<Passenger> thisFloor = building[currentFloor].getLine();
            
            // check if passengers on elevator need to get off on this floor
            if(elevator[0] != null){ // elevator not empty
                for(int i = 0; i < elevator.length; i++){
                    // check if person needs to get off on current floor.
                    // if someone gets off, action exhausted for this tick
                    if(elevator[i] != null && elevator[i].getDestinationFloor() == currentFloor){
                        hasAction = false;
                        //building[currentFloor].getLine().insert(clock, elevator[i]);
                        Passenger tmp = elevator[i];
                        if(currentFloor != 0){
                            thisFloor.insert(time, elevator[i]);
                        }
                        elevator[i] = null;
                        System.out.println("at time " + time + " person " + tmp.getId() + " got off at floor " + currentFloor);
                        tmp.setDestinationFloor(0);
                        tmp.setArrivalTime(time + tmp.getTimeOnFloor());
                    }
                }
                if(!hasAction){
                    reOrderElevator(elevator);
                }
            }

            // TODO FIX THIS SO THAT IT ONLY LOADS THEM WHEN THEY ARE READY
            // check current floor for passengers waiting for elevator
            // if passengers, load them in order of time waited longest, up to 5
                // it takes one unit of time to unload or load passengers, so skip to end
            if(hasAction && !thisFloor.isEmpty()){
                for(int i = 0; i < elevator.length; i++){
                    if(elevator[i] == null && !thisFloor.isEmpty() && thisFloor.peek().getArrivalTime() >= time){
                        elevator[i] = thisFloor.remove();
                        Passenger tmp = elevator[i];
                        System.out.println("at time " + time + " person " + tmp.getId() + " got on at floor " + currentFloor + " destination is floor " + tmp.getDestinationFloor());
                        hasAction = false;
                    }
                }
            }

            // if nothing else, continue moving on path
                // go up a floor until reaching top of building and then start going down until reaching ground in loop.
            if(hasAction){
                // TODO CAN THIS BE CONDENSED INTO ONE IF STATEMENT THINGY
                if(currentFloor == numFloors){
                    //currentFloor--;
                    moving = -1;
                }
                else if(currentFloor == 0){
                    //currentFloor++;
                    moving = 1;
                }
                currentFloor += moving;
                //System.out.println("moooooving");
            }

            time++;
        }
    }

    public void reOrderElevator(Passenger[] elevator){
        for(int i = 0; i < elevator.length-1; i++){
            if(elevator[i] == null){
                elevator[i] = elevator[i+1];
                elevator[i+1] = null;
            }
        }
    }

    public boolean buildingNotEmpty(Floor[] building, Passenger[] elevator){
        for(int i = 0; i < building.length; i++){
            if(!building[i].getLine().isEmpty())
                return true;
        }
        return elevator[0] != null;
    }
}