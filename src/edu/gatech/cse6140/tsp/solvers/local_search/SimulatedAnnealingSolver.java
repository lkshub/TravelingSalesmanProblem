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
    private Random random;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;
	
	public SimulatedAnnealingSolver(Graph graph, long seed) {
        this.graph = graph;
        trace = new Trace();
        random = new Random(seed);
	}

	public SimulatedAnnealingSolver(Graph graph) { this(graph, System.currentTimeMillis()); }

	public SimulatedAnnealingSolver setRandomSeed(long randomSeed) {
		this.random = new Random(randomSeed);

        return this;
	}

    private void setBetterTourAsBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

             System.out.println(bestCost);
        }
    }
	
	/**
	 * returns the neighborhood of size n-1 by deep copy, then perform each adjacent swap
	 * 
	 * @param tour the tour for which the neighbors are found
	 * @return ArrayList of every neighbor
	 */
	private ArrayList<TravelingSalesmanTour> getNeighbors(TravelingSalesmanTour tour) {
		int neighborhoodSize = tour.getTourSize() - 1;

        ArrayList<TravelingSalesmanTour> neighbors = new ArrayList<TravelingSalesmanTour>(neighborhoodSize);

        for(int i = 0; i < neighborhoodSize; i++) {
			TravelingSalesmanTour newNeighbor = new TravelingSalesmanTour(tour.getTour());
			newNeighbor.swapNodes(i, i + 1); //get every adjacent swap
			neighbors.add(newNeighbor);
		}

        return neighbors;
	}
	
	private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

	@Override
	public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
		this.startTime = System.currentTimeMillis();
		this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

		TravelingSalesmanTour candidateTour = new BestHeuristicApproximateSolver(graph).solve(cutoffTimeInSeconds);

        setBetterTourAsBestTour(candidateTour);

        double T = 1;

		while(getTimeRemainingInSeconds() > 2) {
			// generate neighbors
			ArrayList<TravelingSalesmanTour> neighbors = getNeighbors(candidateTour);
			
			// choose a candidate and move there
            int timeout = 500;
            boolean foundNewCandidate = false;

            while (!foundNewCandidate && !(timeout == 0)) {
				// pick neighbor at random
                TravelingSalesmanTour possibleCandidate = neighbors.get(this.random.nextInt(neighbors.size()));

                double deltaE = (double) possibleCandidate.getTourCost() - (double) candidateTour.getTourCost();
				
				if (deltaE < 0) { // if new tour is better, move to tour with probability 1
					foundNewCandidate = true;
                    candidateTour = possibleCandidate;
				} else { // else move with probability e^(deltaE/T)
					double probability = Math.exp(deltaE / T);
					double chance = this.random.nextDouble();

                    if (chance >= probability) {
						foundNewCandidate = true;
						candidateTour = possibleCandidate;
					}
				}

				timeout--;
			}
			
			setBetterTourAsBestTour(candidateTour);
			
			T = T * 0.95; //update temperature value
		}
		
		return bestTour;
	}
	
	public Trace getTrace(){ return trace; }
}
