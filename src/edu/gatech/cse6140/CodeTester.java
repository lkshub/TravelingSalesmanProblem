package edu.gatech.cse6140;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;

import java.util.ArrayList;

public class CodeTester {
    private static void testTour() {
        InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/nilakshdas/Grad School/Fall16/cse6140/Project/gatech-cse6140-fa16-z-tsp/data"
        );

        Graph graphBoston = new Graph(ioHandler.getNodesFromTSPFile("Boston.tsp"));

        TravelingSalesmanTour tourBoston = new TravelingSalesmanTour();

        tourBoston.addNode(graphBoston.getNode(0));
        tourBoston.addNode(graphBoston.getNode(20));
        tourBoston.addNode(graphBoston.getNode(1));
        tourBoston.addNode(graphBoston.getNode(26));
        tourBoston.addNode(graphBoston.getNode(17));
        tourBoston.addNode(graphBoston.getNode(39));
        tourBoston.addNode(graphBoston.getNode(18));
        tourBoston.addNode(graphBoston.getNode(3));
        tourBoston.addNode(graphBoston.getNode(21));
        tourBoston.addNode(graphBoston.getNode(23));
        tourBoston.addNode(graphBoston.getNode(4));
        tourBoston.addNode(graphBoston.getNode(31));
        tourBoston.addNode(graphBoston.getNode(19));
        tourBoston.addNode(graphBoston.getNode(30));
        tourBoston.addNode(graphBoston.getNode(24));
        tourBoston.addNode(graphBoston.getNode(38));
        tourBoston.addNode(graphBoston.getNode(29));
        tourBoston.addNode(graphBoston.getNode(36));
        tourBoston.addNode(graphBoston.getNode(25));
        tourBoston.addNode(graphBoston.getNode(32));
        tourBoston.addNode(graphBoston.getNode(11));
        tourBoston.addNode(graphBoston.getNode(33));
        tourBoston.addNode(graphBoston.getNode(22));
        tourBoston.addNode(graphBoston.getNode(37));
        tourBoston.addNode(graphBoston.getNode(28));
        tourBoston.addNode(graphBoston.getNode(27));
        tourBoston.addNode(graphBoston.getNode(12));
        tourBoston.addNode(graphBoston.getNode(6));
        tourBoston.addNode(graphBoston.getNode(35));
        tourBoston.addNode(graphBoston.getNode(5));
        tourBoston.addNode(graphBoston.getNode(8));
        tourBoston.addNode(graphBoston.getNode(7));
        tourBoston.addNode(graphBoston.getNode(14));
        tourBoston.addNode(graphBoston.getNode(16));
        tourBoston.addNode(graphBoston.getNode(13));
        tourBoston.addNode(graphBoston.getNode(2));
        tourBoston.addNode(graphBoston.getNode(15));
        tourBoston.addNode(graphBoston.getNode(34));
        tourBoston.addNode(graphBoston.getNode(10));
        tourBoston.addNode(graphBoston.getNode(9));

        System.out.println(tourBoston.getTourCost()); // 893536

        ioHandler.putTravelingSalesmanTourInTourFile(tourBoston, "Boston_test.tour");
    }

    public static void testBranchAndBoundSolver() {
        InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/nilakshdas/Grad School/Fall16/cse6140/Project/gatech-cse6140-fa16-z-tsp/data"
        );

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Cincinnati.tsp"));

        BranchAndBoundSolver solver = new BranchAndBoundSolver(graph);

        TravelingSalesmanTour tour = solver.solve();

        System.out.println(tour.getTourCost());
    }

    public static void main(String[] args) {
        testBranchAndBoundSolver();
    }
}
