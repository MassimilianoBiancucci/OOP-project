package com.twitterMetrics.engagementAnalyzer.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter;
import com.twitterMetrics.engagementAnalyzer.Filters.Filter.Field;

public class TweetList {

	private ArrayList<Tweet> tweets;
	private StatisticsList statistics;
	private Field[] statFields = new Field[] {Field.retweet, Field.reply, Field.like, Field.quote, Field.engagement};
	
	/**
	 * Tweet list constructor 
	 * Initilize all the internal objects
	 */
	public TweetList() {
		// initialize the internal Array list
		tweets = new ArrayList<Tweet>();
		statistics = new StatisticsList(statFields);
	}
	
	
	/**
	 * Method that return the tweet at given position
	 * 
	 * @param idx the idx of the requested tweet
	 * @return the tweet at the array position idx
	 */
	public Tweet get(int idx) {
		return tweets.get(idx);
	}
	
	
	/**
	 * Method that return a specific field of the tweet at position idx in the tweet array as int value.
	 * this method only accept public metric fields.
	 * 
	 * @param field field target that will be returned
	 * @param idx tweet position in the tweet array 
	 * @return the requested value of the tweet at position idx
	 * @throws Exception an exception will be returned if the field passed isn't accepted by the method
	 */
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
	
	
	/**
	 * Method that return a specific field of the tweet at position idx in the tweet array as double value.
	 * this method only accept public metric fields.
	 * 
	 * @param field field target that will be returned
	 * @param idx tweet position in the tweet array 
	 * @return the requested value of the tweet at position idx
	 * @throws Exception an exception will be returned if the field passed isn't accepted by the method
	 */
	public double getDobleField(Field field, int idx) throws Exception{
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
				return tweets.get(idx).getEngagement();
			default:
				throw new Exception("TweetList.getIntField() do not support the field: " + Filter.field2String.get(field) + ".");
		}
	}
	
	
	/**
	 * Method that return the cration date of the tweet with position idx in the array
	 * in LocalDateTime format.
	 * 
	 * @param idx 
	 * @return cration date of the tweet with position idx
	 * @throws Exception 
	 */
	public LocalDateTime getDate(int idx) throws Exception {
		return tweets.get(idx).getDate();
	}
	
	
	/**
	 * Method that return the creation time of the tweet with position idx in the array 
	 * in LocalTime format.
	 * 
	 * @param idx
	 * @return creation time of the tweet with position idx
	 * @throws Exception
	 */
	public LocalTime getTime(int idx) throws Exception {
		return tweets.get(idx).getDate().toLocalTime();
	}
	
	
	/**
	 * Method that return the text inside the tweet at position idx
	 * 
	 * @param idx
	 * @return text inside the tweet at position idx
	 */
	public String getMsg(int idx) {
		return tweets.get(idx).getMsg();
	}
	
	
	/**
	 * Method that return the size of the array inside the TweetList object
	 * 
	 * @return the size of the Tweet array
	 */
	public int size() {
		return tweets.size();
	}
	
	
	/**
	 * Method that replace The tweet at position idx with the given tweet
	 * 
	 * @param idx position of the tweet that will be substituted
	 * @param tweet new tweet that take the idx position in the tweet list
	 */
	public void set(int idx, Tweet tweet) {
		tweets.set(idx, tweet);
	}
	
	
	/**
	 * Method that append one tweet to the internal list
	 * 
	 * @param tweet the tweet added to the TweetList
	 */
	public void add(Tweet tweet) {
		tweets.add(tweet);
	}
	
	
	/**
	 * Method that parse and append one tweet to the internal list
	 * given a tweet in JsonObject format.
	 * 
	 * @param tweet a JsonObject that encode a tweet with all the expected fields.
	 * @throws Exception raise an exception if the tweet in JsonObject format isn't correctly formatted.
	 */
	public void add(JsonObject tweet) throws Exception {
		tweets.add(new Tweet(tweet));
	}
	
	
	/**
	 * Method that parse and append one tweet to the internal list
	 * given a tweet in JsonObject format.
	 * 
	 * @param tweet a JsonElement that encode a tweet with all the expected fields.
	 * @throws Exception raise an exception if the JsonElement passed isn't a JsonObject or
	 * if the tweet in JsonObject format isn't correctly formatted.
	 */
	public void add(JsonElement tweet) throws Exception {
		if(!tweet.isJsonObject()) throw new Exception("TweetList.add() expect as argument a jsonElement convertible in jsonObject.");
		tweets.add(new Tweet(tweet.getAsJsonObject()));
	}
	
	
	/**
	 * Method that remove all tweets with the specified tweetId.
	 * 
	 * @param TweetId the tweet id used for the deletion.
	 */
	public void removeByTweetId(long TweetId) {
		tweets.removeIf(tweet -> tweet.getTweetId() == TweetId);
	}
	
	
	/**
	 * Method that remove all the the tweets inside the TweetList by the tweetId of the tweet passed.
	 * 
	 * @param tweet reference tweet for the deletion of the tweets inside the TweetList.
	 */
	public void remove(Tweet tweet) {
		removeByTweetId(tweet.getTweetId());
	}
	
	
	/**
	 * Method that check if a tweet is already inside by tweet id.
	 * 
	 * @param TweetId Long passed as reference for the tweet reserach.
	 * @return true if one tweet with the passed id is present inside the list.
	 */
	public boolean containsTweet(long TweetId) {
		
		for(Tweet tweet: tweets) {
			if(tweet.getTweetId() == TweetId) return true;
		}
		return false;
	}
	
	
	/**
	 * Method that create a Tweetlist with a new internal list, with reference at 
	 * the old objectes contained in the tweet array original Tweet list.
	 * 
	 * @return a copy of the current TweetList
	 */
	@Override
	public TweetList clone() {
		// this method create another object with the same content, but with tweets inside passed by reference
		
		TweetList tweetListClone = new TweetList();
		
		for(Tweet tweet : tweets) {
			tweetListClone.add(tweet);
		}
		
		return tweetListClone;
	}
	
	
	/**
	 * Method that calculate the statistics of all the fields inside 
	 * the contained tweets and return a list object of all these statistics
	 * 
	 * @return a StatisticsList object that contain all the statistics of the contained tweets.
	 * @throws Exception An exception can be raised in case of internal errors
	 */
	public StatisticsList getStatistics() throws Exception {
		
		// remove all the values loaded before
		statistics.clear();
		
		// load all the newest values inside the statistics objects
		for (int i = 0; i < tweets.size(); i++) {
			for(Field field: statFields) {
				statistics.add(field, getDobleField(field, i));
			}
		}
		
		// return the statistics list
		return statistics;
	}
	
	
	@Override
	public String toString() {
		return "TweetList [tweets=" + tweets + ", statistics=" + statistics + "]";
	}
	
	
	/**
	 * Method that return this object in JsonElement format
	 * 
	 * @return the JsonElement rapresentation of this object
	 */
	public JsonElement toJson() {
		
		JsonArray tweetsJAarray = new JsonArray();
		
		for(Tweet tweet: tweets) {
			tweetsJAarray.add(tweet.toJson());
		}
		
		return tweetsJAarray;
	}
}
