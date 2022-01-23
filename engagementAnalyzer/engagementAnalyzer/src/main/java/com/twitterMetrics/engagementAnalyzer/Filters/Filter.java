package com.twitterMetrics.engagementAnalyzer.Filters;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.ConditionalOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.MatchOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;


/**
 * @author Maxxy
 *
 */
public abstract class Filter {
	
	/**
	 * Enum that encode all the Filter fields symbols
	 */
	public enum Field {
		retweet,
		reply,
		like,
		quote,
		engagement,
		text,
		date,
		time,
	}
	
	
	/**
	 * Map that relate string Filter fields symbols to the equivalent in enum format
	 */
	public static final Map<String, Field> fieldsMap = Map.ofEntries(
		entry("retweet_count", 		Field.retweet),
	    entry("reply_count", 		Field.reply),
	    entry("like_count", 		Field.like),
	    entry("quote_count", 		Field.quote),
	    entry("engagement", 		Field.engagement),
	    entry("text", 				Field.text),
	    entry("created_at", 		Field.date),
	    entry("created_in_timeslot", Field.time)
	);
	
	
	/**
	 * Map that relate enum Filter fields symbols to the equivalent in string format
	 */
	public static final Map<Field, String> field2String = Map.ofEntries(
		entry(Field.retweet, 	"retweet_count"),
		entry(Field.reply, 		"reply_count"),
		entry(Field.like, 		"like_count" ),
		entry(Field.quote, 		"quote_count"),
		entry(Field.engagement, "engagement" ),
		entry(Field.text, 		"text"),
		entry(Field.date, 		"created_at" ),
		entry(Field.time, 		"created_in_timeslot")
	);
	
	protected Field field = null;
	protected Operator op = null;
	
	/**
	 * Filter abstract class constructor
	 * 
	 * @param field string that encode the field that will be filtered with this object
	 * @param op the operator that will be used to filter the given field
	 * @throws Exception generic exceptions raised if the field is not accepted or the Operator and the passed field aren't compatible.
	 */
	public Filter(String field, Operator op) throws Exception {
		
		if(validateField(field)) {
			if(op instanceof LogicOperator) {
				validateOperator((LogicOperator) op);
			}else if(op instanceof MatchOperator) {
				validateOperator((MatchOperator) op);
			}else if(op instanceof ConditionalOperator) {
				validateOperator((ConditionalOperator) op);
			}else {
				throw new Exception("Operator passed to a filter must be initialized!");
			}
		}
	}
	
	/**
	 * Filter abstract class second constructor
	 * 
	 * @param field as Field enum that encode the field that will be filtered with this object
	 * @param op the operator that will be used to filter the given field
	 * @throws Exception generic exceptions raised if the field is not correct or the Operator and the passed field aren't compatible.
	 */
	public Filter(Field field, Operator op) throws Exception {
		this(field2String.get(field), op);
	}
	
	// method that verify if the passed symbol is allowed
	// if allowed load the field inside, this function change for each type of Filter
	protected abstract boolean validateField(String field) throws Exception;
	
	
 	// method that verify if the operator passed is coherent with the field
	// NOTE the Logic operator can't be inside any Filter, so this method every time raise an exception.
	private boolean validateOperator(LogicOperator op) throws Exception {
		throw new IncorrectOperatorSymbolException("Logic Operators aren't accepted inside a filter.");
	}
	
	// method that verify if the operator passed is coherent with the field
	private boolean validateOperator(MatchOperator op) throws Exception {
		ArrayList<Field> allowedFields = new ArrayList<>(Arrays.asList(Field.text));
		if(!allowedFields.contains(this.field)) {
			throw new IncorrectOperatorSymbolException("Match Operators are accepted only inside a text Filter.");
		}
		
		this.op = op;
		return true;
	}
	
	// method that verify if the operator passed is coherent with the field
	private boolean validateOperator(ConditionalOperator op) throws Exception {
		ArrayList<Field> allowedFields = new ArrayList<>(Arrays.asList(Field.retweet, Field.reply, Field.like, Field.quote, Field.engagement, Field.date, Field.time));
		if(!allowedFields.contains(this.field)) {
			throw new IncorrectOperatorSymbolException("Conditional Operators aren't accepted only inside a text Filter.");
		}
		
		this.op = op;
		return true;
	}

	
	/**
	 * Method that will apply the filter to the passed TweetList
	 * 
	 * @param tweetList the list with the tweets that will be filtered with this filters and the loaded operator
	 * @return return a tweetList with only the filtered tweets.
	 * @throws Exception generic exception raised if there is some incompatibility with the Operator or the contained OperatorValues.
	 */
	public abstract TweetList applayFilters(TweetList tweetList) throws Exception;
	
	
	/**
	 * Method that return the specific filter in string format.
	 */
	public abstract String toString();
	
	
	/**
	 * Method that return the specific filter in JsonObject format.
	 */
	public abstract JsonObject toJson();
	

	/**
	 * Static method that return true if the symbol match a suported field from this filter class.
	 * 
	 * @param field string tested to match if it is a supported field.
	 * @return true if the field is supported.
	 */
	public static boolean isField(String field) {
		return fieldsMap.containsKey(field);
	}
	
	
	/**
	 * Static method that return true if the symbol match a suported field from this filter class.
	 * 
	 * @param field Field enum tested to match if it is a supported field.
	 * @return true if the field is supported.
	 */
	public static boolean isField(Field field) {
		return fieldsMap.containsValue(field);
	}
}
