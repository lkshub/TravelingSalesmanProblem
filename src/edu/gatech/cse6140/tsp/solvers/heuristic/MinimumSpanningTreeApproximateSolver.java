package edu.gatech.cse6140.tsp.solvers.heuristic;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.*;
import edu.gatech.cse6140.graph.MinimumSpanningTree;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;

import java.util.ArrayList;
import java.util.Stack;

public class MinimumSpanningTreeApproximateSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private TraceFile trace;

    public MinimumSpanningTreeApproximateSolver(Graph graph) { this.graph = graph; trace = new TraceFile();}

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
    	long startTime = System.currentTimeMillis();
        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph.getNodes());

        Stack<Node> nodeStack = new Stack<>();

        if (graph.getNumNodes() > 0)
            nodeStack.push(graph.getNode(0));
//            nodeStack.push(graph.getNodes().iterator().next());

        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();

            tour.addNode(node);

            for (Node neighbor: minimumSpanningTree.getNeighbors(node.getId())) {
                if (!tour.containsNodeId(neighbor.getId()))
                    nodeStack.push(neighbor);
            }
        }
        trace.addEntry(tour.getTourCost(), ((System.currentTimeMillis() - startTime) / 1000));
        return tour;
    }
    
    public TraceFile getTraceFile(){
    	return trace;
    }
}
