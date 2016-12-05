package edu.gatech.cse6140.tsp.solvers.local_search;


import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.TraceFile;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;

import java.util.*;

public class DeterministicHillClimbingSolver implements TravelingSalesmanProblemSolver {
    public class TabuTourMemory {
        private int size;

        private Set<String> tabuSet;
        private Queue<String> tabuQueue;

        public TabuTourMemory(int size) {
            this.size = size;

            tabuSet = new HashSet<>();
            tabuQueue = new LinkedList<>();
        }

        public void addTour(TravelingSalesmanTour tour) {
            if (tabuSet.size() == size)
                tabuSet.remove(tabuQueue.remove());

            tabuSet.add(tour.toString());
            tabuQueue.add(tour.toString());
        }

        public boolean containsTour(TravelingSalesmanTour tour) {
            return tabuSet.contains(tour.toString());
        }

        public void clear() {
            tabuSet.clear();
            tabuQueue.clear();
        }
    }

    private Graph graph;
    private TravelingSalesmanTour bestTour;

    private int bestCost = Integer.MAX_VALUE;
    private int numIterations = 0;
    private long startTime;
    private TraceFile trace;
    
    private long cutoffTimeInSeconds;

    public TabuTourMemory tabuTourMemory;

    public DeterministicHillClimbingSolver(Graph graph) {
        this.graph = graph;
        trace = new TraceFile();

        tabuTourMemory = new TabuTourMemory(1000);
    }

    private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

    private void setBetterTourAsBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
        	trace.addEntry(tour.getTourCost(), ((double)(System.currentTimeMillis() - startTime) / (double)1000));
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            // System.out.println(getTimeRemainingInSeconds()+" - "+numIterations+": "+bestCost+", "+tour);
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds, TravelingSalesmanTour candidateTour) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

        setBetterTourAsBestTour(candidateTour);

        int n = graph.getNumNodes();

        while (getTimeRemainingInSeconds() > 2) {
            numIterations++;

            TravelingSalesmanTour bestNewCandidateTour = null;
            int bestNewCandidateCost = candidateTour.getTourCost();

            TravelingSalesmanTour leastWorstNewCandidateTour = null;
            int leastWorstNewCandidateCost = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < i; j++) {
                    TravelingSalesmanTour newCandidateTour = new TravelingSalesmanTour(candidateTour.getTour());
                    newCandidateTour.swapNodes(i, j);

                    if ((newCandidateTour.getTourCost() < bestNewCandidateCost)
                            && !tabuTourMemory.containsTour(newCandidateTour)) {
                        bestNewCandidateTour = newCandidateTour;
                        bestNewCandidateCost = newCandidateTour.getTourCost();
                    }

                    if (newCandidateTour.getTourCost() < leastWorstNewCandidateCost
                            && !tabuTourMemory.containsTour(newCandidateTour)) {
                        leastWorstNewCandidateTour = newCandidateTour;
                        leastWorstNewCandidateCost = newCandidateTour.getTourCost();
                    }
                }
            }

            if (bestNewCandidateTour != null) {
                setBetterTourAsBestTour(bestNewCandidateTour);
                candidateTour = bestNewCandidateTour;
            } else {
                candidateTour = leastWorstNewCandidateTour;
            }

            tabuTourMemory.addTour(candidateTour);

            System.out.println(getTimeRemainingInSeconds()+" ["+numIterations+", "+candidateTour.getTourCost()+"] ");
        }

        return bestTour;
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        ArrayList<Node> nodes = new ArrayList<>(graph.getNodes());

        return solve(cutoffTimeInSeconds, new NearestNeighborApproximateSolver(graph).solve(0));
    }
    
    public TraceFile getTraceFile(){
    	return trace;
    }
}
