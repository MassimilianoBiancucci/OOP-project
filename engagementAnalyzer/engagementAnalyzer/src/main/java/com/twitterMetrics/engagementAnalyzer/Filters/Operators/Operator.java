package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import static java.util.Map.entry;

import java.time.LocalDate;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorDoubleValues;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorFiltersValues;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorIntValues;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorLocalDateValues;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Operators.Values.OperatorStringValues;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorValuesException;

public abstract class Operator {
	
	// Enum that encode all the operator symbols
	public enum SymOp {
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
	
	public static final Map<SymOp, String> symbolsMap2String = Map.ofEntries(
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
	
	private SymOp symbol = null;
	
	public Operator(String sym, ) throws Exception {
		
		validateSymbol(sym);
		
	}
	
	// method that verify if the passed symbol is allowed
	private boolean validateSymbol(String sym) throws Exception {
		
		if(!symbolsMap.containsKey(sym)) {
			// if the symbol isn't acceptable
			throw new IncorrectOperatorSymbolException("the symbol passed it's not an operator!");
		}
		
		this.symbol = symbolsMap.get(sym);
		return true;
	}
	
	// method that verify if the values int passed are coherent with the symbol
	private boolean validateValues(OperatorIntValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.in, SymOp.nin, SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		return true;
	}
	
	// method that verify if the values double passed are coherent with the symbol
	private boolean validateValues(OperatorDoubleValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.in, SymOp.nin, SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("");
		}
		
		return true;
	}
		
	// method that verify if the values String passed are coherent with the symbol
	private boolean validateValues(OperatorStringValues values) throws Exception {
		
		
		if()
		
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorLocalDateValues values) throws Exception {
		
		
		if()
		
	}
		
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorOperatorValues values) throws Exception {
		
		
		if()
		
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorFiltersValues values) throws Exception {
		
		
		if()
		
	}
	
	public abstract String toString();
	
	public abstract JsonObject toJson();
	
	// method that return true if the symbol match an operator 
	public static boolean isOperator(String op) {
		return symbolsMap.containsKey(op);
	}
	
	
}
