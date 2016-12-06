package edu.gatech.cse6140.tsp.solvers;


import edu.gatech.cse6140.io.Trace;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

public interface TravelingSalesmanProblemSolver {
    public TravelingSalesmanTour solve(int cutoffTimeInSeconds);

    public Trace getTrace();
}
