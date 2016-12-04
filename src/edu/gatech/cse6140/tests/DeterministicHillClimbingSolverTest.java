package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.DeterministicHillClimbingSolver;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


public class DeterministicHillClimbingSolverTest {
    @Test
    public void testDeterministicHillClimbingSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler("./data");

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Roanoke.tsp"));

        TravelingSalesmanTour tour = new DeterministicHillClimbingSolver(graph).solve(600);
    }
}