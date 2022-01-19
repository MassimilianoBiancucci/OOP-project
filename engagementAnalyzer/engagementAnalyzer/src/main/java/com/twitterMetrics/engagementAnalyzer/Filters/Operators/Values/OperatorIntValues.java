package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorIntValues implements OperatorValues{
	
	private int[] values;
	
	public OperatorIntValues(JsonElement values) throws IncorrectOperatorValuesException{
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorIntValues
	public boolean checkValues(JsonElement values) {
		
		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new int[ja.size()];
			
			for(JsonElement je: ja) {
				JsonPrimitive jp = je.getAsJsonPrimitive();
				if(jp.isNumber()) {
					// if the data inside the json isn't a Number the check is failed
					this.values[idx++] = jp.getAsInt();
				}else {
					return false;
				}
			}
			
		}else {
			
			JsonPrimitive jp = values.getAsJsonPrimitive();
			if(jp.isNumber()) {
			
				// if the value of an operator is only one number load it.
				this.values = new int[1];
				this.values[0] = jp.getAsInt();
					
			}else {
				return false;
			}	
		}
		
		return true;
	}
	
	
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorIntValues dosen't have the method applayFilters() implemented.");
	}
	
	// values getter
	public int getValue() throws Exception {
		if(getCount() != 1) throw new Exception("OperatorIntValues: Unexpected number of values calling getValue()");
		return this.values[0];
	}
	
	// values getter
	public int[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorIntValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	// get the count of values
	public int getCount() {
		return this.values.length;
	}
	
	// conversion methods
	public JsonArray toJson() {
		
		JsonArray jsonValues = new JsonArray();
		for(int i : this.values) jsonValues.add(new JsonPrimitive(i));
		return jsonValues;
	}
	
}
