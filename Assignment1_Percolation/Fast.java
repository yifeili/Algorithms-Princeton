import java.util.Arrays;

public class Fast {
	public static void main(String[] args){
		StdDraw.setXscale(0,32768);
		StdDraw.setYscale(0,32768);
		
		String filename = args[0];
		In a = new In(filename);
		int N = a.readInt();
		if (N < 4) return;
		Point[] pointArray = new Point[N];
		for (int i = 0; i < N; i++)
		{
			int x = a.readInt();
			int y = a.readInt();
			Point p = new Point(x,y);
			pointArray[i] = p;
			p.draw();
		}

		FastMethod(pointArray);
		StdDraw.show(0);
	}
	
	private static void FastMethod(Point[] pointArray){
		int N = pointArray.length; 
		
		for (int i=0; i<N; i++)
		{  
            Point origPoint = pointArray[i];  
            Point[] otherPoint = new Point[N-1];  
            for (int j=0; j<pointArray.length; j++)   // Copy the array of pointArray[] to another array without the original point.
            {  
                if (j < i) otherPoint[j] = pointArray[j];  
                if (j > i) otherPoint[j-1] = pointArray[j];  
            }  
            Arrays.sort(otherPoint, origPoint.SLOPE_ORDER);  
              
            int count = 0;  
            int index = 0;  
            double tempSlope = origPoint.slopeTo(otherPoint[0]);  
            
            for (int j=0; j<otherPoint.length;j++)
            {  
                if (Double.compare(origPoint.slopeTo(otherPoint[j]),  tempSlope) == 0)  // Based on one slope like tempSlope, check whether there are other slopes in the new array equaling to it. 
                {  
                    count++;  
                    continue;  
                }
                else
                {  
                    if (count >=3) // If we find more than 4 points on one line, then print their positions and draw the line. Use compareTo method to avoid repeating drawing.
                    {  	
                        if (otherPoint[index].compareTo(origPoint) >=0)
                        {  
                            StdOut.print(origPoint + " -> ");  
                            for (int k=index; k<j-1; k++)
                            {  
                                StdOut.print(otherPoint[k] + " -> ");  
                            }  
                            StdOut.println(otherPoint[j-1]);  
                            origPoint.drawTo(otherPoint[j-1]);  
                        }  
                    }  
                    // Whether we find more than 4 points on one line or not, use the current slope as the new temSlope and default all the other variables. 
                    count = 1;   		
                    index = j;  
                    tempSlope = origPoint.slopeTo(otherPoint[j]);  
                }  
            }  
            if (count >= 3){  
                if (otherPoint[index].compareTo(origPoint) >= 0){  
                    StdOut.print(origPoint + " -> ");  
                    for (int k=index; k<otherPoint.length - 1; k++){  
                        StdOut.print(otherPoint[k] + " -> ");  
                    }  
                    StdOut.println(otherPoint[otherPoint.length-1]);  
                    origPoint.drawTo(otherPoint[otherPoint.length-1]);  
                }  
            }  
        }  
        StdDraw.show(0);  
    }  
	
}
