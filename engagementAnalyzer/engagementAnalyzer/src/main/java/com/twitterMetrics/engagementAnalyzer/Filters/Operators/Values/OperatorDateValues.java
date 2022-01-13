package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;

public class OperatorDateValues implements OperatorValues{
	
	private LocalDateTime[] values;
	private DateParser dateParser;
	
	public OperatorDateValues(JsonArray values) throws IncorrectOperatorValuesException{
		this.values = new LocalDateTime[values.size()];
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
		int idx = 0;
		for(JsonElement je: values) {
			dateParser.setDate(je.getAsJsonPrimitive().getAsString());
			this.values[idx++] = dateParser.getDate();
		}
	}
	
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	public boolean checkValues(JsonArray values) {
		// TODO debug
		for(JsonElement je: values) {
			JsonPrimitive primitive = je.getAsJsonPrimitive();
			if(!primitive.isString()) {
				// if the data inside the json isn't string the check is failed
				return false;
			}else{
				try {
					dateParser.setDate(primitive.getAsString());
				}catch(DateTimeParseException e) {
					// if the parser fail in the parsing of the date the check is failed
					return false;
				}
			}
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
