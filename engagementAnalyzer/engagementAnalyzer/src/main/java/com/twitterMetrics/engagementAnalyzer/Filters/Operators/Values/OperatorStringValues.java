package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.time.format.DateTimeParseException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;

public class OperatorStringValues implements OperatorValues{
	
	private String[] values;
	
	public OperatorStringValues(JsonElement values) throws IncorrectOperatorValuesException {
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorIntValues initialized with wrong values.");
		
	}
	
	// method that check if the jsonArray contain data acceptable from OperatorDateValues
	public boolean checkValues(JsonElement values) {

		if(values.isJsonArray()) {
			
			int idx = 0;
			JsonArray ja = values.getAsJsonArray();
			this.values = new String[ja.size()];
			
			for(JsonElement je: ja) {
				// checking the class of this element
				// this method retrive the class of the element
				// and if is a string check the sub type date and time
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
	
	public int getCount() {
		return this.values.length;
	}
	
	public JsonArray toJson() {
		// TODO implement
		return new JsonArray();
	}

}
