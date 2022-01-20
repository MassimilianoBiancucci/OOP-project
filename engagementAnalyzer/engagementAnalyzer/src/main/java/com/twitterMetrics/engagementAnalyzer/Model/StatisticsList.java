package com.twitterMetrics.engagementAnalyzer.Model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;

public class StatisticsList {
	
	private Map<Field, Statistic> statistics;
	private Field[] fields;
	
	public StatisticsList(Field[] fields) {
		
		this.fields = fields;
		statistics = new HashMap<Field, Statistic>();
		for(Field field: fields) statistics.put(field, new Statistic());
	}
	
	public void add(Field field, double val) {
		statistics.get(field).add(val);
	}
	
	public double getMean(Field field) {
		return statistics.get(field).getMean();
	}
	
	public double getVariance(Field field) {
		return statistics.get(field).getVariance();
	}
	
	public void clear() {
		for(Statistic stat: statistics.values()) stat.clear();
	}
	
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
