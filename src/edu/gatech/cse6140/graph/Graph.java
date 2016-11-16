package edu.gatech.cse6140.graph;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Node> nodes = new ArrayList<>();
    private Integer[][] adjacencyMatrix;

    public Graph(ArrayList<Node> nodes) {
        int n = nodes.size();

        for (int i = 0; i < n; i++) {
            Node node = nodes.get(i);

            this.nodes.add(new Node(i, node.getLabel(),
                    node.getXCoordinate(), node.getYCoordinate()
            ));
        }

        adjacencyMatrix = new Integer[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                adjacencyMatrix[i][j] = this.nodes.get(i)
                        .calculateDistanceFromNode(this.nodes.get(j));
            }
        }
    }

    public Integer getNumNodes() { return nodes.size(); }

    public Node getNode(Integer nodeId) { return nodes.get(nodeId); }

    public Integer getDistanceBetweenNodes(Integer nodeId1, Integer nodeId2) {
        return adjacencyMatrix[nodeId1][nodeId2];
    }
    
    public ArrayList<Node> getNodes(){
    	return nodes;
    }
}
