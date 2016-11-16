package edu.gatech.cse6140.localSearch;
import java.util.ArrayList;

import edu.gatech.cse6140.graph.*;

public class EvaluationFunction {
	private int points;
	//assume the first node is the start and the last node is the end
	public EvaluationFunction(ArrayList<Node> nodes){
		int counter = 2;
		Node startNode = nodes.get(0);
		Node node1 = nodes.get(0);
		Node node2 = nodes.get(1);
		points = 0;
		while(!node2.equals(startNode)){
			points = points + node1.calculateDistanceFromNode(node2);
			node1 = node2;
			node2 = nodes.get(counter);
			counter++;
			if(counter==nodes.size()){
				counter = 0;
			}
		}
	}
	
	public EvaluationFunction(){
	}
	
	public int getPoints(){
		return points;
	}
	
	public void recalculateEvaluation(ArrayList<Node> nodes){
		int counter = 2;
		Node startNode = nodes.get(0);
		Node node1 = nodes.get(0);
		Node node2 = nodes.get(1);
		points = 0;
		while(!node2.equals(startNode)){
			points = points + node1.calculateDistanceFromNode(node2);
			node1 = node2;
			node2 = nodes.get(counter);
			counter++;
			if(counter==nodes.size()){
				counter = 0;
			}
		}		
	}
}
