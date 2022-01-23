package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;

public class OperatorStringValues implements OperatorValues{
	

	private String[] values;
	
	
	/**
	 * OperatorStringValues constructor
	 * 
	 * @param values JsonElement, a JsonArray or a String that will be used as OperatorStringValues values
	 * @throws Exception will be raised a standard exception if the passed JsonElement don't conrepsonde to the expected format
	 */
	public OperatorStringValues(JsonElement values) throws IncorrectOperatorValuesException {
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	private boolean checkValues(JsonElement values) {

		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new String[ja.size()];
			
			for(JsonElement je: ja) {
				// checking the class of this element
				// this method retrive the class of the element
				// and if is a string check the sub type date and time
				@SuppressWarnings("rawtypes")
				Class elemClass = FiltersParser.getElementClass(je);
				
				if(elemClass == String.class) {
					// if the element is a string load it
					this.values[idx++] = je.getAsString();
				}else {
					return false;
				}
			}
			
		}else if(FiltersParser.getElementClass(values) == String.class) {
			
			// if the value of an operator is only one String load it.
			this.values = new String[1];
			this.values[0] = values.getAsString();
					
		}else {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method not implemented
	 * 
	 * @throws Exception if called rise an exception.
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorStringValues dosen't have the method applayFilters() implemented.");
	}
	

	/**
	 * values getter
	 * 
	 * @return Return the internal LocalTime array
	 * @throws Exception raised if the internal LocalTime array is empty
	 */
	public String[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorStringValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	
	@Override
	public int getCount() {
		return this.values.length;
	}
	
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	@Override
	public JsonArray toJson() {
		// TODO implement
		return new JsonArray();
	}
	
	@Override
	public String toString() {
		return "OperatorStringValues [values=" + Arrays.toString(values) + "]";
	}
	
}
