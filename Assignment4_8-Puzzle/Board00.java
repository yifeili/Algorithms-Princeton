import java.util.Arrays;
import java.util.Comparator;

public class Board {
	public final int[][] blocks;
	public final int N;

	// construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
	public Board(int[][] blocks){
		N = blocks.length;
		this.blocks = new int[N][];
		for (int i=0; i<N; i++){
			this.blocks[i] = Arrays.copyOf(blocks[i], N);
			}
	}

	// board dimension N 
	public int dimension(){ 
		return this.N; 
	}
	public int hamming()
	{
		int k=0;
		for (int i=0; i<N; i++)
			for (int j=0; j<N && i+j < 2*N -2; j++)
				if (blocks[i][j] != i*N+j+1){
					k++;
				}
		return k;
	}
	
    // sum of Manhattan distances between blocks and goal  
	public int manhattan()
	{
		int m=0;
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (blocks[i][j] != 0)
				{
					int w = (int) blocks[i][j] / N;
					int v = blocks[i][j] % N; 
					m += Math.abs(w-i) + Math.abs(v-j);
				}
		return m;
	}
	
	// is this board the goal board?  
	public boolean isGoal(){
		return this.hamming() == 0;
	}
	
	// a board obtained by exchanging two adjacent blocks in the same row  
	public Board twin(){
        int[][] twinBoard = new int[N][N];  
        for (int i=0; i<N; i++){  
            for (int j=0; j<N; j++){  
                twinBoard[i][j] = blocks[i][j];  
            }  
        }  
        if (blocks[0][0] != 0 && blocks[0][1] != 0){  
            int temp = twinBoard[0][0];  
            twinBoard[0][0] = twinBoard[0][1];  
            twinBoard[0][1] = temp;  
        }else{  
            int temp = twinBoard[1][0];  
            twinBoard[1][0] = twinBoard[1][1];  
            twinBoard[1][1] = temp;  
        }  
        return new Board(twinBoard); 
	}
	
    // does this board equal y?
	public boolean equals(Object y){
		if (y == this) return true;
		if (y == null) return false;
		
		if (y.getClass() != this.getClass()){  
			return false;  
		}  

		Board thatBoard = (Board)y;
		if (this.N != thatBoard.N){
			return false;
		}
		int [][] thatBoardblocks = thatBoard.blocks;
		for (int i=0; i<N; i++)
			for (int j=0; j<N; j++)
				if (blocks[i][j] != thatBoardblocks[i][j]){
					return false;
					}
		return true;
	}
	
	// all neighboring boards
	public Iterable<Board> neighbors(){
		int blank_i = N;
		int blank_j = N;
		for (int i=0; i<N; i++){
			for (int j=0; j<N; j++){
				if (blocks[i][j] == 0){
					blank_i = i;
					blank_j = j;
				}
			}
		}

		MinPQ<Board> q = new MinPQ<Board>(new Comparator<Board>(){
			public int compare(Board b1, Board b2){
				if (b1.manhattan() < b2.manhattan()) return -1;  
	            else if (b1.manhattan() == b2.manhattan()) return 0;  
	            else return 1;  
			}
		});
        if (blank_j - 1 >= 0){  
            int[][] arr_temp = getCopy();  
            arr_temp[blank_i][blank_j] = arr_temp[blank_i][blank_j - 1];  
            arr_temp[blank_i][blank_j - 1] = 0;  
            q.insert(new Board(arr_temp));  
        }
        if (blank_j + 1 < N){  
            int[][] arr_temp = getCopy();  
            arr_temp[blank_i][blank_j] = arr_temp[blank_i][blank_j + 1];  
            arr_temp[blank_i][blank_j + 1] = 0;  
            q.insert(new Board(arr_temp));
        }
        if (blank_i - 1 >= 0){  
            int[][] arr_temp = getCopy();  
            arr_temp[blank_i][blank_j] = arr_temp[blank_i - 1][blank_j];  
            arr_temp[blank_i - 1][blank_j] = 0;  
            q.insert(new Board(arr_temp));  
        }
        if (blank_i + 1 < N){  
            int[][] arr_temp = getCopy();  
            arr_temp[blank_i][blank_j] = arr_temp[blank_i + 1][blank_j];  
            arr_temp[blank_i + 1][blank_j] = 0;  
            q.insert(new Board(arr_temp));
        }
        return q;
	}
	
	public String toString(){
        StringBuilder s = new StringBuilder();  
        s.append(N + "\n");  
        for (int i = 0; i < N; i++) {  
            for (int j = 0; j < N; j++) {  
                s.append(String.format("%2d ", blocks[i][j]));  
            }  
            s.append("\n");  
        }  
        return s.toString();
	}
	
    private int[][] getCopy(){  
        int[][] result = new int[N][];  
        for (int i=0; i<N; i++){  
            result[i] = Arrays.copyOf(blocks[i], N);  
        }  
        return result; 
		
	}
	
	public static void main(String[] args){
        In in = new In(args[0]);  
        int N = in.readInt();  
        int[][] blocks = new int[N][N];  
        for (int i = 0; i < N; i++)  
            for (int j = 0; j < N; j++)  
                blocks[i][j] = in.readInt();  
        Board initial = new Board(blocks);  
        StdOut.println(initial);  
        StdOut.println(initial.hamming()); 
	}

}
