package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;
import org.junit.Test;


public class MinimumSpanningTreeApproximateSolverTest {
    @Test
    public void testMinimumSpanningTreeApproximateSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Cincinnati.tsp"));

        MinimumSpanningTreeApproximateSolver solver = new MinimumSpanningTreeApproximateSolver(graph);
       
        TravelingSalesmanTour tour = solver.solve(0);

        Trace trace = solver.getTrace();

        System.out.println(tour); // 1-3-10-2-9-6-4-8-5-7-1 for Cincinnati
        System.out.println(tour.getTourCost()); // 308737 for Cincinnati
    }
}