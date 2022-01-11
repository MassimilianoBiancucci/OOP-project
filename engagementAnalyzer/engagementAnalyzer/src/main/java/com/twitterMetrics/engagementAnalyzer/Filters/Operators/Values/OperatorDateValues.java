package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;

public class OperatorDateValues implements OperatorValues{
	
	private LocalDateTime[] values;
	private static DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	
	public int getCount() {
		return values.length;
	}
	
	@Override
	public String toString() {
		
		String operatorString = "OperatorDateValues[";
		for(LocalDateTime date : values)
			operatorString = operatorString + " " + date.format(inputFormat) + ",";
		operatorString = operatorString + "]";
		return operatorString;
	}
	
	public JsonArray toJson() {
		JsonArray jsonValues = new JsonArray();
		for(LocalDateTime i : this.values) 
			jsonValues.add(
				new JsonPrimitive(i.format(inputFormat)));
		
		return jsonValues;
	}

	@Override
	public boolean checkValues(JsonArray values) {
		// TODO Auto-generated method stub
		return false;
	}
}
