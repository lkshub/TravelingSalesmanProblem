package edu.gatech.cse6140;

import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.graph.Node;

import java.util.ArrayList;

public class CodeTester {
    public static void main(String[] args) {
        InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/nilakshdas/Grad School/Fall16/cse6140/Project/gatech-cse6140-fa16-z-tsp/data"
        );

        ArrayList<Node> nodes = ioHandler.getNodesFromTSPFile("Atlanta.tsp");

        for (Node node1: nodes) {
            for (Node node2: nodes) {
                System.out.println(node1.getId()+" "+node2.getId());
                System.out.println(node1.calculateDistanceFromNode(node2));
            }
        }
    }
}
