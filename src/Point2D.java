public class Point2D implements Comparable<Point2D>{
	double x;
	double y;
	
	public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }
//	private static final double distance(Point2D p1, Point2D p2) {
//        return Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
//    };
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!(obj instanceof Point2D))
            return false;

        Point2D point = (Point2D) obj;
        return compareTo(point) == 0;
    }
    @Override
    public int compareTo(Point2D p) {
        
        if (p.x != this.x)
        	return (this.x < p.x) ? -1 : 1;
        if (p.y != this.y)
        	return (this.y < p.y) ? -1 : 1;
        return 0;
    }
//    @Override
//    public String toString() {
//        return "(" + x + ", " + y + ")" ;
//    }
    public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb){
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append("(" + x + ", " + y + ")").append("\n");
        return sb;
    }
    @Override
    public String toString() {
        return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
    }
    
}
