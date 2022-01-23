package com.twitterMetrics.engagementAnalyzer.Model;

import java.util.ArrayList;

public class Statistic {
	
	private ArrayList<Double> values;
	
	/**
	 * Statistic constructor
	 */
	public Statistic() {
		values = new ArrayList<Double>();
	}
	
	/**
	 * Method that add one int value to the statistic.
	 * 
	 * @param value int single element used for the statistic.
	 */
	public void add(int value) {
		values.add((double) value);
	}
	
	/**
	 * Method that add one double value to the statistic.
	 * 
	 * @param value double single element used for the statistic.
	 */
	public void add(double value) {
		values.add(value);
	}
	
	/**
	 * Method that return the mean of all the values in this object as double
	 * 
	 * @return mean of all the values as double
	 */
	public double getMean() {
		
		double mean = 0.0;
		for(double val: values) {
			mean += val;
		}
		
		return mean / values.size();
	}
	
	/**
	 * Method that return the variance of all the values in this object as double
	 * 
	 * @return variance of all the values as double
	 */
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
