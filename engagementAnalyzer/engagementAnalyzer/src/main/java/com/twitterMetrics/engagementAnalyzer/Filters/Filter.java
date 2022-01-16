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


public abstract class Filter {
	
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
	
	public Filter(String field, Operator op) throws Exception {
		
		if(validateField(field)) {
			if(op instanceof LogicOperator) {
				validateOperator((LogicOperator) op);
			}else if(op instanceof MatchOperator) {
				validateOperator((MatchOperator) op);
			}else if(op instanceof ConditionalOperator) {
				validateOperator((ConditionalOperator) op);
			}
		}
		
	}
	
	// method that verify if the passed symbol is allowed
	// if allowed load the field inside, this function change for each type of Filter
	protected abstract boolean validateField(String field) throws Exception;
	
	
 	// method that verify if the operator passed is coherent with the field
	private boolean validateOperator(LogicOperator op) throws Exception {
		ArrayList<Field> allowedFields = new ArrayList<>(Arrays.asList(Field.retweet, Field.reply, Field.like, Field.quote, Field.engagement, Field.text, Field.date, Field.time));
		if(!allowedFields.contains(this.field)) {
			throw new IncorrectOperatorSymbolException("");
		}
		
		this.op = op;
		return true;
	}
	
	// method that verify if the operator passed is coherent with the field
	private boolean validateOperator(MatchOperator op) throws Exception {
		ArrayList<Field> allowedFields = new ArrayList<>(Arrays.asList(Field.retweet, Field.reply, Field.like, Field.quote, Field.engagement, Field.text, Field.date, Field.time));
		if(!allowedFields.contains(this.field)) {
			throw new IncorrectOperatorSymbolException("");
		}
		
		this.op = op;
		return true;
	}
	
	// method that verify if the operator passed is coherent with the field
	private boolean validateOperator(ConditionalOperator op) throws Exception {
		ArrayList<Field> allowedFields = new ArrayList<>(Arrays.asList(Field.retweet, Field.reply, Field.like, Field.quote, Field.engagement, Field.date, Field.time));
		if(!allowedFields.contains(this.field)) {
			throw new IncorrectOperatorSymbolException("");
		}
		
		this.op = op;
		return true;
	}

	
	public abstract String toString();
	
	public abstract JsonObject toJson();
	
	// method that return true if the symbol match an operator 
	public static boolean isField(String op) {
		return fieldsMap.containsKey(op);
	}
}
