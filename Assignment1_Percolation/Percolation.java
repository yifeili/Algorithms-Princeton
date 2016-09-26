
public class Percolation {

    private static boolean[][] opened;
    private int index1 = 0;
    private int index2;
    private int size;
    private WeightedQuickUnionUF qf;

     /**
      * Creates N-by-N grid, with all sites blocked.
      * Initialize each site false.
      */
    public Percolation(int N) 
    {
        size = N;
        index2 = size * size + 1;
        qf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[size][size];
        for (int i=0; i<N; i++)
        	for (int j=0; j<N; j++)
        		opened[i][j] = false;
    }

     /**
      * Opens site (row i, column j) if it is not open already.
      */
    public void open(int i, int j) 			// Use array qf as the monitor that whether the opened sites in "opened" are full.
    {
    	if (i < 1 || i > size || j < 1 || j > size)
    		throw new IndexOutOfBoundsException();
        
    	opened[i-1][j-1] = true; 
          
        if (i == 1) {
        	qf.union(getIndex(i, j), index1);
        }
        if (i == size) {
            qf.union(getIndex(i, j), index2);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            qf.union(getIndex(i, j), getIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            qf.union(getIndex(i, j), getIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            qf.union(getIndex(i, j), getIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            qf.union(getIndex(i, j), getIndex(i + 1, j));
        }
    }

          
    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) 
    {
    	if (i < 1 || i > size || j < 1 || j > size)
    		throw new IndexOutOfBoundsException();
        return opened[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) 		// If site(row i, column j) in array of opened is full, in qf, the index of (row i, column j) should have the same root of index1.
    {
    	if (i < 1 || i > size || j < 1 || j > size)
            throw new IndexOutOfBoundsException();
        return qf.connected(index1, getIndex(i , j)); 
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return qf.connected(index1, index2);
    }

    /**
     * Obtain the complete index of the 2D array.
     */
    private int getIndex(int i, int j) 
    {
         return size * (i - 1) + j;
    }
     
    /**
     * We can try to generate a 5*5 2D array.
     */
    public static void main(String[] args) {
    	int N = 5;
    	Percolation per = new Percolation(N);
    	per.open(2,4);
    	per.open(5,1);
    	per.open(3,3);
    	System.out.println(per.percolates());
    }

}
