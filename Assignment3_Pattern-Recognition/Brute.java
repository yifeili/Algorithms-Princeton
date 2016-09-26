
public class Brute {
 public static void main(String[] args){
  StdDraw.setXscale(0,32768);
  StdDraw.setYscale(0,32768);
  
  String filename = args[0];
  In a = new In(filename);
  int N = a.readInt();
  if (N < 4) return;
  Point[] pointArray = new Point[N];
  for (int i = 0; i < N; i++){
   int x = a.readInt();
   int y = a.readInt();
   Point p = new Point(x,y);
   pointArray[i] = p;
   p.draw();
  }

  BruteForce(pointArray);
  StdDraw.show(0);
 }
  
 private static void BruteForce(Point[] pointArray){
   int N = pointArray.length;
   for (int i=0; i<N; i++)
    for (int j=i+1; j<N; j++)
     for (int k=j+1; k<N; k++)
      for (int l=k+1; l<N; l++)
       if (pointArray[i].slopeTo(pointArray[j]) == pointArray[i].slopeTo(pointArray[k]) && 
        pointArray[i].slopeTo(pointArray[j]) == pointArray[i].slopeTo(pointArray[l]))
        {
         StdOut.println(pointArray[i] + " -> " + pointArray[j] + " -> " 
         + pointArray[k] + " -> " + pointArray[l]);
         pointArray[i].drawTo(pointArray[l]); 
        }
 }

}
