package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class OperatorFilterValues implements OperatorValues{
	
	private Filter[] filters;
	
	public OperatorFilterValues(JsonArray values) throws IncorrectOperatorValuesException {
		
		this.filters = new Filter[values.size()];
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorFilterValues initialized with wrong values.");
		
		
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorFilterValues
	public boolean checkValues(JsonArray values) {
		// TODO debug
		for(JsonElement je: values) {
			JsonPrimitive primitive = je.getAsJsonPrimitive();
			if(!primitive.isJsonObject()) {
				// if the data inside the json isn't a jsonObject the check is failed
				return false;
			}
			// TODO check if the content of the jsonObject is a supported Filter!
		}
		
		return true;
	}
	
	public int getCount() {
		return filters.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
