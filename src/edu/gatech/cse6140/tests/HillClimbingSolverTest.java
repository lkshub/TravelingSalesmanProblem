package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.HillClimbingSolver;
import org.junit.Test;

import static org.junit.Assert.*;


public class HillClimbingSolverTest {
    @Test
    public void testHillClimbingSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Roanoke.tsp"));

        TravelingSalesmanTour tour = new HillClimbingSolver(graph)
                .setRandomSeed(67)
                .solve(400, new BranchAndBoundSolver(graph).solve(200));
    }
}