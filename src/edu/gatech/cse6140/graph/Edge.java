package edu.gatech.cse6140.graph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Edge {
    private Node node1, node2;
    private int weight;

    public Edge(Node node1, Node node2) {
        this.node1 = node1;
        this.node2 = node2;
        this.weight = node1.calculateDistanceFromNode(node2);
    }

    public Set<Node> getNodes() {
        Set<Node> nodes = new HashSet<>();

        nodes.add(node1);
        nodes.add(node2);

        return nodes;
    }

    public int getWeight() { return weight; }
}
