package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator.SymOp;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;

public class ConditionalOperator extends Operator{

	public static final Map<String, SymOp> conditionalSymbolsMap = Map.ofEntries(
	    entry("$gt", SymOp.gt),
	    entry("$gte", SymOp.gte),
	    entry("$lt", SymOp.lt),
	    entry("$lte", SymOp.lte),
	    entry("$bt", SymOp.bt)
	);
	
	public ConditionalOperator(String sym, OperatorValues values) throws Exception {
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
	}
	
	public ConditionalOperator(SymOp sym, OperatorValues values) throws Exception {
		this(symbol2String.get(sym), values);
	}
	
	// Definition of the validation function for the symbol
	protected boolean validateSymbol(String sym) throws Exception{
		
		if(!conditionalSymbolsMap.containsKey(sym)) {
			// if the symbol isn't acceptable
			throw new IncorrectOperatorSymbolException("the symbol passed it's not a logical operator!");
		}
		
		this.symbol = symbolsMap.get(sym);
		return true;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JsonObject toJson() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// method that return true if the symbol match a Conditional operator 
	public static boolean isOperator(String op) {
		return conditionalSymbolsMap.containsKey(op);
	}
}
