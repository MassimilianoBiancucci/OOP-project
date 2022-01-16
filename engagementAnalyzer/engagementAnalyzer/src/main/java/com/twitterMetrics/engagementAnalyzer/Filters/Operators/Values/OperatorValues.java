package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public interface OperatorValues {
	
	public boolean checkValues(JsonPrimitive values);
	
	public int getCount();
	
	public JsonArray toJson();
	
}
