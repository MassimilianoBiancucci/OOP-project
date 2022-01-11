package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class OperatorIntValues implements OperatorValues{
	
	private int[] values;
	
	public OperatorIntValues(int... values) {
		this.values = values;
	}
	
	public OperatorIntValues(JsonArray values) {
		
		
		
		this.values = new int[values.size()];
		
	}
	
	public OperatorIntValues(String values) {
		
	}
	
	public boolean checkValues(JsonArray values) {
		
		for(JsonElement je: values) {
			if(je.getAsJsonPrimitive().isNumber()) {
				je.getAsJsonPrimitive()
			}
		}
		
		return true;
	}
	
	public int getCount() {
		return this.values.length;
	}
	
	public JsonArray toJson() {
		
		JsonArray jsonValues = new JsonArray();
		for(int i : this.values) jsonValues.add(new JsonPrimitive(i));
		return jsonValues;
	}
	
}
