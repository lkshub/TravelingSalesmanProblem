package edu.gatech.cse6140.tsp.solvers;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.MinimumSpanningTree;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.heuristic.BestHeuristicApproximateSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.NearestNeighborApproximateSolver;

import java.util.Collection;
import java.util.PriorityQueue;
import java.util.Set;

import edu.gatech.cse6140.io.*;

public class BranchAndBoundSolver implements TravelingSalesmanProblemSolver {
    private Graph graph;
    private Trace trace;
    private TravelingSalesmanTour bestTour;
    private int bestCost = Integer.MAX_VALUE;

    private int numIterations = 0;
    private long startTime;
    private long cutoffTimeInSeconds;

    public BranchAndBoundSolver(Graph graph) {
        this.graph = graph;
        trace = new Trace();
    }

    private long getTimeRemainingInSeconds() {
        return cutoffTimeInSeconds - ((System.currentTimeMillis() - startTime) / 1000);
    }

    private void evaluateAndSetBestTour(TravelingSalesmanTour tour) {
        if (tour.getTourCost() < bestCost) {
            bestTour = new TravelingSalesmanTour(tour.getTour());
            bestCost = bestTour.getTourCost();
            trace.addEntry(((double)(System.currentTimeMillis() - startTime) / (double)1000), tour.getTourCost());

            // System.out.println(bestCost);
        }
    }

    private int calculateLowerBound(TravelingSalesmanTour partialTour, Collection<Node> remainingNodes) {
        Node headNode = partialTour.getNodeAtPosition(0),
                tailNode = partialTour.getNodeAtPosition(partialTour.getTourSize() - 1);

        int exitCost = Integer.MAX_VALUE;

        for (Node node: remainingNodes)
            exitCost = Math.min(exitCost,
                    Math.min(
                            headNode.calculateDistanceFromNode(node),
                            tailNode.calculateDistanceFromNode(node)
                    )
            );

       return partialTour.getPathCost()
               + exitCost
               + new MinimumSpanningTree(remainingNodes).getCost();
    }

    private void solve(TravelingSalesmanTour candidateTour) {
        if (getTimeRemainingInSeconds() < 2)
            return;

        // numIterations++;

        Set<Node> remainingNodes = graph.getNodes();
        remainingNodes.removeAll(candidateTour.getNodes());

        if (calculateLowerBound(candidateTour, remainingNodes) > bestCost)
            return;

        if (remainingNodes.size() == 0)
            evaluateAndSetBestTour(candidateTour);
        else {
            Node tailNode = candidateTour.getNodeAtPosition(candidateTour.getTourSize() - 1);

            PriorityQueue<Node> candidateNodes = new PriorityQueue<>(remainingNodes.size(),
                    tailNode.getDistanceComparator());



            for (Node candidateNode: remainingNodes)
                candidateNodes.offer(candidateNode);

            while (!candidateNodes.isEmpty()) {
                Node candidateNode = candidateNodes.poll();

                TravelingSalesmanTour newCandidateTour = new TravelingSalesmanTour(candidateTour.getTour());
                newCandidateTour.addNode(candidateNode);

                solve(newCandidateTour);
            }
        }
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds, TravelingSalesmanTour tour) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

        evaluateAndSetBestTour(tour);

        TravelingSalesmanTour newTour = new TravelingSalesmanTour();

        newTour.addNode(new NearestNeighborApproximateSolver(graph).solve(0).getNodeAtPosition(0));

        solve(newTour);

        // System.out.println(numIterations+": "+ bestTour.getTourCost()+", "+bestTour);
                
        return bestTour;
    }

    public TravelingSalesmanTour solve(int cutoffTimeInSeconds) {
        startTime = System.currentTimeMillis();
        this.cutoffTimeInSeconds = (long) cutoffTimeInSeconds;

        TravelingSalesmanTour tour = new BestHeuristicApproximateSolver(graph).solve(cutoffTimeInSeconds);

        solve((int)getTimeRemainingInSeconds(), tour);

        return bestTour;
    }
    
    public Trace getTrace(){ return trace; }
}
