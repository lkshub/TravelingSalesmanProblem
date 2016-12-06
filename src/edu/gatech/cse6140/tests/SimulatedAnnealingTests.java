package edu.gatech.cse6140.tests;

import java.util.ArrayList;

import org.junit.Test;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.local_search.SimulatedAnnealingSolver;

public class SimulatedAnnealingTests {

	@Test
	public void test() {
		InputOutputHandler ioHandler = new InputOutputHandler(
                "./data/"
        );
		ArrayList<Node> nodes = ioHandler.getNodesFromTSPFile("Boston.tsp");
		SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(new Graph(nodes), 13);
		TravelingSalesmanTour tour = solver.solve(100);
		System.out.println(tour);
		System.out.println(tour.getTourCost());
		
		Trace trace = solver.getTrace();
	}

}
