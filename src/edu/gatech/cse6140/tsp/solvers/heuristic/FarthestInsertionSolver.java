package edu.gatech.cse6140.tsp.solvers.heuristic;

import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class FarthestInsertionSolver implements TravelingSalesmanProblemSolver {
	private Graph graph;
	private Trace trace;
	
	public FarthestInsertionSolver(Graph graph){
		this.graph = graph;
		trace = new Trace();
	}
	
	
	public TravelingSalesmanTour solve(int cutofftimeInSecond) {
		long startTime = System.currentTimeMillis();

		TravelingSalesmanTour tour  = new TravelingSalesmanTour();
		
		Node firstNode = graph.getNode(0);
		
		tour.addNode(firstNode);
		
		Set<Node> remainingNodes = graph.getNodes();
		
		remainingNodes.remove(firstNode);
		
		Node farthestNode = Collections.max(remainingNodes, firstNode.getDistanceComparator());
		
		tour.addNode(farthestNode);
		
		while(remainingNodes.size()>0){
			Node nextNode = Collections.max(remainingNodes,tour.getNodeToTourComparator());
			ArrayList<Node> tourList = tour.getTour();

			int insertPosition = 0;
			int minExtraCost = nextNode.calculateDistanceFromNode(
					tour.getNodeAtPosition(0))
					+ nextNode.calculateDistanceFromNode(tour.getNodeAtPosition(tour.getTourSize()-1))
					- tour.getNodeAtPosition(0).calculateDistanceFromNode(tour.getNodeAtPosition(tour.getTourSize()-1)
			);

			for(int i = 1; i < tourList.size();i++){
				int extraCost = nextNode.calculateDistanceFromNode(tour.getNodeAtPosition(i-1)) + nextNode.calculateDistanceFromNode(tour.getNodeAtPosition(i)) - tour.getNodeAtPosition(i-1).calculateDistanceFromNode(tour.getNodeAtPosition(i));
				if(extraCost < minExtraCost){
					minExtraCost = extraCost;
					insertPosition = i;
				}
			}

			tour.addNode(insertPosition, nextNode);
			remainingNodes.remove(nextNode);
		}

		trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());
		
		return tour;
	}

    public Trace getTrace() { return trace; }
}
