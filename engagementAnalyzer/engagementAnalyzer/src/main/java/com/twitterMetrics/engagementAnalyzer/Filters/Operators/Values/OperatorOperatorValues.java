package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.NotImplementedException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorOperatorValues implements OperatorValues{
	


private Operator[] operators;
	
	/**
	 * OperatorOperatorValues constructor
	 * 
	 * @param ops Operator array, that contain the Operators passed as values
	 * @throws Exception will be raised if the operator passed aren't supported.
	 */
	public OperatorOperatorValues(Operator[] ops) throws IncorrectOperatorValuesException{
		
		// Effettua il controllo sul tipo di operatore
		for(Operator op: ops)
			if(op instanceof LogicOperator) 
				throw new IncorrectOperatorValuesException("OperatorOperatorValues can't accept LogicOperators as argument");
		
		operators = ops;
	}
	
	
	/**
	 * OperatorOperatorValues constructor
	 * 
	 * @param op Operator passed as internal value
	 * @throws Exception will be raised if the operator passed isn't supported.
	 */
	public OperatorOperatorValues(Operator op) throws IncorrectOperatorValuesException{
		
		// Effettua il controllo sul tipo di operatore
		if(op instanceof LogicOperator) 
			throw new IncorrectOperatorValuesException("OperatorOperatorValues can't accept LogicOperators as argument");
		
		operators = new Operator[1];
		operators[0] = op;
	}
	
	/**
	 * Method not implemented
	 * 
	 * @throws Exception if called rise an exception.
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		throw new NotImplementedException("OperatorOperatorValues dosen't have the method applayFilters() implemented");
	}
	
	@Override
	public int getCount() {
		return operators.length;
	}
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
	
	@Override
	public String toString() {
		return "OperatorOperatorValues [operators=" + Arrays.toString(operators) + "]";
	}
}
