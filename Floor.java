public class Floor {
    private PriQue<Passenger> line;
    
    public Floor(){
        line = new PriorityQue<Passenger>();
    }

    public Floor(PriQue<Passenger> line){
        this.line = line;
    }

    public PriQue<Passenger> getLine() {
        return line;
    }
}
