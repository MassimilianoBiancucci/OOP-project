package com.twitterMetrics.engagementAnalyzer.Parser;

import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class RequestParser {
	
	private JsonObject request;
	private boolean empty = true;
	
	public RequestParser(JsonObject jsonRequest) {
		
		try {
			
			this.request = jsonRequest;
			parseRequest();
			
		}catch(JsonSyntaxException e) {
			// TODO do something if the json isn't formatted correctly
			
		}catch(Exception e) {
			// TODO Implement behavior for all other possible exceptions
			throw e;
		}
		
	}
	
	private boolean parseRequest() {
		
		for(Map.Entry<String, JsonElement> entry : this.request.entrySet()) {
			
		    System.out.println("Key = " + entry.getKey() + " Value = " + entry.getValue() );
		    
		}
		
		return true;
	}
	
}
