package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import static java.util.Map.entry;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorFilterValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorIntValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorLogicOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorDateValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorStringValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;


public abstract class Operator {
	
	// Enum that encode all the operator symbols
	public enum SymOp {
		nop,
		not,
		or,
		and,
		in,
		nin,
		gt,
		gte,
		lt,
		lte,
		bt
	}
	
	public static final Map<String, SymOp> symbolsMap = Map.ofEntries(
		entry("$nop", SymOp.nop),
	    entry("$not", SymOp.not),
	    entry("$or", SymOp.or),
	    entry("$and", SymOp.and),
	    entry("$in", SymOp.in),
	    entry("$nin", SymOp.nin),
	    entry("$gt", SymOp.gt),
	    entry("$gte", SymOp.gte),
	    entry("$lt", SymOp.lt),
	    entry("$lte", SymOp.lte),
	    entry("$bt", SymOp.bt)
	);
	
	public static final Map<SymOp, String> symbol2String = Map.ofEntries(
		entry(SymOp.nop, "$nop"),
		entry(SymOp.not, "$not"),
		entry(SymOp.or , "$or" ),
		entry(SymOp.and, "$and"),
		entry(SymOp.in , "$in" ),
		entry(SymOp.nin, "$nin"),
		entry(SymOp.gt , "$gt" ),
		entry(SymOp.gte, "$gte"),
		entry(SymOp.lt , "$lt" ),
		entry(SymOp.lte, "$lte"),
		entry(SymOp.bt , "$bt" )
	);
	
	protected SymOp symbol = null;
	protected OperatorValues operatorValues = null;
	
	public Operator(String sym, OperatorValues values) throws Exception {
		
		// if the symbol is valid check the validity of the operator values
		if(validateSymbol(sym)) {
			
			if(values instanceof OperatorIntValues) {
				validateValues((OperatorIntValues) values);
				
			}else if(values instanceof OperatorStringValues) {
				validateValues((OperatorStringValues) values);
				
			}else if(values instanceof OperatorDateValues) {
				validateValues((OperatorDateValues) values);
				
			}else if(values instanceof OperatorOperatorValues) {
				validateValues((OperatorOperatorValues) values);
				
			}else if(values instanceof OperatorLogicOperatorValues) {
				validateValues((OperatorLogicOperatorValues) values);
				
			}else if(values instanceof OperatorFilterValues) {
				validateValues((OperatorFilterValues) values);
				
			}else if(values == null) {
				// if the passed values is null check if the symbol is $nop else throw an exception
				if(this.symbol != SymOp.nop)
					throw new IncorrectOperatorValuesException(
						"the value null passed to the operator " + 
						symbol2String.get(this.symbol) + "dosen't match the allowed types!\n" + 
						"only the $nop operator accept it!");
			}
		}
		
	}
	
	
	
	// method that verify if the passed symbol is allowed
	// if allowed load the symbol inside, this function change for each type of operator
	protected abstract boolean validateSymbol(String sym) throws Exception;
	
	// method that verify if the values int passed are coherent with the symbol
	private boolean validateValues(OperatorIntValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values String passed are coherent with the symbol
	private boolean validateValues(OperatorStringValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.in, SymOp.nin));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorDateValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorOperatorValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.or, SymOp.and, SymOp.not, SymOp.nop));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values LogicOperator passed are coherent with the symbol
	private boolean validateValues(OperatorLogicOperatorValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.or, SymOp.and, SymOp.not, SymOp.nop));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the filters passed are coherent with the symbol
	private boolean validateValues(OperatorFilterValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.or, SymOp.and, SymOp.not, SymOp.nop));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed: " + values.toString() + " \n to the operator " + symbol2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// This method is implemented only inside MatchOperators and on conditional Operators 
	// on logic Operator this method will throw an exception.
	// this method effectivly launche the filtering of tweets based on field, operator and the operator values.
	public abstract TweetList applayFilters(TweetList tweetList, Field field) throws Exception;
	
	public abstract String toString();
	
	public abstract JsonObject toJson();
	
	// method that return true if the symbol match an operator 
	public static boolean isOperator(String op) {
		return symbolsMap.containsKey(op);
	}
	
}
