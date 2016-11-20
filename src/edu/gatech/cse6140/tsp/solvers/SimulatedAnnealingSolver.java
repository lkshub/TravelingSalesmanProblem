package edu.gatech.cse6140.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

public class SimulatedAnnealingSolver implements TravelingSalesmanProblemSolver {
	
	private Random random;
	private ArrayList<Node> nodes;
	
	/**
	 * Constructor for SA solver
	 * 
	 * @param seed random seed
	 */
	public SimulatedAnnealingSolver(int seed, ArrayList<Node> nodes) {
		this.random = new Random(seed);
		this.nodes = nodes;
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

	@Override
	public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
		System.out.println("Starting solver");
		//generate random starting point
		ArrayList<Node> copiedNodes = new ArrayList<Node>(this.nodes.size());
		for(Node node: this.nodes) {
			copiedNodes.add(node);
		}
		NearestNeighborApproximateSolver constructionHeuristic = new NearestNeighborApproximateSolver(new Graph(copiedNodes));
		//Collections.shuffle(copiedNodes, this.random);
		TravelingSalesmanTour candidate = constructionHeuristic.solve(1000);
		TravelingSalesmanTour bestSoFar = candidate;
		double T = 1;
		for(int i = 1; i < 10000; i++) { //10,000 iterations for now
			System.out.println("Iteration " + i + ": T = " + T + " Best = " + bestSoFar.getTourCost() + " Candidate = " + candidate.getTourCost());
			//generate neighbors
			ArrayList<TravelingSalesmanTour> neighbors = this.getNeighbors(candidate);
			
			//choose a candidate and move there
			boolean foundNewCandidate = false;
			int timeout = 500;
			while(!foundNewCandidate && !(timeout == 0)) {
				//pick neighbor at random
				TravelingSalesmanTour possibleCandidate = neighbors.get(this.random.nextInt(neighbors.size()));
				double deltaE = (double) possibleCandidate.getTourCost() - (double) candidate.getTourCost();
				
				if(deltaE < 0) { //if new tour is better, move to tour with probability 1
					foundNewCandidate = true;
					candidate = possibleCandidate;
				} else { //else move with probability e^(deltaE/T)
					double probability = Math.exp(deltaE/T);
					double chance = this.random.nextDouble();
					if(chance >= probability) {
						foundNewCandidate = true;
						candidate = possibleCandidate;
					}
				}
				timeout--;
			}
			
			if(candidate.getTourCost() < bestSoFar.getTourCost()) {
				bestSoFar = candidate;
			}
			
			T = T * 0.95; //update temperature value
		}
		
		return bestSoFar; //return best value
	}

}
