public class Passenger {
    private int count = 0;
    private int id;
    private int arrivalTime;
    private int destinationFloor;
    private int timeOnFloor;
    private boolean isLeaving;

    public Passenger(int arrivalTime, int destinationFloor, int timeOnFloor) {
        this.arrivalTime = arrivalTime;
        this.destinationFloor = destinationFloor;
        this.timeOnFloor = timeOnFloor;
        isLeaving = false;
        id = count++;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public int getTimeOnFloor() {
        return timeOnFloor;
    }

    public boolean isLeaving() {
        return isLeaving;
    }

    public void setLeaving() {
        isLeaving = true;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public void setTimeOnFloor(int timeOnFloor) {
        this.timeOnFloor = timeOnFloor;
    }

    public void setLeaving(boolean isLeaving) {
        this.isLeaving = isLeaving;
    }

    public int getId() {
        return id;
    }

    
}
