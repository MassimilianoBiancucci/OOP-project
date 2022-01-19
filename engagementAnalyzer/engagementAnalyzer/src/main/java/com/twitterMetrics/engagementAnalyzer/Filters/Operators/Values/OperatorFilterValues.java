package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorFilterValues implements OperatorValues{
	
	private Filter[] filters;
	
	public OperatorFilterValues(Filter[] filters) {
		
		this.filters = filters;
	}
	
	public OperatorFilterValues(Filter filter) {
		
		this.filters = new Filter[1];
		this.filters[0] = filter;
	}
	
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		
		int idx = 0;
		TweetList[] filtersTweetLists = new TweetList[filters.length];
		
		for(Filter filter: filters) {
			filtersTweetLists[idx++] = filter.applayFilters(tweetList);
		}
		
		return filtersTweetLists;
	}
	
	public int getCount() {
		return filters.length;
	}
	
	public JsonArray toJson() {
		//TODO implement
		return new JsonArray();
	}
}
