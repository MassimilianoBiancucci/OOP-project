package com.twitterMetrics.engagementAnalyzer.Filters;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectFilterFieldException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class MetricFilter extends Filter{
	
	/**
	 * Map that relate string MetricFilter fields symbols to the equivalent in enum format
	 */
	public static final Map<String, Field> metricMap = Map.ofEntries(
		entry("retweet_count", 		Field.retweet),
	    entry("reply_count", 		Field.reply),
	    entry("like_count", 		Field.like),
	    entry("quote_count", 		Field.quote),
	    entry("engagement", 		Field.engagement)
	);
	
	/**
	 * MetricFilter constructor
	 * 
	 * @param field string that encode the field that will be filtered with this object
	 * @param op the operator that will be used to filter the given field
	 * @throws Exception generic exceptions raised if the field is not accepted or the Operator and the passed field aren't compatible.
	 */
	public MetricFilter(String field, Operator op) throws Exception {
		
		super(field, op);
	}
	
	
	/**
	 * MetricFilter second constructor
	 * 
	 * @param field as Field enum that encode the field that will be filtered with this object
	 * @param op the operator that will be used to filter the given field
	 * @throws Exception generic exceptions raised if the field is not accepted or the Operator and the passed field aren't compatible.
	 */
	public MetricFilter(Field field, Operator op) throws Exception {
		this(field2String.get(field), op);
	}
	
	@Override
	protected boolean validateField(String field) throws Exception {
		if(!metricMap.containsKey(field)) {
			// if the symbol isn't acceptable
			throw new IncorrectFilterFieldException("the field passed to the message filter isn't accepted!");
		}
		
		this.field = fieldsMap.get(field);
		return true;
	}
	
	@Override
	public String toString() {
		return "MetricFilter [field=" + field + ", op=" + op + "]";
	}
	
	/**
	 * Method that return the specific filter in JsonObject format.
	 */
	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Static method that return true if the symbol match a suported field from this filter class.
	 * 
	 * @param field string tested to match if it is a supported field.
	 * @return true if the field is supported.
	 */ 
	public static boolean isField(String field) {
		return metricMap.containsKey(field);
	}
	
	
	/**
	 * Static method that return true if the symbol match a suported field from this filter class.
	 * 
	 * @param field Field enum tested to match if it is a supported field.
	 * @return true if the field is supported.
	 */
	public static boolean isField(Field field) {
		return metricMap.containsValue(field);
	}
	
	/**
	 * Method that will apply the filter to the passed TweetList
	 * 
	 * @param tweetList the list with the tweets that will be filtered with this filters and the loaded operator
	 * @return return a tweetList with only the filtered tweets.
	 * @throws Exception generic exception raised if there is some incompatibility with the Operator or the contained OperatorValues.
	 */
	@Override
	public TweetList applayFilters(TweetList tweetList) throws Exception {
		return op.applayFilters(tweetList, field);
	}
}
