package edu.gatech.cse6140.tsp.solvers.heuristic;


import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;

import java.util.Collections;
import java.util.Set;

public class NearestNeighborApproximateSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private Trace trace;
    private long startTime;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

    public NearestNeighborApproximateSolver(Graph graph) {
        this.graph = graph;
        trace = new Trace();
    }

    private void setBetterTourAsBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());
            System.out.println(bestCost+", "+tour);
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        startTime = System.currentTimeMillis();

        for (Node headNode: graph.getNodes()) {
            TravelingSalesmanTour tour = new TravelingSalesmanTour();

            tour.addNode(headNode);

            Set<Node> remainingNodes = graph.getNodes();
            remainingNodes.removeAll(tour.getNodes());

            while (remainingNodes.size() > 0) {
                Node tailNode = tour.getNodeAtPosition(tour.getTourSize() - 1);
                Node nextNode = Collections.min(remainingNodes, tailNode.getDistanceComparator());
                tour.addNode(nextNode);

                remainingNodes.removeAll(tour.getNodes());
            }

            setBetterTourAsBestTour(tour);
        }

        return bestTour;
    }

    public Trace getTrace() { return trace; }
}
