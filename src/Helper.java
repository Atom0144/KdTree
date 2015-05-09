import java.util.Comparator;
import java.util.List;
// Helper class: contains common methods
public final class Helper {
	// comparators for sorting collections
	public static final Comparator<Point2D> x_comparator = new Comparator<Point2D>() {
        public int compare(Point2D p1, Point2D p2) {
            if (p1.x != p2.x)
            	return (p1.x < p2.x) ? -1 : 1;
        	return 0;
        }
    };
    public static final Comparator<Point2D> y_comparator = new Comparator<Point2D>() {
        public int compare(Point2D p1, Point2D p2) {
            if (p1.y != p2.y)
            	return (p1.y < p2.y) ? -1 : 1;
        	return 0;
        }
    };
    // point in rectangle test
    public static final boolean inRectangle(Point2D k, Region q){
    	return ((k.x >= q.xl && k.x <= q.xh) && (k.y >= q.yl && k.y <= q.yh));
    }
    // range in range test: k fully contained in q
    public static final boolean fullyContainedRegions(Region k, Region q){
    	// if k is unbounded, then it's not fully contained in q
    	if ((k.xl  != -999) && (k.xh  != 999) && (k.yl  != -999) && (k.yh  != 999)){
    		return ((k.xl >= q.xl) && (k.xh <= q.xh) && (k.yl >= q.yl) && (k.yh <= q.yh));
    	}
    	return false;
    }
    // range intersects region test: k intersects q
    public static final boolean IntersectsRegion(Region r, Region k){
    	//return !(((k.xl  != -999) && (r.xh < k.xl)) || ((k.xh  != 999) && (r.xl > k.xh)) || ((k.yl  != -999) && (r.yh < k.yl)) || ((k.yh  != 999) && (r.yh < k.yh)));
    	return ((k.xh >= r.xl) && (r.xh >= k.xl) && (k.yh >= r.yl) && (r.yh >= k.yl));
    }
    public static final void ReportSubTree(Object v, List<Point2D> found){
    	if (v == null) return;
    	if (v instanceof KdTreeNode){ 
    		ReportSubTree(((KdTreeNode)v).left, found);
    		ReportSubTree(((KdTreeNode)v).right, found);
    	}
    	else
    		found.add((Point2D)v);//System.out.println("Point found: (" + ((Point2D)v).x + ", " + ((Point2D)v).y + ")");
    	}
    }
