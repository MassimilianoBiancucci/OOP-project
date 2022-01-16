package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;
import com.twitterMetrics.engagementAnalyzer.supportTypes.DateValue;

public class OperatorDateValues implements OperatorValues{
	
	private LocalDateTime[] values;
	private DateParser dateParser;
	
	public OperatorDateValues(JsonPrimitive values) throws IncorrectOperatorValuesException{
		
		dateParser = new DateParser();
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	public boolean checkValues(JsonPrimitive values) {

		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new LocalDateTime[ja.size()];
			
			for(JsonElement je: ja) {
				// checking the class of this element
				// this method retrive the class of the element
				// and if is a string check the sub type date and time
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
	
	public int getCount() {
		return values.length;
	}
	
	
	@Override
	public String toString() {
		
		String operatorString = "OperatorDateValues[";
		for(LocalDateTime date : values)
			operatorString += " " + dateParser.setDate(date).getTwitterDate() + ",";
		operatorString += "]";
		return operatorString;
	}
	
	
	public JsonArray toJson() {
		JsonArray jsonValues = new JsonArray();
		for(LocalDateTime i : this.values) 
			jsonValues.add(
				new JsonPrimitive(dateParser.setDate(i).getTwitterDate()));
		
		return jsonValues;
	}
}
