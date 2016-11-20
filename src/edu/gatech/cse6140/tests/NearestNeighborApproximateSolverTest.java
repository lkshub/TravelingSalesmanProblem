package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;
import org.junit.Test;

import static org.junit.Assert.*;


public class NearestNeighborApproximateSolverTest {
    @Test
    public void testNearestNeighborApproximateSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Roanoke.tsp"));

        TravelingSalesmanTour tour = new NearestNeighborApproximateSolver(graph).solve(0);

        System.out.println(tour.getTourCost());
        System.out.println(tour);
    }
}