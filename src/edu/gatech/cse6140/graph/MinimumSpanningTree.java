package edu.gatech.cse6140.graph;


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class MinimumSpanningTree {
    private int cost;
    private int size;

    private Map<Integer, Node> nodes;
    private Map<Integer, Integer> posIds;
    private Map<Integer, List<Node>> neighbors;

    private WeightedQuickUnionUF components;

    public MinimumSpanningTree(Collection<Node> nodes) {
        cost = 0;
        size = 0;

        int n = nodes.size();

        this.nodes = new HashMap<>();
        posIds = new HashMap<>();
        neighbors = new HashMap<>();

        int i = 0;
        for (Node node: nodes) {
            this.nodes.put(node.getId(), node);
            posIds.put(node.getId(), i++);
            neighbors.put(node.getId(), new ArrayList<>());
        }

        components = new WeightedQuickUnionUF(n);

        PriorityQueue<Edge> edges = new PriorityQueue<>(Math.max((n * (n - 1) / 2), 1),
                new Comparator<Edge>() {
                    @Override
                    public int compare(Edge e1, Edge e2) { return e1.getWeight() - e2.getWeight(); }
                }
        );

        Set<String> pairs = new HashSet<>();

        for (Node node1: nodes) {
            for (Node node2: nodes) {
                if (node1.getId() == node2.getId())
                    continue;

                String nodePairString = createNodePairString(node1, node2);
                if (!pairs.contains(nodePairString)) {
                    edges.offer(new Edge(node1, node2));

                    pairs.add(nodePairString);
                }
            }
        }

        while (size < n && edges.size() > 0) {
            Edge candidateEdge = edges.poll();

            if (!createsCycle(candidateEdge))
                addEdge(candidateEdge);
        }

//        for (Node node1: this.nodes.values()) {
//            StringJoiner stringJoiner = new StringJoiner(", ");
//
//            for (Node node2: neighbors.get(node1.getId()))
//                stringJoiner.add(((Integer)node2.getId()).toString());
//
//            System.out.println(node1.getId()+" "+stringJoiner);
//        }
    }

    private String createNodePairString(Node node1, Node node2) {
        StringJoiner stringJoiner = new StringJoiner("_");

        stringJoiner.add(((Integer)Math.min(node1.getId(), node2.getId())).toString());
        stringJoiner.add(((Integer)Math.max(node1.getId(), node2.getId())).toString());

        return stringJoiner.toString();
    }

    private void addEdge(Node node1, Node node2) {
        neighbors.get(node1.getId()).add(node2);
        neighbors.get(node2.getId()).add(node1);

        components.union(posIds.get(node1.getId()), posIds.get(node2.getId()));

        cost += node1.calculateDistanceFromNode(node2);

        size++;
    }

    private void addEdge(Edge edge) {
        Node[] edgeNodes = edge.getNodes().toArray(new Node[2]);

        addEdge(edgeNodes[0], edgeNodes[1]);
    }

    private boolean createsCycle(Edge edge) {
        Node[] edgeNodes = edge.getNodes().toArray(new Node[2]);

        return components.connected(posIds.get(edgeNodes[0].getId()), posIds.get(edgeNodes[1].getId()));
    }

    public int getCost() { return cost; }

    public Set<Node> getNodes() { return new HashSet<>(nodes.values()); }

    public List<Node> getNeighbors(int nodeId) { return new ArrayList<>(neighbors.get(nodeId)); }

    public List<Node> getNeighbors(Node node) { return getNeighbors(node.getId()); }
}
