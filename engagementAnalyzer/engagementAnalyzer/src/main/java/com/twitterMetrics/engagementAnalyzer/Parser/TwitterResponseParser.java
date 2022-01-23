package com.twitterMetrics.engagementAnalyzer.Parser;

import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;

public class TwitterResponseParser {
	
	// list of errore returned from the twitter API
	private JsonArray errors;
	
	// Object that contain a list of tweets parsed from the requested
	private TweetList tweets;
	
	/**
	 * TwitterResponseParser constructor
	 * 
	 * @param response Twitter API response as jsonObject that will be parsed
	 * @throws Exception An exception will be raised if there is some error in the expected structure.
	 */
	public TwitterResponseParser(JsonObject response) throws Exception {
		
		tweets = new TweetList();
		parse(response);
		
	}
	
	private void parse(JsonObject response) throws Exception{
		
		for(Map.Entry<String, JsonElement> entry : response.entrySet()) {
			
		    switch(entry.getKey()) {
		    	
		    	// case that parse the list of retriven tweets
			    case "data":
			    		tweets = parseTweetList(entry.getValue());
			    	break;
			    // case that save the list of errors
		    	case "errors":
		    			this.errors = entry.getValue().getAsJsonArray();
			    	break;
			    default:
			    	break;
		    }
		}
	}
	
	
	/**
	 * Errors getter, method that return the erros inside the twitter api response in JsonElement format
	 * 
	 * @return erros inside the twitter api response in JsonElement format
	 */
	public JsonElement getErrors() {
		return errors;
	}

	
	/**
	 * Tweets getter, method that return the tweets inside the twitter api response as a TweetList object
	 * 
	 * @return the tweets inside the twitter api response as a TweetList object
	 */
	public TweetList getTweets() {
		return tweets;
	}
	
	// this method get a list of tweets in jsonArray format 
	// and return a TweetList
	private TweetList parseTweetList(JsonElement rawTweets) throws Exception{
		
		if(rawTweets.isJsonArray()) {
			
			JsonArray ja = rawTweets.getAsJsonArray();
			TweetList tweetList = new TweetList();
			
			for(JsonElement je: ja) {
				tweetList.add(je);
			}
			
			return tweetList;
		}else {
			throw new Exception("ERROR: Malformaed response from Twitter API.");
		}
		
	}
}
