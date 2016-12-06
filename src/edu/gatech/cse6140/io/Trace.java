package edu.gatech.cse6140.io;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Trace {
    private ArrayList<Double> timeCutoffs;
    private ArrayList<Integer> optimalLengths;
	private DecimalFormat df = new DecimalFormat("0.00");
	
	public Trace(){
		optimalLengths = new ArrayList<Integer>();
		timeCutoffs = new ArrayList<Double>();
	}
	
	public void addEntry(double time, int value){
        timeCutoffs.add(time);
        optimalLengths.add(value);
	}

    public void appendTrace(Trace newTrace) {
        timeCutoffs.addAll(newTrace.getTraceTimes());
        optimalLengths.addAll(newTrace.getTraceValues());
    }

	public ArrayList<Double> getTraceTimes() { return new ArrayList<>(timeCutoffs); }

    public ArrayList<Integer> getTraceValues() { return new ArrayList<>(optimalLengths); }

}
