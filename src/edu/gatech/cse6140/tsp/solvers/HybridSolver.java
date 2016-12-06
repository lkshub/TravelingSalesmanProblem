package edu.gatech.cse6140.tsp.solvers;


import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.heuristic.BestHeuristicApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.RandomizedHillClimbingSolver;
import edu.gatech.cse6140.tsp.solvers.local_search.SimulatedAnnealingSolver;

import java.util.Random;

public class HybridSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

    private long randomSeed;
    private Random random;

    private long startTime;
    private long cutoffTimeInSeconds;
    private Trace trace;

    public HybridSolver(Graph graph, long randomSeed) {
        this.graph = graph;
        trace = new Trace();

        this.randomSeed = randomSeed;
        random = new Random(randomSeed);
    }

    public HybridSolver(Graph graph) {
        this(graph, System.currentTimeMillis());
    }

    public HybridSolver setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
        random = new Random(randomSeed);

        return this;
    }

    private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

    private void evaluateAndSetBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

            System.out.println(bestCost);
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = cutoffTimeInSeconds;

        int intermediateCutoffTime = cutoffTimeInSeconds / 2;

        TravelingSalesmanTour tour;

        TravelingSalesmanProblemSolver solver1 = new BestHeuristicApproximateSolver(graph);
        tour = solver1.solve(5);
        evaluateAndSetBestTour(tour);

        System.out.println("---");

        SimulatedAnnealingSolver solver2 = new SimulatedAnnealingSolver(graph, randomSeed);
        tour = solver2.solve(intermediateCutoffTime, tour);
        evaluateAndSetBestTour(tour);

        System.out.println("---");

        RandomizedHillClimbingSolver solver3 = new RandomizedHillClimbingSolver(graph, randomSeed);
        tour = solver3.solve((int)getTimeRemainingInSeconds(), tour);
        evaluateAndSetBestTour(tour);

        return bestTour;
    }

    public Trace getTrace() { return trace; }
}
