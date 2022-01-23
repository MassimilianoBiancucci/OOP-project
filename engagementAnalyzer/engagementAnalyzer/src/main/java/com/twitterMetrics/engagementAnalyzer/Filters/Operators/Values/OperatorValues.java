package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public interface OperatorValues {
	
	
	/**
	 * this method will be implemented on OperatorFilterValues and OperatorLogicOperatorValues 
	 * because they have as content complex objects that has inside one or more tweetlists.
	 * The other operartorsValues like OperatorIntValues or OperatorStringValues and the others 
	 * don't implement this function and rise an error if called.
	 * 
	 * @param tweetList 
	 * @return
	 * @throws Exception 
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception;
	
	public int getCount();
	
	public JsonArray toJson();
	
}
