package edu.gatech.cse6140.tsp.solvers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import edu.gatech.cse6140.localSearch.*;

public class HillClimbingSolver {

	public static void main(String[] args){
		InputOutputHandler ioHandler = new InputOutputHandler(
                "C:/Users/beason92/Desktop/gatech-cse6140-fa16-z-tsp/data"//you should use your own path to test the implementation
        );
		Graph graphBoston = new Graph(ioHandler.getNodesFromTSPFile("Boston.tsp"));
		
		long seed = System.nanoTime();
		
		ArrayList<Node> currSolution = graphBoston.getNodes();
		
		Collections.shuffle(currSolution, new Random(seed));
		
		int maxEvaluations = Integer.parseInt(args[0]);
		
		EvaluationFunction currEval = new EvaluationFunction(currSolution);
		
		int numEvaluations = 1;
				
		EvaluationFunction nextEval = new EvaluationFunction();
				
		while(numEvaluations < maxEvaluations){
			ArrayList<Node> nextSolution = switchRandomCities(currSolution, currSolution.size()-1, currSolution.size());
			
			nextEval.recalculateEvaluation(nextSolution);
			
			if(nextEval.getPoints() < currEval.getPoints()){
				currSolution = nextSolution;
				currEval.recalculateEvaluation(currSolution);
			}
			
			//randomized restart once local maximum is found
			else{
				seed = System.nanoTime();
				nextSolution = currSolution;
				Collections.shuffle(nextSolution, new Random(seed));
			}
			
			numEvaluations++;
		}
		
		System.out.println("Solution Distance: " + currEval.getPoints());
	}
	
	//gets the best swap out of a given neighborhood
	public static ArrayList<Node> switchRandomCities(ArrayList<Node> solution, int max, int neighborhood){
		int bestDistance = Integer.MAX_VALUE;
		ArrayList<Node> bestSolution = solution;
		EvaluationFunction eval = new EvaluationFunction();
		
		for(int i = 0; i < neighborhood; i++){
			long seed = System.nanoTime();
			Random rand = new Random(seed);
			int x = 0; 
			int y = 0;
			
			while(x==y){
				x = rand.nextInt(max);
				y = rand.nextInt(max);
			}
			
			Collections.swap(solution, x, y);
			eval.recalculateEvaluation(solution);
			int nextDistance = eval.getPoints();
			
			if(nextDistance < bestDistance){
				bestDistance = nextDistance;
				bestSolution = solution;
			}
		}
		return bestSolution;
	}
}
