package edu.gatech.cse6140.tsp.solvers;


import java.util.ArrayList;
import edu.gatech.cse6140.graph.Node;

import edu.gatech.cse6140.tsp.TravelingSalesmanTour;

public interface TravelingSalesmanProblemSolver {
    public TravelingSalesmanTour solve(int cutoffTimeInSeconds);
}
