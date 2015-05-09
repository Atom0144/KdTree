import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

// for plotting
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.*;
public class Main {
    private static ArrayList<Point2D> readPointsFromFile(Path file){
    	ArrayList<Point2D> input_list = new ArrayList<Point2D>();
    	String[] s_point = null;
    	try{
    	InputStream in = Files.newInputStream(file);
    	BufferedReader reader = new BufferedReader(new InputStreamReader(in));
    	String line = null;
    	while ((line = reader.readLine()) != null){
    		s_point = line.split(",");
    		Point2D p = new Point2D(Double.valueOf(s_point[0].trim()), Double.valueOf(s_point[1].trim()));
    		input_list.add(p);
    	}
    	}catch(IOException e){
    		System.out.println("File not found");
    	}
    	return input_list;
    }
    private static void writeRandomPointsToFile(String file, int count){
    	try{
    	PrintWriter writer = new PrintWriter(file);
    	while(count-- >0){
    		double x = Math.random();
    		double y = Math.random();
        	writer.println(x + "," + y);
    	}
    	writer.close();
    	}
    	catch(IOException e){
    		System.out.println("file not found");
    	}
    	
    }
    private static void naiveSearch(ArrayList<Point2D> input_list, Region q, ArrayList<Point2D> found_list){
    	for (Point2D p : input_list)
    		if (Helper.inRectangle(p, q))
    			found_list.add(p);//System.out.println("Point found: (" + p.x + ", " + p.y + ")");    		
    }
    
    private static void plotRunTime(int end, int step){
    	//XYDataset d1 = createDataset();
    	//XYDataset d2 = createDataset();
    	XYSeriesCollection seriesCollection = new XYSeriesCollection();
    	XYSeries kdTreeData = new XYSeries("Kd-Tree search");
    	XYSeries naiveData = new XYSeries("Naive search");
    	//XYSeries rTreeData = new XYSeries("Range-Tree search");
    	long[] result;
    	for (int i=10; i <= end; i=i+step){
    		result = search(i);
    		kdTreeData.add((double)i, (double)result[0]);
    		naiveData.add((double)i, (double)result[1]);
    		//rTreeData.add((double)i, (double)result[2]);
    	}
    	seriesCollection.addSeries(kdTreeData);
    	seriesCollection.addSeries(naiveData);
    	//seriesCollection.addSeries(rTreeData);
    	JFreeChart chart = ChartFactory.createScatterPlot("Runtime Analysis", "# of points", "Time in ms", seriesCollection, PlotOrientation.VERTICAL, true, true, false);
        XYPlot plot = (XYPlot)chart.getPlot();
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(0, true);
                
        renderer.setSeriesLinesVisible(1, false);
        renderer.setSeriesShapesVisible(1, true);
        
//        renderer.setSeriesLinesVisible(2, false);
//        renderer.setSeriesShapesVisible(2, true);
        
        plot.setRenderer(renderer);
        
        // create and display a frame...
        ChartFrame frame = new ChartFrame("Runtime Analysis: ", chart);
        frame.pack();
        frame.setVisible(true);
    }
    public static long[] search(int n){
    	String in_path = "/home/satish/workspace/KdTree2/src/input";
    	writeRandomPointsToFile(in_path, n);
    	Path file = Paths.get(in_path);
		ArrayList<Point2D> input_list = readPointsFromFile(file);
		ArrayList<Point2D> found_list1 = new ArrayList<Point2D>();
		ArrayList<Point2D> found_list2 = new ArrayList<Point2D>();
    	double x, y;
    	x = Math.random();
    	y = Math.random();
		Region query = new Region(x, x+0.1, y, y+0.1);
    	//Region query = new Region(0, 1, 0, 1);
    	//long start1 = System.nanoTime();
		KdTree kdTree = new KdTree(input_list);
//		System.out.println("Kd-Tree:");
//		System.out.println(kdTree.root);
		long start1 = System.nanoTime();
		kdTree.searchKdTree(kdTree.root, query, null, 0, found_list1);
    	long end1 = System.nanoTime();
    	naiveSearch(input_list, query, found_list2);
    	long end2 = System.nanoTime();
    	
//    	// range tree search
//    	RangeTree<Double> st = new RangeTree<Double>();
//    	for (Point2D p : input_list)
//    		st.insert(p.x, p.y);
//    	Interval<Double> intX = new Interval<Double>(x, x+0.1);
//        Interval<Double> intY = new Interval<Double>(y, y+0.1);
//        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
//    	long start3 = System.nanoTime();
//        st.query2D(rect);
//        long end3 = System.nanoTime();
    	
//    	System.out.println("KdTree search - time taken:" + (end1 - start1)/10000);
//    	System.out.println("Naive search - time taken:" + (end2 - end1)/10000);
    	long[] result = new long[2];
    	result[0] = (end1 - start1)/10000;
    	result[1] = (end2 - end1)/10000;
//    	result[2] = (end3 - start3)/10000;
//    	System.out.println(result[0] + "," + result[1]);
    	return result;
    }
    public static void search_r(int n){
    	String in_path = "/home/satish/workspace/KdTree2/src/input";
    	writeRandomPointsToFile(in_path, n);
    	Path file = Paths.get(in_path);
		ArrayList<Point2D> input_list = readPointsFromFile(file);
		ArrayList<Point2D> found_list1 = new ArrayList<Point2D>();
		ArrayList<Point2D> found_list2 = new ArrayList<Point2D>();
    	double x, y;
    	x = Math.random();
    	y = Math.random();
		Region query = new Region(x, x+0.1, y, y+0.1);
		System.out.println("Searching for region: [" + query.xl + "," + query.yl + "] X " + " [" + query.xh + "," + query.yh + "]");
		KdTree kdTree = new KdTree(input_list);
    	System.out.println("Kd-Tree:");
    	System.out.println(kdTree.root);
    	kdTree.searchKdTree(kdTree.root, query, null, 0, found_list2);
    	naiveSearch(input_list, query, found_list1);
    	
    	// range tree search
//    	RangeTree<Double> st = new RangeTree<Double>();
//    	for (Point2D p : input_list)
//    		st.insert(p.x, p.y);
//    	Interval<Double> intX = new Interval<Double>(x, x+0.1);
//        Interval<Double> intY = new Interval<Double>(y, y+0.1);
//        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
//    	st.query2D(rect);
    	
        //results
    	System.out.println("Kd-Tree search output:");
    	for (Point2D p: found_list1)
    		System.out.println(p.x + "," + p.y);
    	System.out.println("Naive search output:");
    	for (Point2D p: found_list1)
    		System.out.println(p.x + "," + p.y);
    	
    	
    }
    public static void testRun(){
    	Region query = new Region(-5,-2,-5,1);
    	ArrayList<Point2D> input_list = new ArrayList<Point2D>();
		ArrayList<Point2D> found_list1 = new ArrayList<Point2D>();
		ArrayList<Point2D> found_list2 = new ArrayList<Point2D>();
    	input_list.add(new Point2D(-5,3));
    	input_list.add(new Point2D(-2,6));
    	input_list.add(new Point2D(4,5));
    	input_list.add(new Point2D(3,2));
    	input_list.add(new Point2D(1,1));
    	input_list.add(new Point2D(2,-2));
    	input_list.add(new Point2D(-4,-1));
    	input_list.add(new Point2D(-3,-4));
    	input_list.add(new Point2D(4,-4));
    	input_list.add(new Point2D(-2.5,-2.5));
    	input_list.add(new Point2D(2.75,-3));
    	input_list.add(new Point2D(2,2));
    	input_list.add(new Point2D(-2.5,3));
    	System.out.println("Searching for region: [" + query.xl + "," + query.yl + "] X " + " [" + query.xh + "," + query.yh + "]");
    	KdTree kdTree = new KdTree(input_list);
    	System.out.println("Kd-Tree:");
    	System.out.println(kdTree.root);
    	kdTree.searchKdTree(kdTree.root, query, null, 0, found_list2);
    	naiveSearch(input_list, query, found_list1);
    	System.out.println("Kd-Tree search output:");
    	for (Point2D p: found_list1)
    		System.out.println(p.x + "," + p.y);
    	System.out.println("Naive search output:");
    	for (Point2D p: found_list1)
    		System.out.println(p.x + "," + p.y);
    }
    public static void main(String[] args) {
    	String[] largs = args[0].split(",");
    	if (largs.length == 3)
    		plotRunTime(Integer.parseInt(largs[1]), Integer.parseInt(largs[2]));
    	else if (largs.length == 1 && "test".equals(largs[0]))
    		testRun();
    	else if (largs.length == 2 && "search".equals(largs[0]))
    		search_r(Integer.parseInt(largs[1]));
    	else
    		System.out.println("Invalid argument(s)");
    }
}