package edu.gatech.cse6140.tsp.solvers.heuristic;


import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;

import java.util.ArrayList;

public class BestHeuristicApproximateSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private Trace trace;
    private long startTime;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

    public BestHeuristicApproximateSolver(Graph graph) {
        this.graph = graph;
        trace = new Trace();
    }

    private void setBetterTourAsBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

            // System.out.println(bestCost+", "+tour);
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        startTime = System.currentTimeMillis();

        ArrayList<TravelingSalesmanProblemSolver> solvers = new ArrayList<>();
        solvers.add(new FarthestInsertionApproximateSolver(graph));
        solvers.add(new MinimumSpanningTreeApproximateSolver(graph));
        solvers.add(new NearestNeighborApproximateSolver(graph));

        for (TravelingSalesmanProblemSolver solver: solvers)
            setBetterTourAsBestTour(solver.solve(cutoffTimeInSeconds));

        return bestTour;
    }

    public Trace getTrace() { return trace; }
}
