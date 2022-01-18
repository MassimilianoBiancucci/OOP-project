package com.twitterMetrics.engagementAnalyzer.Model;

import java.util.ArrayList;

import com.google.gson.JsonObject;

public class TweetList {

	private ArrayList<Tweet> tweets;
	
	public TweetList() {}

	public Tweet get(int idx) {
		return tweets.get(idx);
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
	
	public String toString() {
		// TODO implement
		return "";
	}
	
	public String toJson() {
		// TODO implement
		return "";
	}
}
