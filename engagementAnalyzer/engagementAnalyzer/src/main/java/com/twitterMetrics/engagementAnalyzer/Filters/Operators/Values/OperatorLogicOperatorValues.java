package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;

public class OperatorLogicOperatorValues implements OperatorValues{
	
private LogicOperator[] operators;
	
	public OperatorLogicOperatorValues(LogicOperator[] logicOps) {
		
		operators = logicOps;
	}
	
	public OperatorLogicOperatorValues(LogicOperator logicOp) {
		
		operators = new LogicOperator[1];
		operators[0] = logicOp;
	}
	
	public int getCount() {
		return operators.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
