package edu.gatech.cse6140.tsp.solvers;

import edu.gatech.cse6140.graph.Graph;
import edu.gatech.cse6140.graph.Node;
import edu.gatech.cse6140.io.InputOutputHandler;
import edu.gatech.cse6140.tsp.TravelingSalesmanTour;
import java.util.*;

public class MSTApproxSolver {
	
	private Graph graph;
	public MSTApproxSolver(Graph graph){
		this.graph = graph;
	}
	public TravelingSalesmanTour solve(){
		MST mst = computeMST(graph);
		TravelingSalesmanTour tst = new TravelingSalesmanTour();
		Stack<Node> stack = new Stack<>();
		stack.push(graph.getNode(0));
		while(!stack.isEmpty()){
			Node node = stack.pop();
			tst.addNode(node);
			for(Node child:mst.edges.get(node)) {
				if(!tst.containsNodeId(child.getId())){
					stack.push(child);
				}
			}
		}
		return tst;
	}
	private MST computeMST(Graph G){
		MST mst = new MST();
		int[] groupOfV = new int[G.getNumNodes()];
		for(int i = 0;i<G.getNumNodes();i++){
			groupOfV[i]=i;
		}
		PriorityQueue<Edge> edges = new PriorityQueue<Edge>(G.getNumNodes()*G.getNumNodes()/2,new Comparator<Edge>(){
			@Override
			public int compare(Edge a, Edge b){
				return a.weight - b.weight;
			}
		});
		for(int i = 0;i<G.getNumNodes();i++){
			for(int j = i;j<G.getNumNodes();j++){
				edges.offer(new Edge(G.getNode(i),G.getNode(j),G.getDistanceBetweenNodes(i, j)));
			}
		}
		while(mst.size<G.getNumNodes()-1&&edges.size()>0){
			Edge minE = edges.poll();
			int sourceGroup = minE.source.getId();
			while(groupOfV[sourceGroup]!=sourceGroup) {
				sourceGroup = groupOfV[sourceGroup];
			}
			int targetGroup = minE.target.getId();
			while(groupOfV[targetGroup]!=targetGroup) {
				targetGroup = groupOfV[targetGroup];
			}
			if(sourceGroup!=targetGroup){
				mst.addEdge(minE);
				groupOfV[sourceGroup] = targetGroup;
			}
		}
		return mst;
	}
	public static void main(String[] args) {
		InputOutputHandler ioHandler = new InputOutputHandler(
                "/Users/lukai/git/gatech-cse6140-fa16-z-tsp/data"//you should use your own path to test the implementation
        );

        Graph graphBoston = new Graph(ioHandler.getNodesFromTSPFile("Boston.tsp"));
		MSTApproxSolver mstsolver  = new MSTApproxSolver(graphBoston);
		TravelingSalesmanTour tour = mstsolver.solve();
		System.out.println(tour.getTourCost());// the answer is 1142864, ratio = 1.27

	}
	public class MST{
		public int cost;
		public int size;
		public HashMap<Node,List<Node>> edges;
		public MST(){
			cost = 0;
			size = 0;
			edges = new HashMap<>();
		}
		public void addEdge(Edge e){
			if(!edges.containsKey(e.source)) edges.put(e.source,new ArrayList<Node>());
			edges.get(e.source).add(e.target);
			if(!edges.containsKey(e.target)) edges.put(e.target,new ArrayList<Node>());
			edges.get(e.target).add(e.source);
			cost+=e.weight;
			size++;
		}
	}
	public class Edge{
		private Node source;
		private Node target;
		private int weight;
		public Edge(Node s,Node t,int w){
			source = s;
			target = t;
			weight = w;
		}
	}
}
