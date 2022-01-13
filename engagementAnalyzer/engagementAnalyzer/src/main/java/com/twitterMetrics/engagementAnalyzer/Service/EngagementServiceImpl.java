package com.twitterMetrics.engagementAnalyzer.Service;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.twitterMetrics.engagementAnalyzer.Parser.RequestParser;
import com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller.TwitterApiCaller;

@Service
public class EngagementServiceImpl implements EngagementService{
	
	public JsonObject getRawTweetsData(JsonObject requestBody) {
		
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

	public JsonObject getTweetsStats(JsonObject requestBody) {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}

	public JsonObject getTweetsStatsMeta() {
		// TODO Auto-generated method stub
		
		return new JsonObject();
	}
	
}
