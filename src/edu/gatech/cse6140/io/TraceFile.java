package edu.gatech.cse6140.io;

import java.io.*;
import java.util.ArrayList;

public class TraceFile {
	private ArrayList<Integer> optimalLengths;
	private ArrayList<Double> timeCutoffs;
	
	public TraceFile(){
		optimalLengths = new ArrayList<Integer>();
		timeCutoffs = new ArrayList<Double>();
	}
	
	public void addEntry(int length, double time){
		optimalLengths.add(length);
		timeCutoffs.add(time);
	}
	
	public void createTraceFile(String instance, String method, double cutoff, int seed){
		String fileName;
		if(seed!=-1){
			fileName = instance + "_" + method + "_" + cutoff + "_" + seed + ".trace";
		}
		else{
			fileName = instance + "_" + method + "_" + cutoff + ".trace";
		}
		try {
			PrintWriter trace = new PrintWriter("./output/" + fileName);
			
			for(int i = 0; i < timeCutoffs.size(); i ++){
				trace.println(timeCutoffs.get(i) + ", " + optimalLengths.get(i));
			}
			trace.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}