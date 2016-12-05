package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import org.junit.Test;

import static org.junit.Assert.*;


public class BranchAndBoundSolverTest {

    @Test
    public void testSolve() throws Exception {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Toronto.tsp"));

        TravelingSalesmanTour tour = new BranchAndBoundSolver(graph).solve(600);

        System.out.println(tour);
    }
}