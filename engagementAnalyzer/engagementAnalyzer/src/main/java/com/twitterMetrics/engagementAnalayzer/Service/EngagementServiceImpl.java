package com.twitterMetrics.engagementAnalayzer.Service;

import java.io.IOException;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Parser.RequestParser;
import com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller.TwitterApiCaller;


public class EngagementServiceImpl implements EngagementService{
	
	public JsonObject getRawTweetsData(String requestBody) {
		
		try {
			
			TwitterApiCaller api = new TwitterApiCaller();
			RequestParser requestParser = new RequestParser(requestBody);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return new JsonObject();
	}

	public JsonObject getRawTweetsDataMeta() {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}

	public JsonObject getTweetsStats(String requestBody) {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}

	public JsonObject getTweetsStatsMeta() {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}
	
}
