public class PriorityQue<E> implements PriQue<E>{
    class Node{
        private E data;
        private Node next;
        private int priority;

        public Node(E data, Node next, int priority) {
            this.data = data;
            this.next = next;
            this.priority = priority;
        }
    }
    private Node head;

    public PriorityQue(){
        head = null;
    }

    public void insert(int pri, E data) {
        Node current = head;
        boolean flag = current != null && current.priority < pri;
        if(!flag){
            head = new Node(data, current, pri);
            return;
        }
        while(flag && current.next != null){
            if(current.next.priority > pri){
                flag = !flag;
            }
            else{
                current = current.next;
            }
        }
        current.next = new Node(data, current.next, pri);
    }

    public E remove() {
        if(head == null)
            return null;
        Node tmp = head;
        head = head.next;
        return tmp.data;
    }

    public E peek() {
        return head.data;
    }

    public boolean isEmpty() {
        return head == null;
    }
    
}
