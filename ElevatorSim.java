// reads in file and tests the elevator simulation. args[0] is num floors, args[1] is text file.
public class ElevatorSim {
    public static void main(String[] args) {
        // args[0] is number of building floors

        // agrs[1] is file holding the initial events 
        // process file:
        /*
         * 
        Each line in the file will represent a passenger, including the time they arrive, their destination floor, and the time they will spend on the destination floor. Close the file when finished.
        If a line specifies a destination of floor 0, print an error message and go on to the next line of the file.
        If a line specifies a destination floor which is greater than the number of floors in the building, print an error message and go on to the next line of the file.
        Close the file when finished.

         */

        // ignore lines with //
        // <arrival time> <destination floor> <time on destination floor>
        // run simulation using saved list of passengers.
    }

    public void run(PriQue<Passenger> toLoad, int numFloors){
        // clock cycles
        int clock = 0;
        int currentFloor = 0;
        while(!toLoad.isEmpty()){
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