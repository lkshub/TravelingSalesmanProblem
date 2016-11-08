package edu.gatech.cse6140.tsp;

import edu.gatech.cse6140.graph.Node;

import java.util.ArrayList;

public class TravelingSalesmanTour {
    private Integer tourCost = 0;
    private ArrayList<Node> orderedNodes;

    public TravelingSalesmanTour(ArrayList<Node> orderedNodes) {
        this.orderedNodes = new ArrayList<>();

        if (orderedNodes.size() > 0) {
            Node lastNode;

            lastNode = orderedNodes.get(0);

            for (Node node: orderedNodes) {
                this.orderedNodes.add(node);

                tourCost += lastNode.calculateDistanceFromNode(node);

                lastNode = this.orderedNodes.get(this.orderedNodes.size() - 1);
            }

            tourCost += lastNode.calculateDistanceFromNode(this.orderedNodes.get(0));
        }

    }

    public TravelingSalesmanTour() {
        this(new ArrayList<Node>());
    }

    public void addNode(Node node) {
        if (orderedNodes.size() > 0) {
            Node firstNode = orderedNodes.get(0);
            Node lastNode = orderedNodes.get(orderedNodes.size() - 1);

            tourCost -= lastNode.calculateDistanceFromNode(firstNode);
            tourCost += lastNode.calculateDistanceFromNode(node);
            tourCost += node.calculateDistanceFromNode(firstNode);
        }

        orderedNodes.add(node);
    }

    public Integer getTourCost() { return tourCost; }

    public ArrayList<Node> getTour() { return new ArrayList<>(orderedNodes); }
}
