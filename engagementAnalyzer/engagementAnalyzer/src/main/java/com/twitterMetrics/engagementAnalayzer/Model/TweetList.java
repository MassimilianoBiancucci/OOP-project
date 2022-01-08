package com.twitterMetrics.engagementAnalayzer.Model;

import java.util.ArrayList;

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
	
	public String toString() {
		// TODO implement
		return "";
	}
	
	public String toJson() {
		// TODO implement
		return "";
	}
}
