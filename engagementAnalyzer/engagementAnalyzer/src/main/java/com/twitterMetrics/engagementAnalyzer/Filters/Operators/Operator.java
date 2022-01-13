package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import static java.util.Map.entry;

import java.time.LocalDate;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorFilterValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorIntValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorDateValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorStringValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
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
				
			}else if(values instanceof OperatorFilterValues) {
				validateValues((OperatorFilterValues) values);
				
			}
		}
		
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
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values String passed are coherent with the symbol
	private boolean validateValues(OperatorStringValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.in, SymOp.nin, SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorDateValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.in, SymOp.nin, SymOp.gt, SymOp.gte, SymOp.lt, SymOp.lte, SymOp.bt));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the values LocalDate passed are coherent with the symbol
	private boolean validateValues(OperatorOperatorValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.or, SymOp.and, SymOp.not));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed " + values.toString() + " \n to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	// method that verify if the filters passed are coherent with the symbol
	private boolean validateValues(OperatorFilterValues values) throws Exception {
		ArrayList<SymOp> allowedSymbols = new ArrayList<>(Arrays.asList(SymOp.or, SymOp.and, SymOp.not));
		if(!allowedSymbols.contains(this.symbol)) {
			throw new IncorrectOperatorValuesException("the values passed: " + values.toString() + " \n to the operator " + symbolsMap2String.get(this.symbol) + "dosen't match the allowed types!");
		}
		
		this.operatorValues = values;
		return true;
	}
	
	public abstract String toString();
	
	public abstract JsonObject toJson();
	
	// method that return true if the symbol match an operator 
	public static boolean isOperator(String op) {
		return symbolsMap.containsKey(op);
	}
	
}
