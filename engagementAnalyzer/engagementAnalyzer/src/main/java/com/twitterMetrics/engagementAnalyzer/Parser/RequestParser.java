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
			    		for(JsonElement elem: tweetIdsArray)
			    			this.tweetIds[idx++] = elem.getAsString();
			    		
			    	}catch(Exception e) {
			    		e.printStackTrace();
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
	
	// Method for request the Bearer token from the extern of the class
	// bearerToken is a mandatory field, if not present inside the class when requested throw an exception
	public String getBearerToken() throws RequestBodyFieldNotPresentException{
		// TODO implement special behavior
		return bearerToken;
	}
	
	// Method for the set of the bearer
	public void setBearerToken(String bearerToken) {
		this.bearerToken = bearerToken;
	}
	
	// Method for request the parsed list of tweets from the extern of the class
	// tweetsIds is a mandatory field, if not present inside the class when requested throw an exception
	public String[] getTweetsIds() throws RequestBodyFieldNotPresentException{
		// TODO implement special behavior
		return tweetIds;
	}
	
	// Method for request the parsed filters from the extern of the class
	// filters aren't a mandatory field, if not present return an empty $nop (no operation operator)
	// if there are filters in the request return always a $nop operator, but with at least one filter inside.
	public LogicOperator getFilters() {
		// TODO implement special behavior
		return filters;
	}

}
