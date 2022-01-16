package com.twitterMetrics.engagementAnalyzer.Filters;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectFilterFieldException;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class DateFilter extends Filter{
	
	public static final Map<String, Field> dateMap = Map.ofEntries(
	    entry("created_at", 		Field.date)
	);
	
	public DateFilter(String field, Operator op) throws Exception {
		
		super(field, op);
	}
	
	public DateFilter(Field field, Operator op) throws Exception {
		this(field2String.get(field), op);
	}
	
	@Override
	protected boolean validateField(String field) throws Exception {
		if(!dateMap.containsKey(field)) {
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
	
	// method that return true if the field match a Message Filter 
	public static boolean isField(String field) {
		return dateMap.containsKey(field);
	}
	
	// method that return true if the symbol match an operator 
	public static boolean isField(Field op) {
		return dateMap.containsValue(op);
	}
}
