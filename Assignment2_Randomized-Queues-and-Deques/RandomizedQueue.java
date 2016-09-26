import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {  
    private Item[] q;              
    private int N = 0;
    public RandomizedQueue() {  
        q = (Item[]) new Object[2];  
    }  
     
    public boolean isEmpty()
    { return N == 0; }  
     
    public int size()
    { return N; }  
      
    private void resize(int cap) 
    {  
        Item[] temp = (Item[]) new Object[cap];  
        for (int i = 0; i < N; i++) {  
            temp[i] = q[i];  
        }  
        q = temp;  
    } 
    
    public void enqueue(Item item)
    {
    	if (item == null)
    		throw new java.lang.NullPointerException();
    	if (N == q.length) 
    		resize (2*q.length);
    	q[N++] = item;                         
    }
    
    public Item dequeue() 
    {  
        if (isEmpty()) 
        	throw new java.util.NoSuchElementException();  
        int index = StdRandom.uniform(N);  
        Item item = q[index];  
        if (index != N-1)  
        	q[index] = q[N-1];    
        q[N-1] = null; 
        N--;
        
        if (N > 0 && N == q.length/4) resize(q.length/2);  
        return item;  
    }  
      
    public Item sample()
    {  
        if (isEmpty()) throw new java.util.NoSuchElementException();  
        int index = (StdRandom.uniform(N));  
        return q[index];  
    } 
    
    public Iterator<Item> iterator() { return new ArrayIterator(); }
    
    private class ArrayIterator implements Iterator<Item> 
    {
    	private int index = 0;
    	private Item[] r;
    	public ArrayIterator() {
            r = (Item[]) new Object[N];
            for(int i=0; i<N; i++)
                r[i] = q[i];
            StdRandom.shuffle(r);
        }
    	
    	public boolean hasNext() { return index < (N-1); }
    	
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        
        public Item next() {
            if(!hasNext()) throw new java.util.NoSuchElementException();
            Item item = r[index++];
            return item;
        }
    }
    
    public static void main(String[] args) {   
        RandomizedQueue<String> Ranque = new RandomizedQueue<String> ();
        
        Ranque.enqueue("algorithms");
        Ranque.enqueue("love");
        Ranque.enqueue("I");
        Ranque.enqueue("Coursera");
        StdOut.println(Ranque.sample());
        Ranque.dequeue();
        Ranque.dequeue();
        
        StdOut.print("size:"+Ranque.size());
     }
    
    
}