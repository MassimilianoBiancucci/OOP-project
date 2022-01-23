package com.twitterMetrics.engagementAnalyzer.Model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;

public class StatisticsList {
	
	private Map<Field, Statistic> statistics;
	private Field[] fields;
	
	/**
	 * StatisticsList constructor
	 * 
	 * @param fields list of Field enum that will be supported from this StatisticsList object, this object will create one Statistic for each field
	 */
	public StatisticsList(Field[] fields) {
		
		this.fields = fields;
		statistics = new HashMap<Field, Statistic>();
		for(Field field: fields) statistics.put(field, new Statistic());
	}
	
	
	/**
	 * Method that add a value to the specific field statistic 
	 * 
	 * @param field that select the statistic
	 * @param val value to add to the selected statistic
	 */
	public void add(Field field, double val) {
		statistics.get(field).add(val);
	}
	
	
	/**
	 * Method that return the mean of the statistic selected with the field
	 * 
	 * @param field field that select the statistic
	 * @return the mean of the statistic selected
	 */
	public double getMean(Field field) {
		return statistics.get(field).getMean();
	}
	
	
	/**
	 * Method that return the variance of the statistic selected with the field
	 * 
	 * @param field field that select the statistic
	 * @return the mean of the statistic selected
	 */
	public double getVariance(Field field) {
		return statistics.get(field).getVariance();
	}
	
	
	/**
	 * Method that reset all the values inside all the statistics
	 */
	public void clear() {
		for(Statistic stat: statistics.values()) stat.clear();
	}
	
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	public JsonObject toJson() {
		
		JsonObject statisticsJson = new JsonObject();
		
		for(Field field: fields) {
			statisticsJson.addProperty(Filter.field2String.get(field) + "_mean", getMean(field));
		}
		
		for(Field field: fields) {
			statisticsJson.addProperty(Filter.field2String.get(field) + "_variance", getVariance(field));
		}
		
		return statisticsJson;
	}
}
