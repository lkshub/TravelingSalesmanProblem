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

        BranchAndBoundSolver bnb_solver = new BranchAndBoundSolver(graph);
        
        TravelingSalesmanTour tour = bnb_solver.solve(10);
        
        bnb_solver.getTraceFile().createTraceFile("Toronto", "BnB", 10, -1);
        
        System.out.println(tour);
    }
}