package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;
import com.twitterMetrics.engagementAnalyzer.supportTypes.DateValue;

public class OperatorDateValues implements OperatorValues{
	
	private LocalDateTime[] values;
	private DateParser dateParser;
	
	/**
	 * OperatorDateValues constructor
	 * 
	 * @param values JsonElement, a JsonArray or a String that will be used as OperatorDateValues values
	 * @throws Exception will be raised an exception if the element passed does not follow the correct standard.
	 */
	public OperatorDateValues(JsonElement values) throws Exception{
		
		dateParser = new DateParser();
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	private boolean checkValues(JsonElement values) throws DateTimeParseException, Exception {

		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new LocalDateTime[ja.size()];
			
			for(JsonElement je: ja) {
				// checking the class of this element
				// this method retrive the class of the element
				// and if is a string check the sub type date and time
				@SuppressWarnings("rawtypes")
				Class elemClass = FiltersParser.getElementClass(je);
				
				if(elemClass == DateValue.class) {
					// if the element is a Data load it
					this.values[idx++] = dateParser.setDate(je.getAsString()).getDate();
				}else {
					return false;
				}
			}
			
		}else if(FiltersParser.getElementClass(values) == DateValue.class) {
			
			// if the value of an operator is only one String load it.
			this.values = new LocalDateTime[1];
			this.values[0] = dateParser.setDate(values.getAsString()).getDate();
					
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
		throw new NotImplementedException("OperatorDateValues dosen't have the method applayFilters() implemented.");
	}
	

	/**
	 * value getter
	 * 
	 * @return Return the first element of the internal LocalDateTime array
	 * @throws Exception raised if the internal LocalDateTime array has more than one value
	 */
	public LocalDateTime getValue() throws Exception {
		if(getCount() != 1) throw new Exception("OperatorDateValues: Unexpected number of values calling getValue()");
		return this.values[0];
	}
	

	/**
	 * values getter
	 * 
	 * @return Return the internal LocalDateTime array
	 * @throws Exception raised if the internal LocalDateTime array is empty
	 */
	public LocalDateTime[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorDateValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	@Override
	public int getCount() {
		return values.length;
	}
	
	
	@Override
	public String toString() {
		return "OperatorDateValues [values=" + Arrays.toString(values) + ", dateParser=" + dateParser + "]";
	}
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	public JsonArray toJson() {
		JsonArray jsonValues = new JsonArray();
		for(LocalDateTime i : this.values) 
			jsonValues.add(
				new JsonPrimitive(dateParser.setDate(i).getTwitterDate()));
		
		return jsonValues;
	}
}
