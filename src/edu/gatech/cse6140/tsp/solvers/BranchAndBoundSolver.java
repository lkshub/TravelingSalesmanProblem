package edu.gatech.cse6140.tsp.solvers;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

public class BranchAndBoundSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;
    private int calls = 0;

    public BranchAndBoundSolver(Graph graph) {
        this.graph = graph;
    }

    private void solve(TravelingSalesmanTour candidateTour) {
        calls += 1;
        if (candidateTour.getTourCost() > bestCost) {
            return;
        }

        if (candidateTour.getTourSize() == graph.getNumNodes()) {
            if (candidateTour.getTourCost() < bestCost) {
                bestTour = candidateTour;
                bestCost = candidateTour.getTourCost();
                System.out.println(bestCost);
            }
        } else {
            for (int nodeId = 0; nodeId < graph.getNumNodes(); nodeId++) {
                if (!candidateTour.containsNodeId(nodeId)) {
                    TravelingSalesmanTour newCandidateTour = new TravelingSalesmanTour(candidateTour.getTour());

                    newCandidateTour.addNode(graph.getNode(nodeId));

                    solve(newCandidateTour);
                }
            }
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        TravelingSalesmanTour tour = new TravelingSalesmanTour();

        tour.addNode(graph.getNode(0));

        solve(tour);

        System.out.println("solve calls: "+calls);

        return bestTour;
    }
}
