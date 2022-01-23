package com.twitterMetrics.engagementAnalyzer.Parser;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Exceptions.RequestBodyFieldNotPresentException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.UnexpectedRequestBodyFieldException;
import com.twitterMetrics.engagementAnalyzer.Exceptions.UnexpectedRequestBodyFieldValueException;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;

public class RequestParser {
	
	private JsonObject request;
	
	private String bearerToken = "";
	private String[] tweetIds;
	private LogicOperator filters;
	
	
	/**
	 * RequestParser Contructor
	 * 
	 * @param jsonRequest API request in JsonObject format.
	 * @throws Exception an Exception will be raised if there is some error in the request format.
	 */
	public RequestParser(JsonObject jsonRequest) throws 
		Exception {
		
		// initialize the filter with an empty No operation logical operator
		this.filters = new LogicOperator("$nop", null);
		this.request = jsonRequest;
		parseRequest();
	}
	
	
	// Method for parsing of requests for raw tweets by a list of tweets ids
	// fields: TwitterBearerToken, tweetIds, filters
	private void parseRequest() throws Exception {
		
		for(Map.Entry<String, JsonElement> entry : this.request.entrySet()) {
			
		    //System.out.println("Key = " + entry.getKey() + " Value = " + entry.getValue() );
		    
		    switch(entry.getKey()) {
		    	
		    	// case that parse the bearer token
			    case "TwitterBearerToken":
			    	try {
			    		this.bearerToken = entry.getValue().getAsString();
			    		
			    	}catch(Exception e) {
			    		e.printStackTrace();
			    		throw new UnexpectedRequestBodyFieldValueException("Unexpected value inside the field TwitterBearerToken, unexpected value: " + entry.getValue());
			    	}
			    	break;
			    // case that parse the array of tweets
		    	case "tweetIds":
		    		try {
			    		JsonArray tweetIdsArray = entry.getValue().getAsJsonArray();
			    		this.tweetIds = new String[tweetIdsArray.size()];
			    		int idx = 0;
			    		
			    		for(JsonElement elem: tweetIdsArray) {
			    			if(!elem.getAsJsonPrimitive().isNumber()) throw new Exception();
			    			this.tweetIds[idx++] = elem.getAsString();
			    		}
			    			
			    	}catch(Exception e) {
			    		throw new UnexpectedRequestBodyFieldValueException("Unexpected value inside the field tweetIds, unexpected value: " + entry.getValue());
			    	}
			    	break;
			    // case that parse the filters field
		    	case "filters":
		    		try {
		    			this.filters = new FiltersParser(entry.getValue().getAsJsonObject()).getOp();
		    		}catch(Exception e) {
		    			if(e.getMessage() != null) {
		    				throw e;
		    			}else {
		    				throw new UnexpectedRequestBodyFieldValueException("Unexpected value inside the field filters, unexpected value: " + entry.getValue());
		    			}
		    		}
			    	break;
			    default:
			    	throw new UnexpectedRequestBodyFieldException("Unexpected field passed in the json request body, unexpected field: " + entry.getKey());
		    }
		}
	}
	
	
	/**
	 * Method for the Bearer token request
	 * 
	 * @return The Bearer token in String format
	 * @throws RequestBodyFieldNotPresentException bearerToken is a mandatory field, if not present inside the class when requested throw an RequestBodyFieldNotPresentException exception
	 */
	public String getBearerToken() throws RequestBodyFieldNotPresentException{
		// TODO implement special behavior
		return bearerToken;
	}
	
	
	/**
	 * Setter method for the bearerToken for the twitter api
	 * 
	 * @param bearerToken The bearer token for the twitter api in String format
	 */
	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}
	
	
	/**
	 * Getter method for the parsed list of tweets ids.
	 * 
	 * @return the parsed list of tweets ids in String array format.
	 * @throws RequestBodyFieldNotPresentException tweetsIds is a mandatory field, if not present inside the class when requested throw an exception.
	 */
	public String[] getTweetsIds() throws RequestBodyFieldNotPresentException{
		// TODO implement special behavior
		return tweetIds;
	}
	
	
	/**
	 * Getter method for the parsed filters.
	 * filters aren't a mandatory field, if not present return an empty $nop (no operation operator),
	 * if there are filters in the request return always a $nop operator, but with at least one filter inside.
	 * 
	 * @return a logic operator $nop with if present a subtree of operator and filters.
	 */
	public LogicOperator getFilters() {
		return filters;
	}
}
