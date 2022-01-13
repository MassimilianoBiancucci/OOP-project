package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;

public class OperatorIntValues implements OperatorValues{
	
	private int[] values;
	
	public OperatorIntValues(int... values) {
		this.values = values;
	}
	
	public OperatorIntValues(JsonArray values) throws IncorrectOperatorValuesException{
		this.values = new int[values.size()];
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
		int idx = 0;
		for(JsonElement je: values) {
			this.values[idx++] = je.getAsJsonPrimitive().getAsInt();
		}
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorIntValues
	public boolean checkValues(JsonArray values) {
		// TODO debug
		for(JsonElement je: values) {
			JsonPrimitive primitive = je.getAsJsonPrimitive();
			if(!primitive.isNumber()) {
				return false;
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
