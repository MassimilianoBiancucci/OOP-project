package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;
import com.twitterMetrics.engagementAnalyzer.Filters.MessageFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorStringValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class MatchOperator extends Operator{
	
	
	/**
	 * Map that relate enum Operator symbols to the equivalent in string format
	 */
	public static final Map<String, SymOp> matchSymbolsMap = Map.ofEntries(
	    entry("$in", SymOp.in),
	    entry("$nin", SymOp.nin)
	);
	
	
	/**
	 * MatchOperator constructor
	 * 
	 * @param sym String that encode the symbol operator that will be applied with this object
	 * @param values OperatorValues that will be applied with the passed Operator to some filter
	 * @throws Exception generic exceptions raised if the symbol operator is not accepted or the OperatorValues and the passed symbol operator aren't compatible.
	 */
	public MatchOperator(String sym, OperatorValues values) throws Exception {
		
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
		
	}
	
	
	/**
	 * MatchOperator constructor
	 * 
	 * @param sym enum that encode the symbol operator that will be applied with this object
	 * @param values OperatorValues that will be applied with the passed Operator to some filter
	 * @throws Exception generic exceptions raised if the symbol operator is not accepted or the OperatorValues and the passed symbol operator aren't compatible.
	 */
	public MatchOperator(SymOp sym, OperatorValues values) throws Exception {
		this(symbol2String.get(sym), values);
	}
	
	// Definition of the validation function for the symbol
	protected boolean validateSymbol(String sym) throws Exception{
		
		if(!matchSymbolsMap.containsKey(sym)) {
			// if the symbol isn't acceptable
			throw new IncorrectOperatorSymbolException("the symbol passed it's not a logical operator!");
		}
		
		this.symbol = symbolsMap.get(sym);
		return true;
	}
	
	/**
	 * Method that apply the filter to a tweetList
	 * 
	 * @param tweetList List of tweet that will be filtered.	 
	 * @param field tweet field target of the filter.
	 * @throws Exception raised if there are some unexpected operator symbols or Filter field.
	 */
	public TweetList applayFilters(TweetList tweetList, Field field) throws Exception {
		// 
		if(MessageFilter.isField(field)){
			// this case cover the text filed 
			
			// checking if the operatorValues are of the correct subclas
			if(!(this.operatorValues instanceof OperatorStringValues)) 
				throw new Exception("MatchOperator.applayFilters() method accept only OperatorsValues of sub class OperatorStringValues.");
			
			String[] targetStrings = ((OperatorStringValues) this.operatorValues).getValues();
			TweetList resultTweetList = new TweetList();
			
			if(this.symbol == SymOp.in) {
				// apply match filter, create a TweetList with all the tweets that contain inside the message field 
				// one or more of the specified Strings 
				
				// iter all the tweets in the request
				for(int i = 0; i < tweetList.size(); i++) {
					// iter all the strings in the target list
					for(String target: targetStrings) {
						// checkif the current tweet contain the target string
						if(tweetList.getMsg(i).contains(target)) {
							// if the target string is matched add it to the result tweet list
							// and break the match of all the strings on this tweet
							resultTweetList.add(tweetList.get(i));
							break;
						}
					}
				}
				
				return resultTweetList;
				
			}else if(this.symbol == SymOp.nin) {
				// apply not match filter, create a TweetList with all the tweets that do not contain 
				// any of the specified Strings inside the message field 
				
				// flag used to report if the current tweet contain come target string
				boolean matched = false;
				
				// iter all the tweets in the request
				for(int i = 0; i < tweetList.size(); i++) {
					
					// reset the match flag
					matched = false;
					
					// iter all the strings in the target list
					for(String target: targetStrings) {
						// checkif the current tweet contain the target string
						if(tweetList.getMsg(i).contains(target)) {
							// if the target string is matched add it to the result tweet list
							matched = true;
							break;
						}
					}
					
					// if there isn't any of the target strings inside the message 
					// than add the tweet to the result tweet list
					if(!matched)
						resultTweetList.add(tweetList.get(i));
				}
				
				return resultTweetList;
				
			}else {
				throw new Exception("MatchOperator.applayFilters() called with Unexpected symbol.");
			}
			
		}else {
				throw new Exception("Unexpected Filter field: " + Filter.field2String.get(field) + " associated to a MatchOperator.");
		}
	}
	
	
	@Override
	public String toString() {
		return "MatchOperator [symbol=" + symbol + ", operatorValues=" + operatorValues + "]";
	}
	
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Static method that return true if the symbol match a suported MatchOperator symbol.
	 * 
	 * @param op String tested to match if it is a MatchOperator symbol.
	 * @return true if is a MatchOperator symbol.
	 */
	public static boolean isOperator(String op) {
		return matchSymbolsMap.containsKey(op);
	}
}
