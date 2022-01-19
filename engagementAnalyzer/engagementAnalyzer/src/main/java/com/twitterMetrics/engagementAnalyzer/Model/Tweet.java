package com.twitterMetrics.engagementAnalyzer.Model;

import java.time.LocalDateTime;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;
import com.twitterMetrics.engagementAnalyzer.Parser.FiltersParser;
import com.twitterMetrics.engagementAnalyzer.supportTypes.DateValue;

public class Tweet {
	
	// Tweet id, used as uniq identifier
	private long tweetId = -1;
	
	// Tweet date of creation with ref to zulu time
	private DateParser date;
	
	// Tweet message 
	private String msg = "";
	
	// Tweet engagement variables
	private int retweet = 0;
	private int reply = 0;
	private int like = 0;
	private int quote = 0;
	
	// Tweet engagements variables weights, 
	// used for the engagement metrics 
	private static double retweetWeight = 1.0;
	private static double replyWeight = 1.0;
	private static double likeWeight = 1.0;
	private static double quoteWeight = 1.0;
	
	// basic constructor
	public Tweet(int id, String msg, String date) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		this.date = new DateParser(date);
	}
	
	// constructor with engagement raw data 
	public Tweet(long id, String msg, String date, int retweet, int reply, int like, int quote) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		
		this.setRetweet(retweet);
		this.setReply(reply);
		this.setLike(like);
		this.setQuote(quote);
		
		this.date = new DateParser(date);
	}
	
	// constructor with engagement raw data 
	public Tweet(JsonObject jo) throws Exception{
		
		// unfold the json object and load all the needed fields inside the object
		for(Map.Entry<String, JsonElement> entry : jo.entrySet()) {
			
			JsonElement je = entry.getValue();
		    switch(entry.getKey()) {
		    	
		    	// case that parse the 
			    case "id":
			    		if(je.getAsJsonPrimitive().isString()) {
			    			if(isNumeric(je.getAsString())) this.setTweetId(je.getAsLong());
			    			else throw new Exception("Tweet object initialized with malformed json object!");
			    		}else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "text":
				    	if(je.getAsJsonPrimitive().isString()) this.setMsg(je.getAsString());
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "created_at":
				    	if(je.getAsJsonPrimitive().isString()) {
				    		@SuppressWarnings("rawtypes")
							Class elemClass = FiltersParser.getElementClass(je);
				    		if(elemClass == DateValue.class) {
				    			this.date = new DateParser(je.getAsString());
				    		}
				    		else throw new Exception("Tweet object initialized with malformed json object!");
				    	}
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "public_metrics":
				    	if(je.isJsonObject()) {
				    		setPublicMetrics(je.getAsJsonObject());
				    	}
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
		    }
		}
	}
	
	public void setPublicMetrics(JsonObject jo) throws Exception {
		if(jo.size() != 4) throw new Exception("Tweet object initialized with malformed json object!");
		
		// unfold the json object and load all the needed fields inside the object
		for(Map.Entry<String, JsonElement> entry : jo.entrySet()) {
			
			JsonElement je = entry.getValue();
		    switch(entry.getKey()) {
		    	
		    	// case that parse the 
			    case "retweet_count":
			    		if(je.getAsJsonPrimitive().isNumber()) this.setRetweet(je.getAsInt());
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "reply_count":
			    		if(je.getAsJsonPrimitive().isNumber()) this.setReply(je.getAsInt());
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "like_count":
			    		if(je.getAsJsonPrimitive().isNumber()) this.setLike(je.getAsInt());
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
			    	// case that parse the 
			    case "quote_count":
			    		if(je.getAsJsonPrimitive().isNumber()) this.setQuote(je.getAsInt());
			    		else throw new Exception("Tweet object initialized with malformed json object!");
			    	break;
		    }
		}
	}
		
	// method for setting specific weight for each engagement value
	public void setWeights(double retweetWeight, double replyWeight, double likeWeight, double quoteWeight) {
		
		Tweet.setRetweetWeight(retweetWeight);
		Tweet.setReplyWeight(replyWeight);
		Tweet.setLikeWeight(likeWeight);
		Tweet.setQuoteWeight(quoteWeight);
	}
	
	// method for reset specific weight of each engagement value
	public void resetWeights() {
		
		setWeights(1.0, 1.0, 1.0, 1.0);
	}
	
	// getter and setter for tweet id
	public long getTweetId() {
		return tweetId;
	}

	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	
	public LocalDateTime getDate() throws Exception {
		return this.date.getDate();
	}
	
	// method for getting the date in a clean string format
	public String getStringDate() {
		return this.date.getSimpleDate();
	}
	
	// method for getting the date in twitter string format
	public String getTwitterDate() {
		return this.date.getTwitterDate();
	}
	
	// method for 
	public void setDate(String stringDate) {
		this.date.setDate(stringDate);
	}
	
	// getter and setter for tweet message
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	// getter and setter for the retweet count
	public int getRetweet() {
		return retweet;
	}

	public void setRetweet(int retweet) {
		this.retweet = retweet;
	}
	
	// getter and setter for the reply count
	public int getReply() {
		return reply;
	}

	public void setReply(int reply) {
		this.reply = reply;
	}

	// getter and setter for the like count
	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}
	
	// getter and setter for the quote count
	public int getQuote() {
		return quote;
	}

	public void setQuote(int quote) {
		this.quote = quote;
	}
	
	// getter and setter for the retweet count weight 
	public static double getRetweetWeight() {
		return retweetWeight;
	}

	public static void setRetweetWeight(double retweetWeight) {
		Tweet.retweetWeight = retweetWeight;
	}
	
	// getter and setter for the reply count weight
	public static double getReplyWeight() {
		return replyWeight;
	}

	public static void setReplyWeight(double replyWeight) {
		Tweet.replyWeight = replyWeight;
	}

	// getter and setter for the like count weight
	public static double getLikeWeight() {
		return likeWeight;
	}

	public static void setLikeWeight(double likeWeight) {
		Tweet.likeWeight = likeWeight;
	}
	
	// getter and setter for the quote count weight
	public static double getQuoteWeight() {
		return quoteWeight;
	}

	public static void setQuoteWeight(double quoteWeight) {
		Tweet.quoteWeight = quoteWeight;
	}
	
	public double getEngagement() {
		return retweet*retweetWeight + reply*replyWeight + like*likeWeight + quote*quoteWeight;
	}
	
	@Override
	public Tweet clone() {
		// this method create another object with the same content
		return new Tweet(this.tweetId, this.msg, getStringDate(), this.retweet, this.reply, this.like, this.quote);
	}
	
	@Override
	public String toString() {
		
		String stringTweet = "---------------\n";
		stringTweet = stringTweet + "tweet id:   " + this.tweetId + "\n";
		stringTweet = stringTweet + "tweet date: " + getStringDate() + "\n";
		stringTweet = stringTweet + "tweet msg:  " + this.msg + "\n\n";
		stringTweet = stringTweet + "# Engagement values:\n";
		stringTweet = stringTweet + "retweet:    " + this.retweet + "\n";
		stringTweet = stringTweet + "reply:      " + this.reply + "\n";
		stringTweet = stringTweet + "like:       " + this.like + "\n";
		stringTweet = stringTweet + "quote:      " + this.quote + "\n";
		stringTweet = stringTweet + "engagement: " + getEngagement() + "\n";
		
		return stringTweet;
	}
	
	public JsonObject toJson() {
		
		JsonObject jsonTweet = new JsonObject();
		jsonTweet.addProperty("id", Long.toString(getTweetId()));
		jsonTweet.addProperty("created_at", getStringDate());
		jsonTweet.addProperty("text", getMsg());
		jsonTweet.addProperty("engagement", getEngagement());
		JsonObject engagementValues = new JsonObject();
		engagementValues.addProperty("retweet_count", this.retweet);
		engagementValues.addProperty("reply_count", this.reply);
		engagementValues.addProperty("like_count", this.like);
		engagementValues.addProperty("quote_count", this.quote);
		jsonTweet.add("public_metrics", engagementValues);
		
		return jsonTweet;
	}
	
	// simple method to check if string is a number
	private boolean isNumeric(String num) {
		try {  
		    Double.parseDouble(num);  
		    return true;
	  	} catch(NumberFormatException e){  
		    return false;  
	  	}  
	}
}
