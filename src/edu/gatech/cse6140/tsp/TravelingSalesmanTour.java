package edu.gatech.cse6140.tsp;

import edu.gatech.cse6140.graph.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Collections;

public class TravelingSalesmanTour {
	
	private class NodeToTourComparator implements Comparator<Node>{
		@Override
		public int compare(Node node1, Node node2){//the distance is defined as max(node.distanceTo(any node in tour))
			Node farthestNodeInTour1 = Collections.max(getNodes(),node1.getDistanceComparator());
			Node farthestNodeInTour2 = Collections.max(getNodes(),node2.getDistanceComparator());
			return farthestNodeInTour1.calculateDistanceFromNode(node1) - farthestNodeInTour2.calculateDistanceFromNode(node2);
			}
	}
		
    private int tourCost = 0;
    private ArrayList<Node> orderedNodes;
    private HashSet<Integer> tourNodeIds = new HashSet<>();

    public TravelingSalesmanTour(ArrayList<Node> orderedNodes) {
        this();

        for (Node node: orderedNodes)
            addNode(node);
    }

    public TravelingSalesmanTour() { orderedNodes = new ArrayList<>(); }

    public void addNode(int position, Node node) {
        int n = orderedNodes.size();

        if (position < 0 || position > n)
            return;

        if (n > 0) {
            Node headNode = position - 1 >= 0 ? orderedNodes.get(position - 1) : orderedNodes.get(n - 1);
            Node tailNode = position < n ? orderedNodes.get(position) : orderedNodes.get(0);

            tourCost -= headNode.calculateDistanceFromNode(tailNode);
            tourCost += headNode.calculateDistanceFromNode(node);
            tourCost += node.calculateDistanceFromNode(tailNode);
        }

        orderedNodes.add(position, node);
        tourNodeIds.add(node.getId());
    }

    public void addNode(Node node) { addNode(orderedNodes.size(), node); }

    public void removeNode(int position) {
        int n = orderedNodes.size();
        Node node = orderedNodes.get(position);

        if (n > 0) {
            Node headNode = position - 1 >= 0 ? orderedNodes.get(position - 1) : orderedNodes.get(n - 1);
            Node tailNode = position + 1 < n ? orderedNodes.get(position + 1) : orderedNodes.get(0);

            tourCost -= headNode.calculateDistanceFromNode(node);
            tourCost -= node.calculateDistanceFromNode(tailNode);
            tourCost += headNode.calculateDistanceFromNode(tailNode);
        }

        tourNodeIds.remove(node.getId());
        orderedNodes.remove((int) position);
    }

    public void swapNodes(int position1, int position2) {
        if ((int) position1 == position2)
            return;

        Node node1 = orderedNodes.get(position1);
        Node node2 = orderedNodes.get(position2);

        removeNode(position1);
        addNode(position1, node2);

        removeNode(position2);
        addNode(position2, node1);
    }
    
    public ArrayList<Node> shuffleNodesBetweenIndex(int pos1, int pos2, Random random) {
    	ArrayList<Node> partition = new ArrayList<Node>();
    	int start = Integer.min(pos1, pos2);
    	int end = Integer.max(pos1, pos2);
    	for(int i = start; i < end; i++) {
    		partition.add(this.orderedNodes.get(i));
    	}
    	Collections.shuffle(partition, random);
    	for(int i = start; i < end; i++) {
    		this.orderedNodes.set(i, partition.get(i - start));
    	}
    	return this.orderedNodes;
    }

    public int getTourCost() { return tourCost; }

    public int getPathCost() {
        return tourCost
                - orderedNodes.get(0)
                    .calculateDistanceFromNode(orderedNodes.get(getTourSize() - 1));
    }

    public ArrayList<Node> getTour() { return new ArrayList<>(orderedNodes); }

    public Set<Node> getNodes() { return new HashSet<>(orderedNodes); }

    public Node getNodeAtPosition(int position) { return orderedNodes.get(position); }

    public int getTourSize() { return orderedNodes.size(); }

    public Boolean containsNodeId(int nodeId) { return tourNodeIds.contains(nodeId); }

    public String toString() {
        if (orderedNodes.isEmpty())
            return null;

        if (orderedNodes.size() == 1)
            return orderedNodes.get(0).getLabel();

        StringBuilder stringBuilder = new StringBuilder();

        for (Node node: orderedNodes)
            stringBuilder.append(node.getLabel()).append("-");

        stringBuilder.append(orderedNodes.get(0).getLabel());

        return stringBuilder.toString();
    }
    public Comparator<Node> getNodeToTourComparator(){
    	return new NodeToTourComparator();
    }
}
