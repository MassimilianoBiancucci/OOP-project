package com.twitterMetrics.engagementAnalyzer.Filters.Operators;

import static java.util.Map.entry;

import java.util.Map;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.IncorrectOperatorSymbolException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;

public class MatchOperator extends Operator{
	
	public static final Map<String, SymOp> matchSymbolsMap = Map.ofEntries(
	    entry("$in", SymOp.in),
	    entry("$nin", SymOp.nin)
	);
	
	public MatchOperator(String sym, OperatorValues values) throws Exception {
		
		// the super class check the global 
		// validity of each symbol and values
		super(sym, values);
		
	}
	
	// Definition of the validation function for the symbol
	protected boolean validateSymbol(String sym) throws Exception{
		
		if(!matchSymbolsMap.containsKey(sym)) {
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
		return matchSymbolsMap.containsKey(op);
	}
}
