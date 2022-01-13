package com.twitterMetrics.engagementAnalyzer.Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Parser.DateParser;

public class Tweet {
	
	// Tweet id, used as uniq identifier
	private int tweetId = -1;
	
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
	
	// Date time format consider 'Z' character for time with zero offset or Zulu time.
	private static DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	private static DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSS"); 
	
	// basic constructor
	public Tweet(int id, String msg, String date) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		this.date = new DateParser(date);
	}
	
	// constructor with engagement raw data 
	public Tweet(int id, String msg, int retweet, int reply, int like, int quote) {
		
		this.setTweetId(id);
		this.setMsg(msg);
		
		this.setRetweet(retweet);
		this.setReply(reply);
		this.setLike(like);
		this.setQuote(quote);
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
	public int getTweetId() {
		return tweetId;
	}

	public void setTweetId(int tweetId) {
		this.tweetId = tweetId;
	}
	
	public LocalDateTime getDate() {
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
	public String toString() {
		
		String stringTweet = "---------------\n";
		stringTweet = stringTweet + "tweet id:___" + this.tweetId + "\n";
		stringTweet = stringTweet + "tweet date:_" + getStringDate() + "\n";
		stringTweet = stringTweet + "tweet msg:__" + this.msg + "\n";
		stringTweet = stringTweet + "-- engagement values --\n";
		stringTweet = stringTweet + "Retweet:____" + this.retweet + "\n";
		stringTweet = stringTweet + "Reply:______" + this.tweetId + "\n";
		stringTweet = stringTweet + "Like:_______" + this.tweetId + "\n";
		stringTweet = stringTweet + "Quote:______" + this.tweetId + "\n";
		stringTweet = stringTweet + "-- engagement metric --\n";
		stringTweet = stringTweet + "Engagement:_" + getEngagement();
		
		return stringTweet;
	}
	
	public JsonObject toJson() {
		
		JsonObject jsonTweet = new JsonObject();
		jsonTweet.addProperty("id", getTweetId());
		jsonTweet.addProperty("date", getStringDate());
		jsonTweet.addProperty("msg", getMsg());
		jsonTweet.addProperty("engagementMetric", getEngagement());
		JsonObject engagementValues = new JsonObject();
		engagementValues.addProperty("retweet", "");
		engagementValues.addProperty("reply", "");
		engagementValues.addProperty("like", "");
		engagementValues.addProperty("quote", "");
		jsonTweet.add("engagementValues", engagementValues);
		
		return jsonTweet;
	}
}
