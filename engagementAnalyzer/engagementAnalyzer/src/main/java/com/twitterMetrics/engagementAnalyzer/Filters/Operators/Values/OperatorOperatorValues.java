package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

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
	
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorOperatorValues dosen't have the method applayFilters() implemented");
	}
	
	public int getCount() {
		return operators.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
