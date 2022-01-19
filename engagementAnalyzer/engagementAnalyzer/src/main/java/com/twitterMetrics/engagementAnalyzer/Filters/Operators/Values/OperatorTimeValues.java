package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalTime;
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
	// TODO modify for the managing of localDateTime only for hours minutes and seconds
	
	private LocalTime[] values;
	private DateParser dateParser;
	
	public OperatorTimeValues(JsonElement values) throws Exception{
		
		dateParser = new DateParser();
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	public boolean checkValues(JsonElement values) throws Exception  {

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
	
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorTimeValues dosen't have the method applayFilters() implemented.");
	}
	
	// values getter
	public LocalTime getValue() throws Exception {
		if(getCount() != 1) throw new Exception("OperatorTimeValues: Unexpected number of values calling getValue()");
		return this.values[0];
	}
	
	// values getter
	public LocalTime[] getValues() throws Exception {
		if(getCount() < 1) throw new Exception("OperatorTimeValues: Unexpected number of values calling getValues()");
		return this.values;
	}
	
	public int getCount() {
		return values.length;
	}
	
	
	@Override
	public String toString() {
		
		String operatorString = "OperatorDateValues[";
		for(LocalTime date : values)
			operatorString += " " + dateParser.setTime(date).getStrTime() + ",";
		operatorString += "]";
		return operatorString;
	}
	
	
	public JsonArray toJson() {
		JsonArray jsonValues = new JsonArray();
		for(LocalTime time : this.values) 
			jsonValues.add(
				new JsonPrimitive(dateParser.setTime(time).getStrTime()));
		
		return jsonValues;
	}
}
