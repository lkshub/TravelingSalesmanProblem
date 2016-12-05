package edu.gatech.cse6140.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.io.TraceFile;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.SimulatedAnnealingSolver;

public class SimulatedAnnealingTests {

	@Test
	public void test() {
		InputOutputHandler ioHandler = new InputOutputHandler(
                "./data/"
        );
		ArrayList<Node> nodes = ioHandler.getNodesFromTSPFile("Boston.tsp");
		SimulatedAnnealingSolver solver = new SimulatedAnnealingSolver(1, nodes);
		TravelingSalesmanTour tour = solver.solve(10);
		System.out.println(tour);
		System.out.println(tour.getTourCost());
		
		TraceFile trace = solver.getTraceFile();
		trace.createTraceFile("Boston", "SimulatedAnnealing", 10, 1);
	}

}
