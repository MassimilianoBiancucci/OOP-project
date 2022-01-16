package com.twitterMetrics.engagementAnalyzer.Parser;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.ConditionalOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.MatchOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;

public class FiltersParser {
	
	private Operator op;
	
	public FiltersParser(JsonObject jsonFilters) throws Exception{
		parse(jsonFilters);
	}
	
	public Operator getOp() {
		return op;
	}
	
	private void parse(JsonObject tree) throws Exception {
		
		// extract the field inside the jsonObject passed, at this point 2 event can happen,
		// there is an logic operator or there is a filter
		for(Map.Entry<String, JsonElement> entry : tree.entrySet()) {
		    //System.out.println("Key = " + entry.getKey() + " Value = " + entry.getValue() );
			
			
			if(Filter.isField(entry.getKey())) {
				// verifico se l'elemento è un field
				// se è un field posso considerare tutti i seguenti elementi come
				// combinazioni logiche di operatori applicati a questo filtro
				
				
			}else if(LogicOperator.isOperator(entry.getKey())) {
				// verifico se l'elemnto è un logic operator
				// se è un operatore logico devo continuare considerare i successivi 
				// il primo deve essere tassativamente un logic operator 
				
				
			}else {
				// se non è nessuno dei precedenti sollevo un eccezione
				throw new Exception("Wrong field in the filter tree");
			}
			
		}
		
	}
	
	// method that check the richt filter formulation
	// the jsonObject passed at this point must be the content of the
	// of the "filters" key inside the Request Body.
	private void checkFiltersTree(JsonObject tree) throws Exception{
		// extract the field inside the jsonObject passed, at this point 2 event can happen,
		// there is an logic operator or there is a filter
		for(Map.Entry<String, JsonElement> entry : tree.entrySet()) {
		    //System.out.println("Key = " + entry.getKey() + " Value = " + entry.getValue() );
			
			
			if(Filter.isField(entry.getKey())) {
				// verifico se l'elemento è un field
				// se è un field posso considerare tutti i seguenti elementi come
				// combinazioni logiche di operatori applicati a questo filtro
				checkFilter(entry.getValue(), Filter.fieldsMap.get(entry.getKey()));
				
			}else if(LogicOperator.isOperator(entry.getKey())) {
				// verifico se l'elemnto è un logic operator
				// se è un operatore logico devo continuare considerare i successivi 
				// il primo deve essere tassativamente un logic operator 
				checkLogicOperator(entry.getKey(), entry.getValue(), null);
				
			}else {
				// se non è nessuno dei precedenti sollevo un eccezione
				throw new Exception("Wrong field in the filter tree");
			}
			
		}
	}
	
	private void checkFilter(JsonElement subTree, Filter.Field filterParent) throws Exception {
		// the filter field must be already checked , at this point the content must be evaluated
		if(subTree.isJsonObject()) {
			// expected a jsonObject as filter content

			int keysCount = 0;
			for(Map.Entry<String, JsonElement> entry : subTree.getAsJsonObject().entrySet()) {
				// the filter content must be an operator otherwise the check will fail
				if(LogicOperator.isOperator(entry.getKey())) {
					// check if the content of the filter is an operator 
					checkLogicOperator(entry.getKey(), entry.getValue(), true);
					
				}else if(Operator.isOperator(entry.getKey())) {
					// if is already checked that the filter content isn't an operator 
					// checking if it is an operator we know that is a Match operator or a Conditional operator 
					checkNonLogicOperator(entry.getKey(), entry.getValue(), true);
					
				}else {
					throw new Exception("the Filter content must be an operator.");
				}
				
				// se all'intenro dell'oggetto c'è piu di una chiave non è un filtro e il controllo è fallito
				if(keysCount++ > 0) throw new Exception("the Filter content must have only one key.");
			}
			// se il controllo arriva a questo punto è riuscito
		}else {
			// if the content filter isn't a jsonObject throw an exceptions
			throw new Exception("the Filter content must be a json object nothing else is accepted.");
		}
	}
	
	private void checkLogicOperator(String op, JsonElement subTree, Filter.Field filterParent) throws Exception {
		
		
		Operator.SymOp symOp = Operator.symbolsMap.get(op);
		
		if( symOp == Operator.SymOp.nop | symOp == Operator.SymOp.not) {
			// if the logical operator are $nop or $not the content of the operator must be a JsonObject
			if(subTree.isJsonObject()) {
				
				// esegue il controllo di corretezza per 
				checkLogicOperatorContent(subTree, filterParent);
				
			}else {
				throw new Exception("the content of the operator " + op + " must be an jsonObject!");
			}
		}else if(symOp == Operator.SymOp.or | symOp == Operator.SymOp.and){
			// if the logical operator are $or or $and the content of the operator must be a JsonArray
			if(subTree.isJsonArray()) {
				
				for(JsonElement je: subTree.getAsJsonArray()) {
					if(je.isJsonObject()) {
						//TODO verificare se è necessario aggiungere il controllo per evitare che vengano passati oggetti di tipo diverso dentro
						// allo stesso operatore logico.
						checkLogicOperatorContent(je, filterParent);
					}else {
						throw new Exception("Inside a logic Operator $or or $and must be passed an jsonArray of JsonObjects.");
					}
				}
				
			}else {
				throw new Exception("the content of the operator " + op + " must be a jsonArray!");
			}
		}else{
			throw new Exception("The method checkLogicOperator() as parameter \"op\" accept only a logical operator.");
		}
	}
	
	// metodo che effettua il controllo di correttezza per il contenuto di un logic Operator
	// metodo di supporto per il metodo checkLogicOperator
	private void checkLogicOperatorContent(JsonElement subTree, Filter.Field filterParent) throws Exception {
		int keysCount = 0;
		for(Map.Entry<String, JsonElement> entry : subTree.getAsJsonObject().entrySet()) {
			
			if(Filter.isField(entry.getKey())){
				if(filterParent  != null) {
					// if the filter is child of another filter
					throw new Exception("A Filter can't be inside another Filter.");
				}else {
					checkFilter(entry.getValue());
				}
				
			}else if(LogicOperator.isOperator(entry.getKey())) {
				checkLogicOperator(entry.getKey(), entry.getValue(), filterParent);
				
			}else if(Operator.isOperator(entry.getKey())){
				if(filterParent != null) {
					checkNonLogicOperator(entry.getKey(), entry.getValue(), filterParent);
				}else {
					throw new Exception("A non-logic Operator must be a Filter's child.");
				}
			}
			
			// se all'intenro dell'oggetto c'è piu di una chiave non è un filtro e il controllo è fallito
			if(keysCount++ > 0) throw new Exception("The Filter content must have only one key.");
		}
	}
	
	private void checkNonLogicOperator(String op, JsonElement subTree, Filter.Field filterParent) throws Exception {
		// TODO verify if this control is always necessary
		if(filterParent == null) throw new Exception("Filter tree malformed! A non logic operator must have one filter parent in its branch.");
		
		Operator.SymOp symOp = Operator.symbolsMap.get(op);
		
		if(ConditionalOperator.conditionalSymbolsMap.containsKey(op)) {
			// se l'operatore passato è un operatore condizionale
			
			if( symOp == Operator.SymOp.gt  | 
				symOp == Operator.SymOp.gte | 
				symOp == Operator.SymOp.lt  | 
				symOp == Operator.SymOp.lte) {
				// these operators need only one (int | date | time) as content
				JsonPrimitive jp = subTree.getAsJsonPrimitive();
				if(jp.isString()) {
					// if the argument is a string it's needed to check if it is a dato or time values
					try {
						DateParser dateParser = new DateParser(jp.getAsString());
						
						if(dateParser.isTimeBool()) {
							// the argument is a timeSlot value
							
						}else {
							// the argument is a date value
							
						}
						
					}catch(DateTimeParseException e) {
						// if an exception raise it's a general string, so another exception should be r
						throw new Exception("Conditional operators dosn't accept normal string as argument, only the specified Date and Time formats are accepted as strings.");
					}
					
				}else if(jp.isNumber()) {
					
				}
			}else if(symOp == Operator.SymOp.bt) {
				// these operators need an array of (int | date | time) as content
				
			}
		}else if(MatchOperator.matchSymbolsMap.containsKey(op)) {
			// se l'operatore passato è un match operator 
			
		}else {
			// se la stringa passata coe op non corrisponde a nessuno di valori DateTimeParseException
			throw new Exception("The method checkNonLogicOperator() as parameter \"op\" accept only a Match operator symbol ora Conditional operator symbol.");
		}
	}
	
	private void checkValues(JsonElement subTree, Filter.Field filterParent) throws Exception {
		// TODO verify if this control is always necessary
		if(filterParent == null) throw new Exception("Filter tree malformed! A value must have one filter parent in its branch.");
		
	}
	
}