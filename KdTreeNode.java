public class KdTreeNode{
	double split;
    Object left;
    Object right;
    int depth;
    KdTreeNode(double split, Object left, Object right, int depth) {
        this.split = split;
        this.left = left;
        this.right = right;
        this.depth = depth;
    }
	public static int compare(int depth, double s, Point2D p) {
        boolean x_axis = (depth % 2 == 0); // if depth is even, compare x values else y values
        if (x_axis){
            if (s != p.x)
            	return (p.x < s ) ? -1 : 1;
        	return 0;
        }
        if (s != p.y)
            return (p.y < s) ? -1 : 1;
        return 0;
    }
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null)
//            return false;
//        if (!(obj instanceof KdTreeNode))
//            return false;
//
//        KdTreeNode kdTreeNode = (KdTreeNode) obj;
//        if (this.compareTo(kdTreeNode) == 0)
//            return true;
//        return false;
//    }
    public StringBuilder toString(StringBuilder prefix, boolean isTail, StringBuilder sb) {
        if(right != null) {
        	if (right instanceof KdTreeNode)
        		((KdTreeNode)right).toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        	else
        		((Point2D)right).toString(new StringBuilder().append(prefix).append(isTail ? "│   " : "    "), false, sb);
        }
        sb.append(prefix).append(isTail ? "└── " : "┌── ").append(split).append("\n");
        if(left != null) {
            if (left instanceof KdTreeNode)
            	((KdTreeNode)left).toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
            else
            	((Point2D)left).toString(new StringBuilder().append(prefix).append(isTail ? "    " : "│   "), true, sb);
        } 
        return sb;
    }
    @Override
    public String toString() {
        return this.toString(new StringBuilder(), true, new StringBuilder()).toString();
    }
}
