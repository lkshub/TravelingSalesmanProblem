package edu.gatech.cse6140.tsp.solvers.local_search;

import java.util.ArrayList;
import java.util.Random;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.BestHeuristicApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

public class SimulatedAnnealingSolver implements TravelingSalesmanProblemSolver {

    private Graph graph;
    private Trace trace;
    private long startTime;
    private long cutoffTimeInSeconds;
    private long randomSeed;
    private Random random;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

	public SimulatedAnnealingSolver(Graph graph, long randomSeed) {
        this.graph = graph;
        trace = new Trace();
        this.randomSeed = randomSeed;
        random = new Random(randomSeed);
	}

	public SimulatedAnnealingSolver(Graph graph) { this(graph, System.currentTimeMillis()); }

	public SimulatedAnnealingSolver setRandomSeed(long randomSeed) {
		this.randomSeed = randomSeed;
        random = new Random(randomSeed);

        return this;
	}

    private void setBetterTourAsBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

            // System.out.println(bestCost);
        }
    }

	private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

	public TravelingSalesmanTour solve(int cutoffTimeInSeconds, TravelingSalesmanTour candidateTour) {
		this.startTime = System.currentTimeMillis();
		this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

        setBetterTourAsBestTour(candidateTour);

        double T = 800, Tmin = 0.01;

		while(getTimeRemainingInSeconds() > 2 && T > Tmin) {
            int n = graph.getNumNodes();

            // choose a candidate and move there
            int timeout = 800;
            boolean foundNewCandidate = false;

            while (!foundNewCandidate && !(timeout == 0)) {
				// pick neighbor at random
                TravelingSalesmanTour possibleCandidate = new TravelingSalesmanTour(candidateTour.getTour());

                int i = random.nextInt(n), j = random.nextInt(n);
                while (i == j)
                    j = random.nextInt(n);

                possibleCandidate.swap2OPT(i, j);

                double deltaE = (double) possibleCandidate.getTourCost() - (double) candidateTour.getTourCost();

				if (deltaE < 0) { // if new tour is better, move to tour with probability 1
					foundNewCandidate = true;
                    candidateTour = possibleCandidate;
				} else { // else move with probability e^(-deltaE/T)
					double probability = Math.exp(-1 * deltaE / T);
					double chance = this.random.nextDouble();

                    if (chance < probability) {
                        foundNewCandidate = true;
						candidateTour = possibleCandidate;
					}
				}

				timeout--;
			}

			setBetterTourAsBestTour(candidateTour);

            T = T * 0.9995; //update temperature value
		}

		return bestTour;
	}

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = cutoffTimeInSeconds;

        TravelingSalesmanTour tour = new BestHeuristicApproximateSolver(graph).solve(cutoffTimeInSeconds);

        return solve((int)getTimeRemainingInSeconds(), tour);
    }

	public Trace getTrace(){ return trace; }
}
