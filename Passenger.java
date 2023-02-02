public class Passenger {
    private int arrivalTime;
    private int destinationFloor;
    private int timeOnFloor;

    public Passenger(int arrivalTime, int destinationFloor, int timeOnFloor) {
        this.arrivalTime = arrivalTime;
        this.destinationFloor = destinationFloor;
        this.timeOnFloor = timeOnFloor;
    }

    // debug method
    public String toString(){
        return "Arrives: " + arrivalTime + ", goes to floor " + destinationFloor + ", spending " + timeOnFloor + " time there.";
    }
}
