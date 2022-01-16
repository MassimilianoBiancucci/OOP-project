package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class OperatorFilterValues implements OperatorValues{
	
	private Filter[] filters;
	
	public OperatorFilterValues(JsonPrimitive values) throws IncorrectOperatorValuesException {
		
		// TODO trovare un'altro sistema per l'immissione dei valori
		//this.filters = new Filter[values.size()];
		
		if(!checkValues(values))
			throw new IncorrectOperatorValuesException("OperatorFilterValues initialized with wrong values.");

	}
	
	// method that check if the jsonArray contain data acceptable from OperatorFilterValues
	public boolean checkValues(JsonPrimitive values) {
		// TODO debug

		if(values.isJsonObject()) {
			return checkFilter(values);
			
		}else if(values.isJsonArray()) {
			for(JsonElement je: values.getAsJsonArray()) {
				// dato che questo oggetto accetta solo filtri
				if(je.isJsonObject()) {
					// effettuo il controllo su tutti gli oggetti interni all'Arrays
					// se uno di questi non supera il controllo, il controllo è fallito.
					if(!checkFilter(je)) return false;
				}else {
					// if the content of the jsonArray isn't a jsonObject can't be a filter
					// so the check fail
					return false;
				}
			}
		}else {
			// if the data inside the json isn't a jsonObject the check is failed
			return false;
		}
		
		return true;
	}
	
	private boolean checkFilter(JsonElement value) {
		// se l'oggetto passato all'OperatorFilterValues è di tipo jsonObject questa funzione 
		// verifica che l'oggeto abbia la struttura di un filtro.
		int keysCount = 0;
		for(Map.Entry<String, JsonElement> entry : value.getAsJsonObject().entrySet()) {
			// se la chiave contenuta all'interno non è un field allora il controllo è fallito
			if(!Filter.isField(entry.getKey())) return false;
			// se all'intenro dell'oggetto c'è piu di una chiave non è un filtro e il controllo è fallito
			if(keysCount++ > 0) return false;
		}
		// se il controllo arriva a questo punto è riuscito
		return true;
	}
	
	
	
	public int getCount() {
		return filters.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
