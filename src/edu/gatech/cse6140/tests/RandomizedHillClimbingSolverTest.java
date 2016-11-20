package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.RandomizedHillClimbingSolver;
import org.junit.Test;


public class RandomizedHillClimbingSolverTest {
    @Test
    public void testRandomizedHillClimbingSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Roanoke.tsp"));

        TravelingSalesmanTour tour = new RandomizedHillClimbingSolver(graph)
                .setRandomSeed(67)
                .solve(400, new BranchAndBoundSolver(graph).solve(200));
    }
}