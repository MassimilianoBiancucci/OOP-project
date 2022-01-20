package com.twitterMetrics.engagementAnalyzer.Model;

import java.util.ArrayList;

public class Statistic {
	
	private ArrayList<Double> values;
	
	public Statistic() {
		values = new ArrayList<Double>();
	}
	
	public void add(int value) {
		values.add((double) value);
	}
	
	public void add(double value) {
		values.add(value);
	}
	
	public double getMean() {
		
		double mean = 0.0;
		for(double val: values) {
			mean += val;
		}
		
		return mean / values.size();
	}
	
	public double getVariance() {
		
		double mean = getMean();
		double variance = 0.0;
		
		for(double val: values) {
			variance += Math.pow((val - mean), 2);
		}
		
		return variance / values.size();
	}
	
	public void clear() {
		values.clear();
	}
	
}
