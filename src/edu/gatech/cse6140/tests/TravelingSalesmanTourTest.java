package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

import static org.junit.Assert.*;
import org.junit.Test;

public class TravelingSalesmanTourTest {
    @Test
    public void testAddNode() throws Exception {
        Node node1 = new Node(1, "n", 0.0, 0.0);
        Node node2 = new Node(2, "n", 3.0, 0.0);
        Node node3 = new Node(3, "n", 3.0, 4.0);
        Node node4 = new Node(4, "n", 0.0, 4.0);

        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(node1);
        assert tour.containsNodeId(1);
        assertEquals(0, (int) tour.getTourCost());

        tour.addNode(node2);
        assert tour.containsNodeId(2);
        assertEquals(6, (int) tour.getTourCost());

        tour.addNode(node3);
        assert tour.containsNodeId(3);
        assertEquals(12, (int) tour.getTourCost());

        tour.addNode(node4);
        assert tour.containsNodeId(4);
        assertEquals(14, (int) tour.getTourCost());
    }

    @Test
    public void testAddNodeAtPosition() throws Exception {
        Node node1 = new Node(1, "n", 0.0, 0.0);
        Node node2 = new Node(2, "n", 3.0, 0.0);
        Node node3 = new Node(3, "n", 3.0, 4.0);
        Node node4 = new Node(4, "n", 0.0, 4.0);

        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(node1);
        tour.addNode(node3);
        tour.addNode(node4);
        assert tour.containsNodeId(1);
        assert tour.containsNodeId(3);
        assert tour.containsNodeId(4);
        assertEquals(12, (int) tour.getTourCost());

        assert !tour.containsNodeId(2);
        tour.addNode(1, node2);
        assert tour.containsNodeId(2);
        assertEquals(14, (int) tour.getTourCost());
    }

    @Test
    public void testRemoveNode() throws Exception {
        Node node1 = new Node(1, "n", 0.0, 0.0);
        Node node2 = new Node(2, "n", 3.0, 0.0);
        Node node3 = new Node(3, "n", 3.0, 4.0);

        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(node1);
        tour.addNode(node2);
        tour.addNode(node3);
        assert tour.containsNodeId(1);
        assert tour.containsNodeId(2);
        assert tour.containsNodeId(3);
        assertEquals(12, (int) tour.getTourCost());

        tour.removeNode(1);
        assert !tour.containsNodeId(2);
        assertEquals(10, (int) tour.getTourCost());
    }

    @Test
    public void testSwapNodes() throws Exception {
        Node node1 = new Node(1, "n", 0.0, 0.0);
        Node node2 = new Node(2, "n", 3.0, 0.0);
        Node node3 = new Node(3, "n", 3.0, 4.0);
        Node node4 = new Node(4, "n", 0.0, 4.0);

        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(node1);
        tour.addNode(node2);
        tour.addNode(node3);
        tour.addNode(node4);
        assertEquals(14, (int) tour.getTourCost());

        tour.swapNodes(2, 3);
        assertEquals(16, (int) tour.getTourCost());

        tour.swapNodes(3, 2);
        assertEquals(14, (int) tour.getTourCost());
    }

    @Test
    public void testGetTourCost() throws Exception {
        Node node1 = new Node(1, "n", 0.0, 0.0);
        Node node2 = new Node(2, "n", 3.0, 0.0);
        Node node3 = new Node(3, "n", 3.0, 4.0);
        Node node4 = new Node(4, "n", 0.0, 4.0);

        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(node1);
        assertEquals(0, (int) tour.getTourCost());

        tour.addNode(node2);
        assertEquals(6, (int) tour.getTourCost());

        tour.addNode(node3);
        assertEquals(12, (int) tour.getTourCost());

        tour.addNode(0, node4);
        assertEquals(14, (int) tour.getTourCost());
    }

    @Test
    public void testBostonTour() throws Exception {
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
        // tourBoston.addNode(graphBoston.getNode(39)); // added below
        tourBoston.addNode(graphBoston.getNode(18));
        tourBoston.addNode(graphBoston.getNode(3));
        tourBoston.addNode(graphBoston.getNode(21));
        tourBoston.addNode(graphBoston.getNode(23));
        tourBoston.addNode(graphBoston.getNode(4));
        tourBoston.addNode(graphBoston.getNode(38)); // extra
        tourBoston.addNode(graphBoston.getNode(31));
        tourBoston.addNode(graphBoston.getNode(19));
        tourBoston.addNode(graphBoston.getNode(30));
        tourBoston.addNode(graphBoston.getNode(24));
        tourBoston.addNode(graphBoston.getNode(38)); // duplicated above
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

        tourBoston.addNode(5, graphBoston.getNode(39));
        tourBoston.removeNode(11);
        tourBoston.swapNodes(11, 3);
        tourBoston.swapNodes(3, 11);

        assertEquals(893536, (int) tourBoston.getTourCost());
    }
}