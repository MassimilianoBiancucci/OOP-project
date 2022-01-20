package com.twitterAnalyzer.engagementAnalyzer.TestService;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Parser.RequestParser;
import com.twitterMetrics.engagementAnalyzer.Parser.TwitterResponseParser;
import com.twitterMetrics.engagementAnalyzer.Service.EngagementService;

public class EngagementTestServiceImpl implements EngagementService{

	@Override
	public JsonObject getRawTweetsData(JsonObject requestBody, String userId) throws Exception {
		
		// In this test method the request is processed but not used,
		// due to the fake implementation of the Twitter API caller
		RequestParser requestParser = new RequestParser(requestBody);
		
		// In the test service for avoid the usage of the twitterApiCaller will be used only fake data 
		// similar to the original data but static, this response don't change for all the routes except for the metadata routes
		JsonObject tweeterApiResponse = JsonParser.parseString(EngagementTestServiceFakeData.fakeTwitterApiResponse).getAsJsonObject();
		
		// at this point all the tweets are parsed
		TwitterResponseParser tweeterApiResponseParsed = new TwitterResponseParser(tweeterApiResponse);
		
		// now the filters will be applied to the tweets 
		LogicOperator filterRoot = requestParser.getFilters();
		TweetList filteredTweetList = filterRoot.applayFilters(tweeterApiResponseParsed.getTweets());
		
		// Response buliding
		JsonObject response = new JsonObject();
		response.addProperty("status", "success");
		response.addProperty("tweets_count", tweeterApiResponseParsed.getTweets().size());
		response.add("tweets", filteredTweetList.toJson());
		response.add("errors", tweeterApiResponseParsed.getErrors());
		
		return response;
	}

	
	// method that process the request of tweets engagement metrics
	@Override
	public JsonObject getTweetsStats(JsonObject requestBody, String userId) throws Exception {
		
		// In this test method the request is processed but not used,
		// due to the fake implementation of the Twitter API caller
		RequestParser requestParser = new RequestParser(requestBody);	
		
		// In the test service for avoid the usage of the twitterApiCaller will be used only fake data 
		// similar to the original data but static, this response don't change for all the routes except for the metadata routes
		JsonObject tweeterApiResponse = JsonParser.parseString(EngagementTestServiceFakeData.fakeTwitterApiResponse).getAsJsonObject();
		
		// at this point all the tweets are parsed
		TwitterResponseParser tweeterApiResponseParsed = new TwitterResponseParser(tweeterApiResponse);
		
		// now the filters will be applied to the retrived tweets
		LogicOperator filterRoot = requestParser.getFilters();
		TweetList filteredTweetList = filterRoot.applayFilters(tweeterApiResponseParsed.getTweets());
		
		// Response buliding
		JsonObject response = new JsonObject();
		response.addProperty("status", "success");
		response.addProperty("tweets_count", tweeterApiResponseParsed.getTweets().size());
		response.add("tweetsMetrics", filteredTweetList.getStatistics().toJson());
		response.add("errors", tweeterApiResponseParsed.getErrors());
		
		return response;
	}
}
