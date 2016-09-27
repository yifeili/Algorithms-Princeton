/* Bruteforce implementation */
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> set;

    // construct an empty set of points
    public PointSET(){
        set = new TreeSet<Point2D>();
    }
 
    // is the set empty?
    public boolean isEmpty(){
        return set.size() == 0;
    }
 
    // number of points in the set
    public int size(){
        return set.size();
    }
 
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p){
        set.add(p);
    }
 
    // does the set contain the point p?
    public boolean contains(Point2D p){
        return set.contains(p);
    }
 
    // draw all of the points to standard draw
    public void draw(){
        for (Point2D p : pointSET) {
            StdDraw.point(p.x(), p.y());
        }
    }
 
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        for (Point2D p : pointSET) {
            if (rect.contains(p)){
                rangeSet.add(p);
            }
        }
        return rangeSet;
    }
 
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p){
        Point2D nearestPoint2D = null;
        double distance = Double.MAX_VALUE;
  
        if (this.pointSET.isEmpty()){
            return nearestPoint2D;
        }
  
        for (Point2D pointIter : pointSET) {
            if (pointIter.distanceTo(p) < distance){
            nearestPoint2D = pointIter;
            distance = pointIter.distanceTo(p);
            }
        }
        return nearestPoint2D;
    }
}
