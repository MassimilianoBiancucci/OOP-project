package com.twitterMetrics.engagementAnalyzer.Service;

import com.google.gson.JsonObject;

public interface EngagementService {
	
	public abstract JsonObject getRawTweetsData(JsonObject requestBody, String userId) throws Exception;
	
	public abstract JsonObject getTweetsStats(JsonObject requestBody, String userId) throws Exception;
	
}
