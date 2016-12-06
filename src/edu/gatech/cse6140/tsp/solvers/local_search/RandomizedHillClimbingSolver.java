package edu.gatech.cse6140.tsp.solvers.local_search;


import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.BestHeuristicApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.FarthestInsertionApproximateSolver;

import java.util.Random;

public class RandomizedHillClimbingSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

    private long randomSeed;
    private Random random;

    private int numIterations = 0;
    private long startTime;
    private long cutoffTimeInSeconds;
    private Trace trace;

    public RandomizedHillClimbingSolver(Graph graph) {
        this.graph = graph;
        trace = new Trace();

        randomSeed = System.currentTimeMillis();
        random = new Random(randomSeed);
    }

    private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

    private void evaluateAndSetBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

            // System.out.println(getTimeRemainingInSeconds()+" - "+numIterations+": "+bestCost+", "+tour);
        }
    }

    public RandomizedHillClimbingSolver setRandomSeed(long randomSeed) {
        this.randomSeed = randomSeed;
        random = new Random(randomSeed);

        return this;
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds, TravelingSalesmanTour candidateTour) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

        evaluateAndSetBestTour(candidateTour);

        int n = graph.getNumNodes();

        int numIterationsUnchanged = 0;

        while (getTimeRemainingInSeconds() > 2) {
            numIterations++;

            if (numIterationsUnchanged > 2 * n * n) {
                // ArrayList<Node> nodes = new ArrayList<>(graph.getNodes());
                // Collections.shuffle(nodes, random);

                candidateTour = new TravelingSalesmanTour(candidateTour.getTour());

                int i = random.nextInt(n - 5);

                // j = random.nextInt(n);
                // while (i == j) {
                //     i = random.nextInt(n);
                //     j = random.nextInt(n);
                // }

                System.out.println("RESTARTING: "+candidateTour.getTourCost());

                candidateTour.shuffleNodesBetweenIndex(i, i + 6, random);
                evaluateAndSetBestTour(candidateTour);

                System.out.println("RESTARTED : "+candidateTour.getTourCost());

                numIterationsUnchanged = 0;
            }

            TravelingSalesmanTour newCandidateTour = new TravelingSalesmanTour(candidateTour.getTour());

            int i = random.nextInt(n), j = random.nextInt(n);
            while (i == j) {
                i = random.nextInt(n);
                j = random.nextInt(n);
            }

            newCandidateTour.swapNodes(i, j);

            if (newCandidateTour.getTourCost() < candidateTour.getTourCost()) {
                evaluateAndSetBestTour(newCandidateTour);

                candidateTour = newCandidateTour;

                numIterationsUnchanged = 0;
            } else {
                numIterationsUnchanged++;
            }
        }

        return bestTour;
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        // ArrayList<Node> nodes = new ArrayList<>(graph.getNodes());
        // Collections.shuffle(nodes, random);

        return solve(cutoffTimeInSeconds, new BestHeuristicApproximateSolver(graph).solve(cutoffTimeInSeconds));
    }
    
    public Trace getTrace(){
    	return trace;
    }
}
