public class Subset {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> w = new RandomizedQueue<String>();
        while(!StdIn.isEmpty()) {
            w.enqueue(StdIn.readString());       
        }
        for(int i = 0; i < k; i++) {
            StdOut.println(w.dequeue());
        }      
    }
}