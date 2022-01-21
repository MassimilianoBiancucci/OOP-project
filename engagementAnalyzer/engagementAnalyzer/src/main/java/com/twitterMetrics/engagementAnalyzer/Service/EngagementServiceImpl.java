package com.twitterMetrics.engagementAnalyzer.Service;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Filters.Operators.LogicOperator;
import com.twitterMetrics.engagementAnalyzer.Model.TweetList;
import com.twitterMetrics.engagementAnalyzer.Parser.RequestParser;
import com.twitterMetrics.engagementAnalyzer.Parser.TwitterResponseParser;
import com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller.TwitterApiCaller;

@Service
public class EngagementServiceImpl implements EngagementService{
	
	
	/**
	* method that process the request of tweets engagement metrics.
	* <p>
	* This method take 
	*
	* @exception Exception can be raised by the RequestParser, the TwitterApiCaller or TwitterResponseParser.
	* @param requestBody the json request body passed to the request, passed as Gson JsonObject.
	* @param userId a string that encode the Twitter id of the requested user, if passed the TwitterApiCaller will retrive the tweets of that user, this param can be passed as null if the field "TweetIds" in the request body is passed. If userId is passed as null and the field "TweetIds" in the request body isn't present the function will rise an Exception.
	*/
	@Override
	public JsonObject getRawTweetsData(JsonObject requestBody, String userId) throws Exception {
		
		RequestParser requestParser = new RequestParser(requestBody);	
		TwitterApiCaller api = new TwitterApiCaller(requestParser.getBearerToken());
		
		JsonObject tweeterApiResponse;
		
		// if the request passed is by userId it will be passed
		// otherwise the request should contain a list of tweet's ids
		if(userId == null) {
			tweeterApiResponse = api.getTweetsFromIds(requestParser.getTweetsIds());
		}else {
			tweeterApiResponse = api.getTweetsFromUserId(userId);
		}
		
		//DEBUG #####################################################
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(gson.toJson(tweeterApiResponse));
		//System.out.println(" --- ");
		// ##########################################################
		
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

		RequestParser requestParser = new RequestParser(requestBody);	
		TwitterApiCaller api = new TwitterApiCaller(requestParser.getBearerToken());
		
		JsonObject tweeterApiResponse;
		
		// if the request passed is by userId it will be passed
		// otherwise the request should contain a list of tweet's ids
		if(userId == null) {
			tweeterApiResponse = api.getTweetsFromIds(requestParser.getTweetsIds());
		}else {
			tweeterApiResponse = api.getTweetsFromUserId(userId);
		}
		
		//DEBUG #####################################################
		//Gson gson = new GsonBuilder().setPrettyPrinting().create();
		//System.out.println(gson.toJson(tweeterApiResponse));
		//System.out.println(" --- ");
		// ##########################################################
		
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
