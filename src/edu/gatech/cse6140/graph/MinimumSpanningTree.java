package edu.gatech.cse6140.graph;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class MinimumSpanningTree {
    private int cost;
    private int size;

    private Map<Integer, Node> nodes;
    private Map<Integer, List<Node>> neighbors;

    private WeightedQuickUnionUF components;

    public MinimumSpanningTree(Graph graph) {
        cost = 0;
        size = 0;

        int n = graph.getNumNodes();

        nodes = new HashMap<>();
        neighbors = new HashMap<>();

        for (int i = 0; i < n; i++) {
            nodes.put(i, graph.getNode(i));
            neighbors.put(i, new ArrayList<>());
        }

        components = new WeightedQuickUnionUF(n);

        PriorityQueue<Edge> edges = new PriorityQueue<Edge>((n * (n - 1) / 2), new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) { return e1.getWeight() - e2.getWeight(); }
        });

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++)
                edges.offer(new Edge(graph.getNode(i), graph.getNode(j)));
        }

        while (size < n && edges.size() > 0) {
            Edge candidateEdge = edges.poll();

            if (!createsCycle(candidateEdge))
                addEdge(candidateEdge);
        }
    }

    private void addEdge(Node node1, Node node2) {
        neighbors.get(node1.getId()).add(node2);
        neighbors.get(node2.getId()).add(node1);

        components.union(node1.getId(), node2.getId());

        cost += node1.calculateDistanceFromNode(node2);

        size++;
    }

    private void addEdge(Edge edge) {
        Node[] edgeNodes = edge.getNodes().toArray(new Node[2]);

        addEdge(edgeNodes[0], edgeNodes[1]);
    }

    private boolean createsCycle(Edge edge) {
        Node[] edgeNodes = edge.getNodes().toArray(new Node[2]);

        return components.connected(edgeNodes[0].getId(), edgeNodes[1].getId());
    }

    public int getCost() { return cost; }

    public Set<Node> getNodes() { return new HashSet<>(nodes.values()); }

    public List<Node> getNeighbors(int nodeId) { return new ArrayList<>(neighbors.get(nodeId)); }

    public List<Node> getNeighbors(Node node) { return getNeighbors(node.getId()); }
}
