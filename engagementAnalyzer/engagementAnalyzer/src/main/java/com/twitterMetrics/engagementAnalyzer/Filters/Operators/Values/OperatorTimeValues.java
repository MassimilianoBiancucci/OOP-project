package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalTime;
import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;
import com.twitterMetrics.engagementAnalyzer.supportTypes.TimeValue;

public class OperatorTimeValues implements OperatorValues{
	
	private LocalTime[] values;
	private DateParser dateParser;
	
	/**
	 * OperatorTimeValues constructor
	 * 
	 * @param values JsonElement, a JsonArray or a String that will be used as OperatorTimeValues values
	 * @throws Exception will be raised a standard exception if the passed JsonElement don't conrepsonde to the expected format
	 */
	public OperatorTimeValues(JsonElement values) throws Exception{
		
		dateParser = new DateParser();
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	private boolean checkValues(JsonElement values) throws Exception  {

		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new LocalTime[ja.size()];
			
			for(JsonElement je: ja) {
				// checking the class of this element
				// this method retrive the class of the element
				// and if is a string check the sub type date and time
				@SuppressWarnings("rawtypes")
				Class elemClass = FiltersParser.getElementClass(je);
				
				if(elemClass == TimeValue.class) {
					// if the element is a Data load it
					this.values[idx++] = dateParser.setDate(je.getAsString()).getTime();
				}else {
					return false;
				}
			}
			
		}else if(FiltersParser.getElementClass(values) == TimeValue.class) {
			
			// if the value of an operator is only one String load it.
			this.values = new LocalTime[1];
			this.values[0] = dateParser.setDate(values.getAsString()).getTime();
					
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
		throw new NotImplementedException("OperatorTimeValues dosen't have the method applayFilters() implemented.");
	}
	

	/**
	 * value getter
	 * 
	 * @return Return the first element of the internal LocalTime array
	 * @throws Exception raised if the internal LocalTime array has more than one value
	 */
	public LocalTime getValue() throws Exception {
		if(getCount() != 1) throw new Exception("OperatorTimeValues: Unexpected number of values calling getValue()");
		return this.values[0];
	}
	

	/**
	 * values getter
	 * 
	 * @return Return the internal LocalTime array
	 * @throws Exception raised if the internal LocalTime array is empty
	 */
	public LocalTime[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorTimeValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	
	@Override
	public int getCount() {
		return values.length;
	}
	
	
	@Override
	public String toString() {
		return "OperatorTimeValues [values=" + Arrays.toString(values) + ", dateParser=" + dateParser + "]";
	}
	
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	@Override
	public JsonArray toJson() {
		JsonArray jsonValues = new JsonArray();
		for(LocalTime time : this.values) 
			jsonValues.add(
				new JsonPrimitive(dateParser.setTime(time).getStrTime()));
		
		return jsonValues;
	}
}
