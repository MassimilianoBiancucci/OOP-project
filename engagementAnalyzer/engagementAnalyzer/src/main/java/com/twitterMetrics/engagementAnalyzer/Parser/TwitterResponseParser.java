package com.twitterMetrics.engagementAnalyzer.Parser;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class TwitterResponseParser {
	
	// list of errore returned from the twitter API
	private JsonObject errors;
	
	// Object that contain a list of tweets parsed from the requested
	private TweetList tweets;
	
	public TwitterResponseParser(JsonObject response) throws Exception {
		
		tweets = new TweetList();
		parse(response);
		
	}
	
	private void parse(JsonObject response) throws Exception{
		
		for(Map.Entry<String, JsonElement> entry : response.entrySet()) {
		    
		    switch(entry.getKey()) {
		    	
		    	// case that parse the list of retriven tweets
			    case "data":
			    		
			    	break;
			    // case that save the list of errors
		    	case "errors":
		    			this.errors = entry.getValue().getAsJsonObject();
			    	break;
			    default:
			    	break;
		    }
		}
		
	}
	
	
	public JsonObject getErrors() {
		return errors;
	}

	
	public TweetList getTweets() {
		return tweets;
	}
	
	// this method get a list of tweets in jsonArray format 
	// and return a TweetList
	private TweetList parseTweetList(JsonArray ja) {
		
		
		
	}
}
