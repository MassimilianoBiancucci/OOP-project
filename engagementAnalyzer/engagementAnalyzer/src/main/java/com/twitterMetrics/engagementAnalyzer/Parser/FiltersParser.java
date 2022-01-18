package com.twitterMetrics.engagementAnalyzer.Parser;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.twitterMetrics.engagementAnalyzer.Filters.DateFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.MessageFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.MetricFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.TimeFilter;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.ConditionalOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.MatchOperator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Operator;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorDateValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorFilterValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorIntValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorLogicOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorOperatorValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorStringValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorTimeValues;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.Values.OperatorValues;
import com.twitterMetrics.engagementAnalyzer.supportTypes.DateValue;
import com.twitterMetrics.engagementAnalyzer.supportTypes.TimeValue;

public class FiltersParser {
	
	private LogicOperator op;
	
	public FiltersParser(JsonObject jsonFilters) throws Exception{
		op = parse(jsonFilters);
	}
	
	public LogicOperator getOp() {
		return op;
	}
	
	// method that check the richt filter formulation
	// the jsonObject passed at this point must be the content of the
	// of the "filters" key inside the Request Body.
	private LogicOperator parse(JsonObject tree) throws Exception{
		// extract the field inside the jsonObject passed, at this point 2 event can happen,
		// there is an logic operator or there is a filter
		if(tree.size() > 1) throw new Exception("Only one key is allowed in the first level of the filter tree");
		for(Map.Entry<String, JsonElement> entry : tree.entrySet()) {
		    //System.out.println("Key = " + entry.getKey() + " Value = " + entry.getValue() );
			
			if(Filter.isField(entry.getKey())) {
				// verifico se l'elemento è un field
				// se è un field posso considerare tutti i seguenti elementi come
				// combinazioni logiche di operatori applicati a questo filtro
				Filter filter = checkFilter(entry.getValue(), Filter.fieldsMap.get(entry.getKey()));
				return new LogicOperator(Operator.SymOp.nop, new OperatorFilterValues(filter));

				
			}else if(LogicOperator.isOperator(entry.getKey())) {
				// verifico se l'elemnto è un logic operator
				// se è un operatore logico devo continuare considerare i successivi 
				// il primo deve essere tassativamente un logic operator 
				return checkLogicOperator(entry.getKey(), entry.getValue());
				
			}else {
				// se non è nessuno dei precedenti sollevo un eccezione
				throw new Exception("Wrong field in the filter tree");
			}
		}
		
		// the code should not reach this point
		throw new Exception("[Error] Unexpected parse behaviour!!");
	}
	
	private Filter checkFilter(JsonElement subTree, Filter.Field filterParent) throws Exception {
		// the filter field must be already checked , at this point the content must be evaluated
		if(subTree.isJsonObject()) {
			// expected a jsonObject as filter content
			
			Filter filter = null;
			JsonObject jo = subTree.getAsJsonObject();
			
			// se all'intenro dell'oggetto c'è piu di una chiave non è un filtro e il controllo è fallito
			if(jo.size() > 1) throw new Exception("the Filter content must have only one key.");
			
			for(Map.Entry<String, JsonElement> entry : subTree.getAsJsonObject().entrySet()) {
				// the filter content must be a non-logic operator otherwise the check will fail
				if(MatchOperator.isOperator(entry.getKey()) | ConditionalOperator.isOperator(entry.getKey())) {
					// checking if it is a Match operator or a Conditional operator 
					filter = checkNonLogicOperator(entry.getKey(), entry.getValue(), filterParent);
					
				}else {
					throw new Exception("The Filter's content must be a non-logic Operator.");
				}
				
			}
			
			//qui posso ritornare il filtro caricato
			return filter;
			
		}else {
			// if the content filter isn't a jsonObject throw an exceptions
			throw new Exception("the Filter content must be a json object nothing else is accepted.");
		}
	}
	
	private LogicOperator checkLogicOperator(String op, JsonElement subTree) throws Exception {
		
		Operator.SymOp symOp = Operator.symbolsMap.get(op);
		
		if( symOp == Operator.SymOp.nop | symOp == Operator.SymOp.not) {
			// if the logical operator are $nop or $not the content of the operator must be a JsonObject
			if(subTree.isJsonObject()) {
				
				// esegue il controllo di corretezza per 
				return checkLogicOperatorContent(subTree);
				
			}else {
				throw new Exception("the content of the operator " + op + " must be an jsonObject!");
			}
		}else if(symOp == Operator.SymOp.or | symOp == Operator.SymOp.and){
			// if the logical operator are $or or $and the content of the operator must be a JsonArray
			if(subTree.isJsonArray()) {
				
				int idx = 0;
				JsonArray ja = subTree.getAsJsonArray();
				LogicOperator opls[] = new LogicOperator[ja.size()];
				
				for(JsonElement je: ja) {
					if(je.isJsonObject()) {
						// Inside a logic operator can be passed filter and logic operators together
						// but at compilatio time filters will be wrapped inside nop operators.
						opls[idx++] = checkLogicOperatorContent(je);
					}else {
						throw new Exception("Inside a logic Operator $or or $and must be passed an jsonArray of JsonObjects.");
					}
				}
				
				OperatorLogicOperatorValues opOplValues = new OperatorLogicOperatorValues(opls);
				return new LogicOperator(symOp, opOplValues);
				
			}else {
				throw new Exception("the content of the operator " + op + " must be a jsonArray!");
			}
		}else{
			throw new Exception("The method checkLogicOperator() as parameter \"op\" accept only a logical operator.");
		}
	}
	
	// metodo che effettua il controllo di correttezza per il contenuto di un logic Operator
	// metodo di supporto per il metodo checkLogicOperator
	private LogicOperator checkLogicOperatorContent(JsonElement subTree) throws Exception {
		
		JsonObject jo = subTree.getAsJsonObject();
		
		// se all'intenro dell'oggetto c'è piu di una chiave non è un filtro e il controllo è fallito
		if(jo.size() > 1) throw new Exception("The Filter content must have only one key.");
		
		for(Map.Entry<String, JsonElement> entry : jo.entrySet()) {
			
			if(Filter.isField(entry.getKey())){
				// if the logic operator has inside a Filter
				Filter filter = checkFilter(entry.getValue(), Filter.fieldsMap.get(entry.getKey()));
				return new LogicOperator(Operator.SymOp.nop, new OperatorFilterValues(filter));
				
			}else if(LogicOperator.isOperator(entry.getKey())) {
				// if the logic operator has inside a another logic operator
				return checkLogicOperator(entry.getKey(), entry.getValue());
				
			}else if(Operator.isOperator(entry.getKey())){
				throw new Exception("A non-logic Operator can't be passed inside a Logic Operator.");
			}
		}
		
		// the code should not reach this point
		throw new Exception("[Error] Unexpected parse behaviour!!");
	}
	
	private Filter checkNonLogicOperator(String op, JsonElement subTree, Filter.Field filterParent) throws Exception {
		if(filterParent == null) throw new Exception("Filter tree malformed! A non logic operator must have one filter parent in its branch.");
		
		if(ConditionalOperator.conditionalSymbolsMap.containsKey(op)) {
			// se l'operatore passato è un operatore condizionale
			
			ConditionalOperator condOp = null;
			Operator.SymOp symOp = Operator.symbolsMap.get(op);
			
			if( symOp == Operator.SymOp.gt  | 
				symOp == Operator.SymOp.gte | 
				symOp == Operator.SymOp.lt  | 
				symOp == Operator.SymOp.lte) {
				// these operators accept only one (int | date | time) as content
				
				Class elemClass = getElementClass(subTree);
				
				if(elemClass == int.class) {
					// if the agument has int type 
					// qui ho il filtro, l'operatore e i valori.
					// devo risolvere la procedura dicreazione dell'oggetto filtro
					
					OperatorIntValues values = new OperatorIntValues(subTree);
					condOp = new ConditionalOperator(op, values);
					
				}else if(elemClass == DateValue.class) {
					// if the agument has Date type 
					
					OperatorDateValues values = new OperatorDateValues(subTree);
					condOp = new ConditionalOperator(op, values);
					
				}else if(elemClass == TimeValue.class) {
					// if the agument has Time type 
					
					OperatorTimeValues values = new OperatorTimeValues(subTree);
					condOp = new ConditionalOperator(op, values);
					
				}else{
					// if the agument hasn't a supported type 
					throw new Exception("Conditional operator " + Operator.symbol2String.get(symOp) + " only allow int, Date or Time types arguments.");
				}

			}else if(symOp == Operator.SymOp.bt) {
				// these operators need an array of (int | date | time) as content
				if(subTree.isJsonArray()) {
					
					JsonArray ja = subTree.getAsJsonArray();
					
					// check if all the values inside are the same types
					if(checkJsonArrayValuesTypesHomogeneity(ja)){
						// check the types of the values inside the array
						
						JsonElement firstElem = getFirstElement(subTree.getAsJsonArray());
						Class firstElemClass = getElementClass(firstElem);
						
						if(firstElemClass == int.class) {
							// here should start the came back to the start
							
							OperatorIntValues values = new OperatorIntValues(subTree);
							condOp = new ConditionalOperator(op, values);
							
						}else if(firstElemClass == DateValue.class) {
							// here should start the came back to the start
							
							OperatorDateValues values = new OperatorDateValues(subTree);
							condOp = new ConditionalOperator(op, values);
							
						}else if(firstElemClass == TimeValue.class) {
							// here should start the came back to the start
							
							OperatorTimeValues values = new OperatorTimeValues(subTree);
							condOp = new ConditionalOperator(op, values);
							
						}else {
							throw new Exception("The Conditional operator $bt only allow array of 2 int | date | time values as content");
						}
					}else {
						throw new Exception("The Conditional operator $bt only allow array of 2 int | date | time values as content with same type");
					}
				}else {
					throw new Exception("The Conditional operator $bt only allow array of 2 int | date | time values as content");
				}
			}
			
			// if the code reach this point the condOp var is loaded with the operator 
			//correctly initialized with its operator, here the filter is created
			if(MetricFilter.isField(filterParent)) {
				MetricFilter filter = new MetricFilter(filterParent, condOp);
				return filter;
				
			}else if(MessageFilter.isField(filterParent)) {
				MessageFilter filter = new MessageFilter(filterParent, condOp);
				return filter;
				
			}else if(DateFilter.isField(filterParent)) {
				DateFilter filter = new DateFilter(filterParent, condOp);
				return filter;
				
			}else if(TimeFilter.isField(filterParent)) {
				TimeFilter filter = new TimeFilter(filterParent, condOp);
				return filter;
				
			}else {
				throw new Exception("[Error] Unexpected parse behaviour!!");
			}
			
		}else if(MatchOperator.matchSymbolsMap.containsKey(op)) {
			
			MatchOperator macthOp;
			
			// se l'operatore passato è un match operator 
			if(subTree.isJsonArray()) {
				// if the content of the match operator is a jsonArray 
				JsonArray ja = subTree.getAsJsonArray();
				
				// verify that all the elements inside the match operator has the same type
				if(!checkJsonArrayValuesTypesHomogeneity(ja)) throw new Exception("MatchOperator don't allow parameters of different types together.");
				
				// check the type of the values inside, if the homogenity control is passed it's only need
				// the check of the class of the first element of the array
				JsonElement firstElem = getFirstElement(ja);
				Class firstElemClass = getElementClass(firstElem);
				
				// the only type allowed inside the Match operator is String 
				// note that getElementClass understand the difference between a normal string 
				// and a string that contain a date or a time format.
				if(firstElemClass == String.class) {
					// if the type is string check if compatible with the filter
					// here should start the came back to the start
					
					OperatorStringValues values = new OperatorStringValues(subTree);
					macthOp = new MatchOperator(op, values);
					
				}else {
					// if the type isn't string it's not supported
					throw new Exception("MatchOperator only allow String types arguments.");
				}
				
			}else {
				throw new Exception("Match operators only accept jsonArray as content.");
			}
			
			// if the code reach this point the condOp var is loaded with the operator 
			//correctly initialized with its operator, here the filter is created
			if(MetricFilter.isField(filterParent)) {
				MetricFilter filter = new MetricFilter(filterParent, macthOp);
				return filter;
			}else if(MessageFilter.isField(filterParent)) {
				MessageFilter filter = new MessageFilter(filterParent, macthOp);
				return filter;
			}else if(DateFilter.isField(filterParent)) {
				DateFilter filter = new DateFilter(filterParent, macthOp);
				return filter;
			}else if(TimeFilter.isField(filterParent)) {
				TimeFilter filter = new TimeFilter(filterParent, macthOp);
				return filter;
			}else {
				throw new Exception("[Error] Unexpected parse behaviour!!");
			}
			
		}else {
			// se la stringa passata coe op non corrisponde a nessuno di valori DateTimeParseException
			throw new Exception("The method checkNonLogicOperator() as parameter \"op\" accept only a Match operator symbol ora Conditional operator symbol.");
		}
	}
	
	// easy method for getting the first element inside the a jsonArray
	private JsonElement getFirstElement(JsonArray ja) throws Exception {
		for(JsonElement je: ja) return je;
		throw new Exception("Empty JsonArray in the filter are not allowed.");
	}
	
	// method that return the type of a json element
	public static Class getElementClass(JsonElement je) {
		
	    if (!je.isJsonPrimitive()) {
	        return je.getClass();
	    }
	    
	    final JsonPrimitive primitive = je.getAsJsonPrimitive();
	    
	    if (primitive.isString()) {
	    	
	    	try {
				DateParser dateParser = new DateParser(primitive.getAsString());
				if(dateParser.isTimeBool()) {
					// the primitive is a timeSlot value
					return TimeValue.class;
				}else {
					// the primitive is a date value
					return DateValue.class;
				}
			}catch(DateTimeParseException e) {
				// if an exception raise it's a general string, so another exception should be r
				return String.class;
			}
	    	
	    } else if (primitive.isNumber()) {
	    	// NOTE for this implementation it's not important to know
	    	// if numbers are int or float, in any case they will be truncated.
	        //String numStr = primitive.getAsString();
	        //if (numStr.contains(".") || numStr.contains("E")) {
	        //    return Double.class;
	        //}
	        //return Long.class;
	        return int.class;
	    	
	    } else if (primitive.isBoolean()) {
	        return Boolean.class;
	        
	    } else {
	        return je.getClass();
	    }
	}
	
	// Method that check if all the elements of a jsonArray have the same classes
	private boolean checkJsonArrayValuesTypesHomogeneity(JsonArray ja) throws Exception {
		
		JsonElement firstElem = getFirstElement(ja);
		Class firstElemClass = getElementClass(firstElem);
		
		for(JsonElement je: ja) {
			if(firstElemClass != getElementClass(je)) return false; 
		}
		
		return true;
	}
	
}