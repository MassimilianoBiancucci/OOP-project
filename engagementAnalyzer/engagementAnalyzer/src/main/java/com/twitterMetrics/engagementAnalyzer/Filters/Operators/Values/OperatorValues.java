package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;

public interface OperatorValues {
	
	public boolean checkValues(JsonArray values);
	
	public int getCount();
	
	public JsonArray toJson();
	
}
