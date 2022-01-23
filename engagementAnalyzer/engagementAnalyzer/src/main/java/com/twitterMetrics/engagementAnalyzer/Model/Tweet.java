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
	

	/**
	 * Tweet basic constructor with only the basics insormations
	 * 
	 * @param id the twitter id of this tweet
	 * @param msg the message contained inside the tweet
	 * @param date the creation date of the tweet in string format
	 */
	public Tweet(int id, String msg, String date) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		this.date = new DateParser(date);
	}
	
	 
	/**
	 * constructor with engagement raw date
	 * 
	 * @param id the twitter id of this tweet
	 * @param msg the message contained inside the tweet
	 * @param date the creation date of the tweet in string format
	 * @param retweet the number of retweet associated to this tweet 
	 * @param reply the number of reply associated to this tweet
	 * @param like the number of like associated to this tweet
	 * @param quote the number of quote associated to this tweet
	 */
	public Tweet(long id, String msg, String date, int retweet, int reply, int like, int quote) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		
		this.setRetweet(retweet);
		this.setReply(reply);
		this.setLike(like);
		this.setQuote(quote);
		
		this.date = new DateParser(date);
	}
	
	
	/**
	 * constructor with engagement raw data, with embedded json parsing 
	 * 
	 * @param jo JsonObject that ancode all the tweet information in a given format
	 * @throws Exception Exception raised if the json object passed has an unexpected format
	 */
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
	

	// method that get a json object that contain all the public metrics and load it in the object
	private void setPublicMetrics(JsonObject jo) throws Exception {
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
		

	/**
	 * method for setting specific weight for each engagement values used for the cumulative metric
	 * 
	 * @param retweetWeight retweet weight used in the calculation of the cumulative engagement metric
	 * @param replyWeight reply weight used in the calculation of the cumulative engagement metric
	 * @param likeWeight like weight used in the calculation of the cumulative engagement metric
	 * @param quoteWeight quote weight used in the calculation of the cumulative engagement metric
	 */
	public void setWeights(double retweetWeight, double replyWeight, double likeWeight, double quoteWeight) {
		
		Tweet.setRetweetWeight(retweetWeight);
		Tweet.setReplyWeight(replyWeight);
		Tweet.setLikeWeight(likeWeight);
		Tweet.setQuoteWeight(quoteWeight);
	}
	
	
	/**
	 * method for reset specific weight of each engagement value
	 */
	public void resetWeights() {
		
		setWeights(1.0, 1.0, 1.0, 1.0);
	}
	
	
	/**
	 * getter for tweet id
	 * 
	 * @return the tweet id
	 */
	public long getTweetId() {
		return tweetId;
	}

	/**
	 * setter fot the tweet id 
	 * 
	 * @param tweetId the tweet id
	 */
	public void setTweetId(long tweetId) {
		this.tweetId = tweetId;
	}
	
	/**
	 * getter for the tweet creation date in LocalDateTime format
	 * 
	 * @return the tweet creation date in LocalDateTime format
	 * @throws Exception can rise an exception if the internal date parser has an unexpected value
	 */
	public LocalDateTime getDate() throws Exception {
		return this.date.getDate();
	}
	

	/**
	 * method for getting the tweet creation date in string format
	 * 
	 * @return the tweet creation date in string format
	 */
	public String getStringDate() {
		return this.date.getSimpleDate();
	}
	

	/**
	 * method for getting the date in twitter string NumberFormatException
	 * 
	 * @return 
	 */
	public String getTwitterDate() {
		return this.date.getTwitterDate();
	}
	
	
	/**
	 * method for the creation date setting from a string format
	 * 
	 * @param stringDate creation date in string format
	 */
	public void setDate(String stringDate) {
		this.date.setDate(stringDate);
	}
	
	
	/**
	 * getter for tweet message
	 * 
	 * @return the text inside the tweet
	 */
	public String getMsg() {
		return msg;
	}
	
	
	/**
	 * setter for tweet message
	 * 
	 * @param msg tweet message in string format
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	/**
	 * getter for the retweet count
	 * 
	 * @return the count of retweet of this tweet
	 */
	public int getRetweet() {
		return retweet;
	}
	
	
	/**
	 * setter for the retweet count
	 * 
	 * @param retweet the number of retweets of this tweet
	 */
	public void setRetweet(int retweet) {
		this.retweet = retweet;
	}
	
	
	/**
	 * getter for the reply count
	 * 
	 * @return the count of reply of this tweet
	 */
	public int getReply() {
		return reply;
	}
	
	
	/**
	 * setter for the reply count
	 * 
	 * @param reply the number of reply of this tweet
	 */
	public void setReply(int reply) {
		this.reply = reply;
	}
	
	
	/**
	 * getter for the like count
	 * 
	 * @return the count of like of this tweet
	 */
	public int getLike() {
		return like;
	}
	
	/**
	 * setter for the like count
	 * 
	 * @param like the number of like of this tweet
	 */
	public void setLike(int like) {
		this.like = like;
	}
	
	
	/**
	 * getter for the quote count
	 * 
	 * @return the count of quote of this tweet
	 */
	public int getQuote() {
		return quote;
	}

	/**
	 * setter for the quote count
	 * 
	 * @param quote the number of quote of this tweet
	 */
	public void setQuote(int quote) {
		this.quote = quote;
	}
	
	
	/**
	 * static getter for the retweet weight of all tweets
	 * 
	 * @return the weight of retweet for all tweets
	 */
	public static double getRetweetWeight() {
		return retweetWeight;
	}
	
	/**
	 * static setter for the retweet weight of all tweets
	 * 
	 * @param retweetWeight the weight of retweet for all tweets
	 */
	public static void setRetweetWeight(double retweetWeight) {
		Tweet.retweetWeight = retweetWeight;
	}
	
	
	/**
	 * static getter for the reply weight of all tweets
	 * 
	 * @return the weight of reply for all tweets
	 */
	public static double getReplyWeight() {
		return replyWeight;
	}
	
	/**
	 * static setter for the reply weight of all tweets
	 * 
	 * @param replyWeight the weight of reply for all tweets
	 */
	public static void setReplyWeight(double replyWeight) {
		Tweet.replyWeight = replyWeight;
	}
	
	
	/**
	 * static getter for the like weight of all tweets
	 * 
	 * @return the weight of like for all tweets
	 */
	public static double getLikeWeight() {
		return likeWeight;
	}

	
	/**
	 * static setter for the like weight of all tweets
	 * 
	 * @param likeWeight the weight of like for all tweets
	 */
	public static void setLikeWeight(double likeWeight) {
		Tweet.likeWeight = likeWeight;
	}
	
	
	/**
	 * static getter for the quote weight of all tweets
	 * 
	 * @return the weight of quote for all tweets
	 */
	public static double getQuoteWeight() {
		return quoteWeight;
	}
	
	
	/**
	 * static setter for the quote weight of all tweets
	 * 
	 * @param quoteWeight the weight of quote for all tweets
	 */
	public static void setQuoteWeight(double quoteWeight) {
		Tweet.quoteWeight = quoteWeight;
	}
	
	/**
	 * getter for the engagement unified metric
	 * 
	 * @return the engagement unified metric
	 */
	public double getEngagement() {
		return retweet*retweetWeight + reply*replyWeight + like*likeWeight + quote*quoteWeight;
	}
	
	
	/**
	 *Method that return a new tweet object with the same properties of the current tweet 
	 *
	 * @return the copy of the current tweet
	 */
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
	
	/**
	 * Method that return this object in JsonObject format
	 * 
	 * @return the JsonObject rapresentation of this object
	 */
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
