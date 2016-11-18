package edu.gatech.cse6140.graph;

import java.util.*;

public class Graph {
    private Map<Integer, Node> nodes;
    private Map<Integer, Set<Integer>> edges;

    public Graph() {
        nodes = new HashMap<>();
        edges = new HashMap<>();
    }

    public Graph(Collection<Node> nodes) {
        this();

        for (Node node: nodes)
            addNode(node);
    }

    public void addNode(Node node) {
        nodes.put(node.getId(), node);
        edges.put(node.getId(), new HashSet<>());
    }

    public int getNumNodes() { return nodes.size(); }

    public Node getNode(int nodeId) { return nodes.get(nodeId); }

    public Set<Node> getNodes() { return new HashSet<>(nodes.values()); }

    public Edge getEdge(int nodeId1, int nodeId2) { return new Edge(getNode(nodeId1), getNode(nodeId2)); }
}
