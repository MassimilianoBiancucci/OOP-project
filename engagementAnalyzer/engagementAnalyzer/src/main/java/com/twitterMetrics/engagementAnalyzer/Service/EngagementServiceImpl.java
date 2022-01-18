package com.twitterMetrics.engagementAnalyzer.Service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Parser.RequestParser;
import com.twitterMetrics.engagementAnalyzer.Parser.TwitterResponseParser;
import com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller.TwitterApiCaller;

@Service
public class EngagementServiceImpl implements EngagementService{
	
	
	
	// method that process the request of raw tweets
	public JsonObject getRawTweetsData(JsonObject requestBody) throws Exception {
		
		RequestParser requestParser = new RequestParser(requestBody);	
		TwitterApiCaller api = new TwitterApiCaller(requestParser.getBearerToken());
		JsonObject tweeterApiResponse = api.getTweetsFromIds(requestParser.getTweetsIds());
		
		//DEBUG
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(tweeterApiResponse));
		System.out.println(" --- ");
		
		TwitterResponseParser tweeterApiResponseParsed = new TwitterResponseParser(tweeterApiResponse);
		
		
		return new JsonObject();
	}

	
	public JsonObject getRawTweetsDataMeta() {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}

	public JsonObject getTweetsStats(JsonObject requestBody) {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}

	public JsonObject getTweetsStatsMeta() {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}
	
}
