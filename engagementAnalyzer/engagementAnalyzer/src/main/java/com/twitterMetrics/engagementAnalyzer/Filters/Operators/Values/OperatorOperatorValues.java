package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class OperatorOperatorValues implements OperatorValues{
	
private Operator[] operators;
	
	public OperatorOperatorValues(Operator[] ops) throws IncorrectOperatorValuesException{
		
		// Effettua il controllo sul tipo di operatore
		for(Operator op: ops)
			if(op instanceof LogicOperator) 
				throw new IncorrectOperatorValuesException("OperatorOperatorValues can't accept LogicOperators as argument");
		
		operators = ops;
	}
	
	public OperatorOperatorValues(Operator op) throws IncorrectOperatorValuesException{
		
		// Effettua il controllo sul tipo di operatore
		if(op instanceof LogicOperator) 
			throw new IncorrectOperatorValuesException("OperatorOperatorValues can't accept LogicOperators as argument");
		
		operators = new Operator[1];
		operators[0] = op;
	}
	
	private void checkValues() {
		
	}
	
	public int getCount() {
		return operators.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
