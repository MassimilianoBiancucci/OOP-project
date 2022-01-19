package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorLogicOperatorValues implements OperatorValues{
	
private LogicOperator[] operators;
	
	public OperatorLogicOperatorValues(LogicOperator[] logicOps) {
		
		operators = logicOps;
	}
	
	public OperatorLogicOperatorValues(LogicOperator logicOp) {
		
		operators = new LogicOperator[1];
		operators[0] = logicOp;
	}
	
	// This method call all the applayFilters method from 
	// each sub-operator
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		
		int idx = 0;
		TweetList[] operatorsTweetLists = new TweetList[operators.length];
		
		for(LogicOperator opl: operators) {
			operatorsTweetLists[idx++] = opl.applayFilters(tweetList);
		}
		
		return operatorsTweetLists;
	}
	
	public int getCount() {
		return operators.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
