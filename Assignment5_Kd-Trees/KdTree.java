import java.util.TreeSet;

public class KdTree {
    private static class KdNode {
        private KdNode leftNode;
        private KdNode rightNode;
        private final boolean isVertical;
        private final double x;
        private final double y;

        public KdNode(final double x, final double y, final KdNode leftNode,
                      final KdNode rightNode, final boolean isVertical) {
            this.x = x;
            this.y = y;
            this.leftNode = leftNode;
            this.rightNode = rightNode;
            this.isVertical = isVertical;
        }
    }

    private static final RectHV CONTAINER = new RectHV(0, 0, 1, 1);
    private KdNode rootNode;
    private int size;

    // construct an empty set of points
    public KdTree() {
        this.size = 0;
        this.rootNode = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // number of points in the set
    public int size() {
        return this.size;
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(final Point2D p) {
        this.rootNode = insert(this.rootNode, p, true);
    }

    private KdNode insert(final KdNode node, final Point2D p,
                          final boolean isVertical) {
        // if new node, create it
        if (node == null) {
            size++;
            return new KdNode(p.x(), p.y(), null, null, isVertical);
        }

        // if already in, return it
        if (node.x == p.x() && node.y == p.y()) {
            return node;
        }

        // insert it where corresponds (left - right recursive call)
        if (node.isVertical && p.x() < node.x || !node.isVertical
                && p.y() < node.y) {
            node.leftNode = insert(node.leftNode, p, !node.isVertical);
        } else {
            node.rightNode = insert(node.rightNode, p, !node.isVertical);
        }
        return node;
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(rootNode, p.x(), p.y());
    }

    private boolean contains(KdNode node, double x, double y) {
        if (node == null) {
            return false;
        }

        if (node.x == x && node.y == y) {
            return true;
        }

        if (node.isVertical && x < node.x || !node.isVertical && y < node.y) {
            return contains(node.leftNode, x, y);
        } else {
            return contains(node.rightNode, x, y);
        }
    }

    // draw all of the points to standard draw
    public void draw() {
        StdDraw.setScale(0, 1);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        CONTAINER.draw();

        draw(rootNode, CONTAINER);
    }

    private void draw(final KdNode node, final RectHV rect) {
        if (node == null) {
            return;
        }

        // draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        new Point2D(node.x, node.y).draw();
        
        // get the min and max points of division line
        Point2D min, max;
        if (node.isVertical) {
            StdDraw.setPenColor(StdDraw.RED);
            min = new Point2D(node.x, rect.ymin());
            max = new Point2D(node.x, rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            min = new Point2D(rect.xmin(), node.y);
            max = new Point2D(rect.xmax(), node.y);
        }
        
        // draw that division line
        StdDraw.setPenRadius();
        min.drawTo(max);

        // recursively draw children
        draw(node.leftNode, leftRect(rect, node));
        draw(node.rightNode, rightRect(rect, node));
    }

    private RectHV leftRect(final RectHV rect, final KdNode node) {
        if (node.isVertical) {
            return new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
        } else {
            return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
        }
    }

    private RectHV rightRect(final RectHV rect, final KdNode node) {
        if (node.isVertical) {
            return new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            return new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
        }
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(final RectHV rect) {
        final TreeSet<Point2D> rangeSet = new TreeSet<Point2D>();
        range(rootNode, CONTAINER, rect, rangeSet);
        
        return rangeSet;
    }

    private void range(final KdNode node, final RectHV nrect,
                       final RectHV rect, final TreeSet<Point2D> rangeSet) {
        if (node == null)
            return;
        
        if (rect.intersects(nrect)) {
            final Point2D p = new Point2D(node.x, node.y);
            if (rect.contains(p))
                rangeSet.add(p);
            range(node.leftNode, leftRect(nrect, node), rect, rangeSet);
            range(node.rightNode, rightRect(nrect, node), rect, rangeSet);
        }
    }

    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(final Point2D p) {
        return nearest(rootNode, CONTAINER, p.x(), p.y(), null);
    }

    private Point2D nearest(final KdNode node, final RectHV rect,
                            final double x, final double y, final Point2D candidate) {
        if (node == null){
            return candidate;
        }

        double dqn = 0.0;
        double drq = 0.0;
        RectHV left = null;
        RectHV rigt = null;
        final Point2D query = new Point2D(x, y);
        Point2D nearest = candidate;

        if (nearest != null) {
            dqn = query.distanceSquaredTo(nearest);
            drq = rect.distanceSquaredTo(query);
        }

        if (nearest == null || dqn > drq) {
            final Point2D point = new Point2D(node.x, node.y);
            if (nearest == null || dqn > query.distanceSquaredTo(point))
                nearest = point;
            
            if (node.isVertical) {
                left = new RectHV(rect.xmin(), rect.ymin(), node.x, rect.ymax());
                rigt = new RectHV(node.x, rect.ymin(), rect.xmax(), rect.ymax());
                
                if (x < node.x) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, rigt, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            } else {
                left = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.y);
                rigt = new RectHV(rect.xmin(), node.y, rect.xmax(), rect.ymax());
                
                if (y < node.y) {
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                    nearest = nearest(node.rightNode, rigt, x, y, nearest);
                } else {
                    nearest = nearest(node.rightNode, rigt, x, y, nearest);
                    nearest = nearest(node.leftNode, left, x, y, nearest);
                }
            }
        }

        return nearest;
    }
}