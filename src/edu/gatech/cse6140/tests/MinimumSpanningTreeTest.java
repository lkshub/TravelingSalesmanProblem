package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.MinimumSpanningTree;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class MinimumSpanningTreeTest {

    @Test
    public void testGetCost() throws Exception {
        ArrayList<Node> nodes = new ArrayList<>();

        nodes.add(new Node(0, "0", 0.0, 0.0));
        nodes.add(new Node(1, "1", 0.0, 1.0));
        nodes.add(new Node(2, "2", 0.0, 2.0));
        nodes.add(new Node(3, "3", 0.0, 3.0));

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(nodes);

        assertEquals(3, minimumSpanningTree.getCost());
    }

    @Test
    public void testGetNeighbors() throws Exception {
        ArrayList<Node> nodes = new ArrayList<>();

        nodes.add(new Node(0, "0", 0.0, 0.0));
        nodes.add(new Node(1, "1", 0.0, 1.0));
        nodes.add(new Node(2, "2", 0.0, 2.0));
        nodes.add(new Node(3, "3", 0.0, 3.0));

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(nodes);

        assertEquals(1, minimumSpanningTree.getNeighbors(0).size());
        assertEquals(2, minimumSpanningTree.getNeighbors(1).size());
        assertEquals(2, minimumSpanningTree.getNeighbors(2).size());
        assertEquals(1, minimumSpanningTree.getNeighbors(3).size());
    }

    @Test
    public void testMinimumSpanningTreeOnCincinnati() {
        InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/nilakshdas/Grad School/Fall16/cse6140/Project/gatech-cse6140-fa16-z-tsp/data"
        );

        Graph graph = new Graph(ioHandler.getNodesFromTSPFile("Cincinnati.tsp"));

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph.getNodes());

        System.out.println(minimumSpanningTree.getCost());
    }
}