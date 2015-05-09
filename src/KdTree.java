import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KdTree{
	Object root = null;
	public KdTree(List<Point2D> P){
		root = buildKdTree(P, 0);
	}
	private static Object buildKdTree(List<Point2D> P, int depth){
		boolean x_axis = (depth%2==0);
		// if P contains only one point, return a leaf storing this point
		if (P.size() == 1)
			return new Point2D(P.get(0).x, P.get(0).y);
		// if depth is odd, sort along x axis else y axis to get the split node
		if (x_axis)
			Collections.sort(P, Helper.x_comparator);
		else
			Collections.sort(P, Helper.y_comparator);
		int mid = P.size()/2;
		// create split node
		KdTreeNode v = new KdTreeNode(x_axis ? P.get(mid).x : P.get(mid).y, null, null, depth);
		List<Point2D> P1 = new ArrayList<Point2D>();
		List<Point2D> P2 = new ArrayList<Point2D>();
		// add points left of v to P1 and right of v to P2
		if (P.size() > 2)
			for (int i = 0; i < P.size(); i++){
	//			if (i!= mid){
				Point2D p = P.get(i);
				// comparing along x or y axis based on depth is handled in compareTo function 
				if (KdTreeNode.compare(depth, v.split, p) <= 0)
					P1.add(p);
				else
					P2.add(p);
	//			}
			}
		else
		{
			Point2D p1 = P.get(0);
			Point2D p2 = P.get(1);
			if (KdTreeNode.compare(depth, v.split, p1) < 0){
				P1.add(p1);
				P2.add(p2);
			}
			else{
				P1.add(p2);
				P2.add(p1);
			}
		}
		// build KdTree for P1 and P2 and set them as left and right child of v 
		Object vLeft = null; 
		Object vRight = null;
		if (P1.size() > 0){
			vLeft = buildKdTree(P1, depth+1);
		}
		if (P2.size() > 0){
			vRight = buildKdTree(P2, depth+1);
		}
		v.left = vLeft;
		v.right = vRight;
		return v;
	}
	void searchKdTree(Object v, Region k, Region r, int depth, List<Point2D> found){
		if (v instanceof Point2D){
			if (Helper.inRectangle((Point2D)v, k))
				found.add((Point2D)v);//System.out.println("Point found: (" + ((Point2D)v).x + ", " + ((Point2D)v).y + ")");
		}
		else{
			boolean x_axis = (depth%2== 0);
			Region left = (r == null) ? new Region(): new Region(r.xl, r.xh, r.yl, r.yh);
			Region right = (r == null) ? new Region(): new Region(r.xl, r.xh, r.yl, r.yh);
			if (x_axis){
		    	left.xh = ((KdTreeNode)v).split;
		    	right.xl = ((KdTreeNode)v).split;
			}
			else{
				left.yh = ((KdTreeNode)v).split;
		    	right.yl = ((KdTreeNode)v).split;
			}
			// if region(leftChild(v)) is fully contained in R
			// report subtree(leftChild(v))
			// else search recursively in lower levels
			if (Helper.fullyContainedRegions(left, k)) 
				Helper.ReportSubTree(((KdTreeNode)v).left, found);
			else if (Helper.IntersectsRegion(left, k))
				searchKdTree(((KdTreeNode)v).left, k, left, depth+1, found);
			// if region(rightChild(v)) is fully contained in R
			// report subtree(rightChild(v))
			// else search recursively in lower levels
			if (Helper.fullyContainedRegions(right, k)) 
				Helper.ReportSubTree(((KdTreeNode)v).right, found);
			else if (Helper.IntersectsRegion(right, k))
				searchKdTree(((KdTreeNode)v).right, k, right, depth+1, found);
		}
	}
}