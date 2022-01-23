package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorLogicOperatorValues implements OperatorValues{
	

	private LogicOperator[] operators;
	
	/**
	 * OperatorLogicOperatorValues constructor
	 * 
	 * @param values LogicOperator array, contain the logic operators that will be sed as values
	 */
	public OperatorLogicOperatorValues(LogicOperator[] logicOps) {
		
		operators = logicOps;
	}
	
	public OperatorLogicOperatorValues(LogicOperator logicOp) {
		
		operators = new LogicOperator[1];
		operators[0] = logicOp;
	}
	

	/**
	 * This method call all the applayFilters method from each sub-operator an retrive all their results
	 * 
	 * @param tweetList the TweetList that will be filtered from each sub object
	 * @return an array of TweetList retrived from each sub Operator
	 * @throws Exception an exception is rised if some sub operator raise an exception in the applayFilters sub call.
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		
		int idx = 0;
		TweetList[] operatorsTweetLists = new TweetList[operators.length];
		
		for(LogicOperator opl: operators) {
			operatorsTweetLists[idx++] = opl.applayFilters(tweetList);
		}
		
		return operatorsTweetLists;
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
	@Override
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
	
	@Override
	public String toString() {
		return "OperatorLogicOperatorValues [operators=" + Arrays.toString(operators) + "]";
	}

}
