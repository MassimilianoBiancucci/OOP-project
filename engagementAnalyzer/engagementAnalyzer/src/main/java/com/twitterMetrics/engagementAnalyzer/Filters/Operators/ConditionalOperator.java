package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;

public class ConditionalOperator extends Operator{

	public ConditionalOperator(String sym, OperatorValues values) throws Exception {
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
