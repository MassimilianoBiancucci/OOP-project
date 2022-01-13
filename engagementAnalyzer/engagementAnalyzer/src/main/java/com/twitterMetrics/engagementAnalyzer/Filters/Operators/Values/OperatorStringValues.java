package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.format.DateTimeParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;

public class OperatorStringValues implements OperatorValues{
	
	private String[] values;
	
	public OperatorStringValues(JsonArray values) throws IncorrectOperatorValuesException {
		this.values = new String[values.size()];
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
		int idx = 0;
		for(JsonElement je: values) {
			this.values[idx++] = je.getAsJsonPrimitive().getAsString();
		}
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	public boolean checkValues(JsonArray values) {
		// TODO debug
		for(JsonElement je: values) {
			JsonPrimitive primitive = je.getAsJsonPrimitive();
			if(!primitive.isString()) {
				// if the data inside the json isn't string the check is failed
				return false;
			}
		}
		
		return true;
	}
	
	public int getCount() {
		return this.values.length;
	}
	
	public JsonArray toJson() {
		// TODO implement
		return new JsonArray();
	}

}
