package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorIntValues implements OperatorValues{
	


	private int[] values;
	
	
	/**
	 * OperatorIntValues constructor
	 * 
	 * @param values JsonElement, a JsonArray or an int that will be used as OperatorIntValues values
	 * @throws IncorrectOperatorValuesException will be raised a standard exception if the passed JsonElement don't conrepsonde to the expected format
	 */
	public OperatorIntValues(JsonElement values) throws IncorrectOperatorValuesException{
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorIntValues
	private boolean checkValues(JsonElement values) {
		
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
	
	
	/**
	 * Method not implemented
	 * 
	 * @throws Exception if called rise an exception.
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorIntValues dosen't have the method applayFilters() implemented.");
	}
	
	/**
	 * value getter
	 * 
	 * @return Return the first element of the internal int array
	 * @throws Exception raised if the internal int array has more than one value
	 */
	public int getValue() throws Exception {
		if(getCount() != 1) throw new Exception("OperatorIntValues: Unexpected number of values calling getValue()");
		return this.values[0];
	}
	

	/**
	 * values getter
	 * 
	 * @return Return the internal int array
	 * @throws Exception raised if the internal int array is empty
	 */
	public int[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorIntValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	// get the count of values
	@Override
	public int getCount() {
		return this.values.length;
	}
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	public JsonArray toJson() {
		
		JsonArray jsonValues = new JsonArray();
		for(int i : this.values) jsonValues.add(new JsonPrimitive(i));
		return jsonValues;
	}
	
	@Override
	public String toString() {
		return "OperatorIntValues [values=" + Arrays.toString(values) + "]";
	}
	
}
