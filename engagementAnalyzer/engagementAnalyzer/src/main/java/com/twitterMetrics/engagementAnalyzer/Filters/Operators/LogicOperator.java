package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorFilterValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorLogicOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
import com.twitterMetrics.engagementAnalyzer.Model.Tweet;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class LogicOperator extends Operator{
	
	public static final Map<String, SymOp> logicSymbolsMap = Map.ofEntries(
			entry("$nop", SymOp.nop),
		    entry("$not", SymOp.not),
		    entry("$or", SymOp.or),
		    entry("$and", SymOp.and)
		);
	
	public LogicOperator(String sym, OperatorValues values) throws Exception {
		
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
		
	}
	
	public LogicOperator(SymOp sym, OperatorValues values) throws Exception {
		this(symbol2String.get(sym), values);
	}
	
	// Definition of the validation function for the symbol
	protected boolean validateSymbol(String sym) throws Exception{
		
		if(!logicSymbolsMap.containsKey(sym)) {
			// if the symbol isn't acceptable
			throw new IncorrectOperatorSymbolException("the symbol passed it's not a logical operator!");
		}
		
		this.symbol = symbolsMap.get(sym);
		return true;
	}
	
	// this method should be present inside a 
	@Override
	public TweetList applayFilters(TweetList tweetList, Field field) throws Exception{
		throw new Exception("applayFilters() isn't supported by LogicOperators.");
	}
	
	public TweetList applayFilters(TweetList tweetList) throws Exception  {
		
		if(operatorValues instanceof OperatorLogicOperatorValues |
			operatorValues instanceof OperatorFilterValues) {
			
			switch(this.symbol) {
				case nop:
					// the nop operator does nothing, it's only a pass trough
					return applyNop(tweetList);
					
				case not:
					// the not operator take the original tweets list and remove all the tweets 
					// present in the returned list from the Operator content 
					return applyNot(tweetList);

				case and:
					// the and operator retrive many tweets list from each sub object and return 
					// a tweet list with tweets present in all the sub lists 
					return applyAnd(tweetList);

				case or:
					// the or operator retrive many tweets list from each sub object and return 
					// a tweet list with all the tweets retrived 
					return applyOr(tweetList);

			default:
				throw new Exception("Unexpected Operator symbol inside LogicOperator");
			}
		}else {
			
			if(operatorValues == null && symbol == SymOp.nop) {
				// if the operatorValue is null and the operator is nop 
				// than maybe filters aren't in the request body, so return the passed tweetList. 
				return tweetList;
			}
			
			throw new Exception("Unexpected OperatorValues inside LogicOperator");
		}
		
	}
	
	private TweetList applyNop(TweetList tweetList) throws Exception {
		// this method only check if there's only one object inside
		// than return the tweetlist from it.
		
		TweetList[] resultTweetLists = operatorValues.applayFilters(tweetList);
		
		if(resultTweetLists.length != 1) 
			throw new Exception("Unexpected number of objects inside $nop logicOperator.");
		
		return resultTweetLists[0];
		
	}
	
	private TweetList applyNot(TweetList tweetList) throws Exception {
		// This method accept only one operator inside, if more operators are inside raise an exception 
		// Each tweet in the tweetList retrived by the operator inside is removed from a copy of the original 
		// tweetList.
		
		TweetList[] resultTweetLists = operatorValues.applayFilters(tweetList);
		
		if(resultTweetLists.length != 1) 
			throw new Exception("Unexpected number of objects inside $not logicOperator.");
		
		TweetList tweetListCopy = tweetList.clone();
		TweetList resultTweetList = resultTweetLists[0];
		
		for(int i = 0; i < resultTweetList.size(); i++) {
			tweetListCopy.remove(resultTweetList.get(i));
		}
		
		return tweetListCopy;
	}
	
	private TweetList applyAnd(TweetList tweetList) throws Exception {
		// This method perform the logic intersection over the tweets 
		// present in the lists of the contained operators or filters.
		
		TweetList[] resultTweetLists = operatorValues.applayFilters(tweetList);
		TweetList intersecList = new TweetList();
		
		if(resultTweetLists.length < 1) {
			// if there arent operators inside rise an error
			throw new Exception("Unexpected number of objects 0 inside $and logicOperator.");
			
		}else if(resultTweetLists.length == 1) {
			// if the object inside is only one same behaviour of $nop as passtrough
			return resultTweetLists[0];
			
		}else {
			// if there are at least 2 tweetLists as result create another list
			// only with objects presents in all the lists 
			
			int nLists = resultTweetLists.length;
			
			// iter all the elements of the first retrived list
			for(int i = 0; i < resultTweetLists[0].size(); i++) {
				
				Tweet targetTweet = resultTweetLists[0].get(i);
				boolean accepted = false;
				
				// check if that tweet is present in all the other lists
				for(int j = 1; j < nLists; j++) {
					accepted &= resultTweetLists[j].containsTweet(targetTweet.getTweetId());
				}
				
				// if present load it in the new list
				if(accepted) intersecList.add(targetTweet);
			}
			
			return intersecList;
		}
	}
	
	private TweetList applyOr(TweetList tweetList) throws Exception {
		
		TweetList[] resultTweetLists = operatorValues.applayFilters(tweetList);
		TweetList unionList = new TweetList();
		
		if(resultTweetLists.length < 1) {
			// if there arent operators inside rise an error
			throw new Exception("Unexpected number of objects 0 inside $or logicOperator.");
			
		}else if(resultTweetLists.length == 1) {
			// if the object inside is only one same behaviour of $nop as passtrough
			return resultTweetLists[0];
			
		}else {
			// if there are at least 2 tweetLists as result create another list
			// with all the objects presents in all the lists 
			
			int nLists = resultTweetLists.length;
			
			// iter all the lists
			for(int i = 0; i < nLists; i++) {
				
				// iter all the elements of the current list
				for(int j = 0; j < resultTweetLists[i].size(); j++) {
					
					// extract one tweet from the current list
					Tweet targetTweet = resultTweetLists[i].get(j);
					
					// check if the tweet is already present inside the list
					// otherwise add it to the list.
					if(!unionList.containsTweet(targetTweet.getTweetId()))
						unionList.add(targetTweet);
				}
			}
			return unionList;
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
	
	// method that return true if the symbol match a logical operator 
	public static boolean isOperator(String op) {
		return logicSymbolsMap.containsKey(op);
	}
		
}
