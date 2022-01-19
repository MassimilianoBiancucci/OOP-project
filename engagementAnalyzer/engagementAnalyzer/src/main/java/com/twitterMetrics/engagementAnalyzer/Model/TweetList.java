package com.twitterMetrics.engagementAnalyzer.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;

public class TweetList {

	private ArrayList<Tweet> tweets;
	
	public TweetList() {
		// initialize the internal Array list
		tweets = new ArrayList<Tweet>();
	}

	public Tweet get(int idx) {
		return tweets.get(idx);
	}
	
	public int getIntField(Field field, int idx) throws Exception{
		switch(field) {
			case retweet:
				return tweets.get(idx).getRetweet();
			case reply:
				return tweets.get(idx).getReply();
			case like:
				return tweets.get(idx).getLike();
			case quote:
				return tweets.get(idx).getQuote();
			case engagement:
				return (int) tweets.get(idx).getEngagement();
			default:
				throw new Exception("TweetList.getIntField() do not support the field: " + Filter.field2String.get(field) + ".");
		}
	}
	
	public LocalDateTime getDate(int idx) {
		return tweets.get(idx).getDate();
	}
	
	public String getMsg(int idx) {
		return tweets.get(idx).getMsg();
	}
	
	public int size() {
		return tweets.size();
	}
	
	public void set(int idx, Tweet tweet) {
		tweets.set(idx, tweet);
	}
	
	public void add(Tweet tweet) {
		tweets.add(tweet);
	}
	
	public void add(JsonObject tweet) throws Exception {
		tweets.add(new Tweet(tweet));
	}
	
	public void add(JsonElement tweet) throws Exception {
		if(!tweet.isJsonObject()) throw new Exception("TweetList.add() expect as argument a jsonElement convertible in jsonObject.");
		tweets.add(new Tweet(tweet.getAsJsonObject()));
	}
	
	// method that remove all tweets with the specified tweetId
	public void removeByTweetId(long TweetId) {
		tweets.removeIf(tweet -> tweet.getTweetId() == TweetId);
	}
	
	public void remove(Tweet tweet) {
		removeByTweetId(tweet.getTweetId());
	}
	
	// method that check if a tweet is already inside by tweet id
	public boolean containsTweet(long TweetId) {
		
		for(Tweet tweet: tweets) {
			if(tweet.getTweetId() == TweetId) return true;
		}
		return false;
	}
	
	@Override
	public TweetList clone() {
		// this method create another object with the same content, but with tweets inside passed by reference
		
		TweetList tweetListClone = new TweetList();
		
		for(Tweet tweet : tweets) {
			tweetListClone.add(tweet);
		}
		
		return tweetListClone;
	}
	
	/** if clone() don't work properly
	public TweetList deepClone() {
		// this method create another object with the same content,with even cloned tweets
		return ;
	}*/
	
	public String toString() {
		// TODO implement
		return "";
	}
	
	// method that return the tweet list as a jsonArray with a JsonObject for each tweet
	public JsonElement toJson() {
		
		JsonArray tweetsJAarray = new JsonArray();
		
		for(Tweet tweet: tweets) {
			tweetsJAarray.add(tweet.toJson());
		}
		
		return tweetsJAarray;
	}
}
