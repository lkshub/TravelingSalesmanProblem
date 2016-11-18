package edu.gatech.cse6140.tsp.solvers.heuristic;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.MinimumSpanningTree;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;

import java.util.Stack;

public class MinimumSpanningTreeApproximateSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;

    public MinimumSpanningTreeApproximateSolver(Graph graph) { this.graph = graph; }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        MinimumSpanningTree minimumSpanningTree = new MinimumSpanningTree(graph);

        Stack<Node> nodeStack = new Stack<>();

        nodeStack.push(graph.getNode(0));

        while (!nodeStack.isEmpty()) {
            Node node = nodeStack.pop();

            tour.addNode(node);

            for (Node neighbor: minimumSpanningTree.getNeighbors(node.getId())) {
                if (!tour.containsNodeId(neighbor.getId()))
                    nodeStack.push(neighbor);
            }
        }

        return tour;
    }
}
