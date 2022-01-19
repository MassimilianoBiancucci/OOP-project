package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import static java.util.Map.entry;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.DateFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;
import com.twitterMetrics.engagementAnalyzer.Filters.MetricFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.TimeFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorDateValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorIntValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorTimeValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class ConditionalOperator extends Operator{

	public static final Map<String, SymOp> conditionalSymbolsMap = Map.ofEntries(
	    entry("$gt", SymOp.gt),
	    entry("$gte", SymOp.gte),
	    entry("$lt", SymOp.lt),
	    entry("$lte", SymOp.lte),
	    entry("$bt", SymOp.bt)
	);
	
	public ConditionalOperator(String sym, OperatorValues values) throws Exception {
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
	}
	
	public ConditionalOperator(SymOp sym, OperatorValues values) throws Exception {
		this(symbol2String.get(sym), values);
	}
	
	// Definition of the validation function for the symbol
	protected boolean validateSymbol(String sym) throws Exception{
		
		if(!conditionalSymbolsMap.containsKey(sym)) {
			// if the symbol isn't acceptable
			throw new IncorrectOperatorSymbolException("the symbol passed it's not a logical operator!");
		}
		
		this.symbol = symbolsMap.get(sym);
		return true;
	}
	
	
	public TweetList applayFilters(TweetList tweetList, Field field) throws Exception {
		

		if(MetricFilter.isField(field)){
			// this case cover all the fields that need int values
			return applyCond2Metrics(tweetList, field);
			
		}else if(DateFilter.isField(field)){
			// this case cover all the fields that need Date values
			return applyCond2Date(tweetList);
			
		}else if(TimeFilter.isField(field)){
			// this case cover all the fields that need Time values
			return applyCond2Time(tweetList);
				
		}else {
				throw new Exception("Unexpected Filter field: " + Filter.field2String.get(field) + " associated to a ConditionalOperator.");
		}
	}
	
	
	// this function apply the right operator to the tweet list considering the passed field
	// specificaly apply conditional operators to int fields
	private TweetList applyCond2Metrics(TweetList tweetList, Field field) throws Exception{
		//The first step is the control of the field and the operator values type
		
		if(!MetricFilter.isField(field)) 
			throw new Exception("applyCond2Metrics() method accept only MetricFilter fields.");
		
		if(!(this.operatorValues instanceof OperatorIntValues)) 
			throw new Exception("applyCond2Metrics() method accept only OperatorsValues of sub class OperatorIntValues.");
		
		TweetList resultTweetList = new TweetList();
		int compVal = 0;
		
		switch(this.symbol) {
			case gt:
				// application of the operator 'greater' on Metrics field
				
				compVal = ((OperatorIntValues) this.operatorValues).getValue();
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getIntField(field, i) > compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case gte:
				// application of the operator 'greater or equal' on Metrics field
				
				compVal = ((OperatorIntValues) this.operatorValues).getValue();
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getIntField(field, i) >= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lt:
				// application of the operator 'less than' on Metrics field
				
				compVal = ((OperatorIntValues) this.operatorValues).getValue();
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getIntField(field, i) < compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lte:
				// application of the operator 'less then or equal' on Metrics field
				
				compVal = ((OperatorIntValues) this.operatorValues).getValue();
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getIntField(field, i) <= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case bt:
				// application of the operator 'between' on Metrics field
				int[] compValues = ((OperatorIntValues) this.operatorValues).getValues();
				
				if(compValues.length != 2) 
					throw new Exception("applyCond2Metrics() unexpected number of values inside $bt operator.");
				
				for(int i = 0; i < tweetList.size(); i++) {
					
					int tweetFiledVal = tweetList.getIntField(field, i);
					
					if( tweetFiledVal >= compValues[0] && tweetFiledVal <= compValues[1]) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			default:
				throw new Exception("Unexpected Operator symbol inside ConditionalOperator.");
		}
	}
	
	// this function apply the right operator to the tweet list considering the passed field
	// specificaly apply conditional operators to Date fields
	private TweetList applyCond2Date(TweetList tweetList) throws Exception{
		//The first step is the control of the operator values type
		
		if(!(this.operatorValues instanceof OperatorDateValues)) 
			throw new Exception("applyCond2Date() method accept only OperatorsValues of sub class OperatorDateValues.");
		
		TweetList resultTweetList = new TweetList();
		
		ZoneOffset offset = ZoneOffset.of("+00:00");
		long compVal;
		
		switch(this.symbol) {
			case gt:
				// application of the operator 'greater' on Date field
				
				compVal = ((OperatorDateValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) > compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case gte:
				// application of the operator 'greater or equal' on Date field
				
				compVal = ((OperatorDateValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) >= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lt:
				// application of the operator 'less than' on Date field
				
				compVal = ((OperatorDateValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) < compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lte:
				// application of the operator 'less then or equal' on Date field
				
				compVal = ((OperatorDateValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) <= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case bt:
				// application of the operator 'between' on Date field
				LocalDateTime[] compValues = ((OperatorDateValues) this.operatorValues).getValues();
				
				if(compValues.length != 2) 
					throw new Exception("applyCond2Time() unexpected number of values inside $bt operator.");
				
				for(int i = 0; i < tweetList.size(); i++) {
					
					long tweetFieldVal = tweetList.getDate(i).toEpochSecond(offset);
					
					if( tweetFieldVal >= compValues[0].toEpochSecond(offset) && 
						tweetFieldVal <= compValues[1].toEpochSecond(offset)) {
						
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			default:
				throw new Exception("Unexpected Operator symbol inside ConditionalOperator.");
		}
	}
	
	// this function apply the right operator to the tweet list considering the passed field
	// specificaly apply conditional operators considering time slots
	private TweetList applyCond2Time(TweetList tweetList) throws Exception{
		//The first step is the control of the operator values type
		
		if(!(this.operatorValues instanceof OperatorTimeValues)) 
			throw new Exception("applyCond2Time() method accept only OperatorsValues of sub class OperatorTimeValues.");
		
		TweetList resultTweetList = new TweetList();
		
		ZoneOffset offset = ZoneOffset.of("+00:00");
		long compVal;
		
		switch(this.symbol) {
			case gt:
				// application of the operator 'greater' on Time field
				
				compVal = ((OperatorTimeValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) > compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case gte:
				// application of the operator 'greater or equal' on Time field
				
				compVal = ((OperatorTimeValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) >= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lt:
				// application of the operator 'less than' on Time field
				
				compVal = ((OperatorTimeValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) < compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			case lte:
				// application of the operator 'less then or equal' on Time field
				
				compVal = ((OperatorTimeValues) this.operatorValues).getValue().toEpochSecond(offset);
				for(int i = 0; i < tweetList.size(); i++) {
					
					if(tweetList.getDate(i).toEpochSecond(offset) <= compVal) {
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
				
			case bt:
				// application of the operator 'between' on Time field
				LocalDateTime[] compValues = ((OperatorTimeValues) this.operatorValues).getValues();
				
				if(compValues.length != 2) 
					throw new Exception("applyCond2Time() unexpected number of values inside $bt operator.");
				
				for(int i = 0; i < tweetList.size(); i++) {
					
					long tweetFieldVal = tweetList.getDate(i).toEpochSecond(offset);
					
					if( tweetFieldVal >= compValues[0].toEpochSecond(offset) && 
						tweetFieldVal <= compValues[1].toEpochSecond(offset)) {
						
						resultTweetList.add(tweetList.get(i));
					}
				}
				return resultTweetList;
		
			default:
				throw new Exception("Unexpected Operator symbol inside ConditionalOperator.");
		}
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
	
	// method that return true if the symbol match a Conditional operator 
	public static boolean isOperator(String op) {
		return conditionalSymbolsMap.containsKey(op);
	}
}
