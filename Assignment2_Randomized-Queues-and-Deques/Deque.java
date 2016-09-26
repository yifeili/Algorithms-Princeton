import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> 
{
	private int N;
	private Node first, last;
	private class Node
	{
		Item item;
		Node next;	
		Node prev;
	}
	
	public Deque()
	{
		first = last = null;
        N = 0;
	}
	
	public boolean isEmpty()
	{ return N == 0; }
	
	public int size()
	{ return N; }
	
	public void addFirst(Item item)
	{		
		if(item == null) throw new java.lang.NullPointerException();
		Node oldfirst = first;
		first = new Node ();
		first.item = item;
		first.next = oldfirst;
		first.prev = null;
		if (isEmpty()) last = first;
		else oldfirst.prev = first;
		N++;
	}
	
	public void addLast(Item item) 
	{
	    if(item == null) throw new java.lang.NullPointerException();
	    Node oldlast = last;
	    last = new Node();
	    last.item = item;
	    last.prev = oldlast;
	    last.next = null;
	    if(isEmpty()) first = last;
	    else oldlast.next = last;
	    N++;
	   }

	public Item removeFirst()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException(); 
		Item item = first.item;
		first = first.next;
		N--;
		if (isEmpty()) last = null;
		else first.prev = null;
		return item;
	}
	
	public Item removeLast()
	{
		if (isEmpty()) throw new java.util.NoSuchElementException(); 
		Item item = last.item;
		last = last.prev;
		N--;
		if (isEmpty()) first = null;
		else last.next = null;
		return item;
	}
	
	public Iterator<Item> iterator(){
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<Item>
	{
		private Node current = first;
		public boolean hasNext() { return current != null; }
		public void remove() { throw new java.lang.UnsupportedOperationException(); }
		public Item next()
		{
			if(!hasNext()) throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.next;
			return item;
		}
	}
	
	public static void main(String[] args)
	{
		Deque<String> deque = new Deque<String> ();
		deque.addFirst("algorithms");
		deque.addFirst("love");
		deque.addFirst("I");
		deque.addLast("coursera");
	   
		StdOut.println(deque.removeLast());
	    StdOut.println(deque.removeFirst());
	    StdOut.println(deque.removeFirst());
	    StdOut.println(deque.removeFirst());
	    StdOut.print("size:"+deque.size());
	}
}
