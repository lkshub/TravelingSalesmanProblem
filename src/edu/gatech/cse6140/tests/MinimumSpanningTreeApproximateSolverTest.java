package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;
import org.junit.Test;


public class MinimumSpanningTreeApproximateSolverTest {
    @Test
    public void testMinimumSpanningTreeApproximateSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/nilakshdas/Grad School/Fall16/cse6140/Project/gatech-cse6140-fa16-z-tsp/data"
        );

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Roanoke.tsp"));

        TravelingSalesmanProblemSolver solver = new MinimumSpanningTreeApproximateSolver(graph);

        TravelingSalesmanTour tour = solver.solve(0);

        System.out.println(tour);
        System.out.println(tour.getTourCost());
    }
}