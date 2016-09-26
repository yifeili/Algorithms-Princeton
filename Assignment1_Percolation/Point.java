import java.util.Comparator;

public class Point implements Comparable<Point> {
   public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();        // compare points by slope to this point

   public final int x, y;
   public Point(int x, int y)                         // construct the point (x, y)
   {
	   this.x = x;
	   this.y = y;
   }

   public class SlopeOrder implements Comparator<Point>
   {
	   public int compare(Point p1, Point p2)
	   {
		   if (slopeTo(p1) < slopeTo(p2)) return -1;
		   if (slopeTo(p1) == slopeTo(p2)) return 0;
		   else return +1;
	   }
   }
   
   public void draw()                               // draw this point
   {
	   StdDraw.point(x,y);
   }
   public void drawTo(Point that)                   // draw the line segment from this point to that point
   {
	   StdDraw.line(this.x, this.y, that.x, that.y);
   }
   public String toString()                           // string representation
   {
	   return "(" + x + "," + y + ")";
   }

   public int compareTo(Point that)                // is this point lexicographically smaller than that point?
   {
	   if (this.y < that.y ) return -1;
	   if (this.y == that.y && this.x < that.x ) return -1;
	   if (this.y == that.y && this.x == that.x) return 0;
	   else return +1;
   }
   
   public double slopeTo(Point that)                  // the slope between this point and that point
   { 
	   double dx = that.x - this.x;
	   double dy = that.y - this.y;
	   if (dx == 0) return Double.POSITIVE_INFINITY;
	   else if (dx == 0 && dy == 0) return Double.NEGATIVE_INFINITY;
	   else if (dy == 0) return +0;
	   else return dy/dx;
	   
   }
}