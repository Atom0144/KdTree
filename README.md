This is an implementation of the KdTree according to the algorithm from the book Computational Geometry: Algorithms and Applications by de Berg et.al

Implementation only supports 2D range queries. This was meant to be an experiment to compare runtimes between the naive seach and a KDTree search and so doesn't include support for typical KDTree features such as nearest neighbour search and doesn't support higher dimensions.

The tool can be run in three modes:

1. Test run : Runs KdTree search for a predefined test input and prints the tree as console putput
> ./RunKdTreeExp test 

2. Search: Runs naive search and KdTree search for a number of random points specified
> ./RunKdTreeExp search,10

3. Experiment mode: Runs naive search and KdTree search for the number of random points specified in input. Eg: Below command runs experiment for n = 10000 with steps 100. So n1=100, n2=200, n3=300, etc. to ni=10000
> ./RunKdTreeExp exp,10000,100

Everytime the code is compiled and executed. So no make step is required. Only external dependency is the library to plot graphs included under /lib (downloaded from here: http://www.jfree.org/jfreechart/) .
