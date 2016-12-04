package edu.gatech.cse6140.tests;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.tsp.solvers.BranchAndBoundSolver;
import edu.gatech.cse6140.tsp.solvers.TravelingSalesmanProblemSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.FarthestInsertionSolver;
import edu.gatech.cse6140.tsp.solvers.heuristic.MinimumSpanningTreeApproximateSolver;

import org.junit.Test;

public class FarthestInsertionSolverTest {

	@Test
	public void testFarthestInsertionSolver(){
		InputOutputHandler ioHandler = new InputOutputHandler(
                "./data"
        );
        String[] cities = {"Atlanta.tsp","Boston.tsp","Cincinnati.tsp","Champaign.tsp","Denver.tsp","NYC.tsp","Philadelphia.tsp","Roanoke.tsp","SanFrancisco.tsp","Toronto.tsp","UKansasState.tsp","UMissouri.tsp"};
        int[] exactCost = {2003763,893536,277952,52643,100431,1555060,1395981,655454,810196,1176151,62962,132709};
        for (int i = 0; i < cities.length; i++){
        	//System.out.println(city);
        	String city  =  cities[i];
        	
	        Graph graph = new Graph(ioHandler.getNodesFromTSPFile(city));
	        
	        TravelingSalesmanProblemSolver solver = new FarthestInsertionSolver(graph);
	        
	        TravelingSalesmanTour tour = solver.solve(600);
	        
	        
	        double ratio = (double)tour.getTourCost()/exactCost[i];
	        
	        System.out.println(tour.getTourCost()+" "+ratio);
	        
        }


        
        
        
	}

}
