package com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values;

import java.util.Arrays;

import com.google.gson.JsonArray;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class OperatorFilterValues implements OperatorValues{
	
	private Filter[] filters;
	
	
	/**
	 * OperatorFilterValues constructor
	 * 
	 * @param filters Filter array, Filters that will be used as values of this object
	 */
	public OperatorFilterValues(Filter[] filters) {
		
		this.filters = filters;
	}
	
	
	/**
	 * OperatorFilterValues constructor
	 * 
	 * @param filter Filter that will be used as value of this object
	 */
	public OperatorFilterValues(Filter filter) {
		
		this.filters = new Filter[1];
		this.filters[0] = filter;
	}
	
	
	/**
	 * This method call all the applayFilters method from each sub-Filter and retrive all their results
	 * 
	 * @param tweetList the TweetList that will be filtered from each sub object
	 * @return an array of TweetList retrived from each sub Filter
	 * @throws Exception an exception is rised if some sub Filter raise an exception in the applayFilters sub call.
	 */
	public TweetList[] applayFilters(TweetList tweetList) throws Exception {
		
		int idx = 0;
		TweetList[] filtersTweetLists = new TweetList[filters.length];
		
		for(Filter filter: filters) {
			filtersTweetLists[idx++] = filter.applayFilters(tweetList);
		}
		
		return filtersTweetLists;
	}
	
	@Override
	public int getCount() {
		return filters.length;
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
		return "OperatorFilterValues [filters=" + Arrays.toString(filters) + "]";
	}
}
