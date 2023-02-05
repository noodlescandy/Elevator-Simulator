/*
 * This tests the PriQ implementation independently from the elevator code
 */
public class QueueTestor {
	public static void main(String[] args) {
		PriQue<String> myQ = new PriorityQue<String>();
		myQ.insert(35, "thrity five");
		myQ.insert(40, "forty");
		myQ.insert(5, "five-1");
		myQ.insert(5, "five-2");
		myQ.insert(10, "ten");
		myQ.insert(15, "fifteen");
		myQ.insert(12, "twelve");
		myQ.insert(45, "forty five");
		
		while(!myQ.isEmpty()) {
			System.out.println(myQ.remove());
		}
	}
}
