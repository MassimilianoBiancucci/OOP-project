package com.twitterMetrics.engagementAnalyzer.Filters;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectFilterFieldException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class MetricFilter extends Filter{
	
	public static final Map<String, Field> metricMap = Map.ofEntries(
		entry("retweet_count", 		Field.retweet),
	    entry("reply_count", 		Field.reply),
	    entry("like_count", 		Field.like),
	    entry("quote_count", 		Field.quote),
	    entry("engagement", 		Field.engagement)
	);
	
	public MetricFilter(String field, Operator op) throws Exception {
		
		super(field, op);
	}

	@Override
	protected boolean validateField(String field) throws Exception {
		if(!metricMap.containsKey(field)) {
			// if the symbol isn't acceptable
			throw new IncorrectFilterFieldException("the field passed to the message filter isn't accepted!");
		}
		
		this.field = fieldsMap.get(field);
		return true;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// method that return true if the field match a Metric Filter 
	public static boolean isField(String field) {
		return metricMap.containsKey(field);
	}
	
}
