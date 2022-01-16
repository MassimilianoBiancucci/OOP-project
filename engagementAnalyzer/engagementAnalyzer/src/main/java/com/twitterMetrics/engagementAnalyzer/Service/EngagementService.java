package com.twitterMetrics.engagementAnalyzer.Service;

import com.google.gson.JsonObject;

public interface EngagementService {
	
	public abstract JsonObject getRawTweetsData(JsonObject requestBody) throws Exception;
	public abstract JsonObject getRawTweetsDataMeta();
	
	public abstract JsonObject getTweetsStats(JsonObject requestBody) throws Exception;
	public abstract JsonObject getTweetsStatsMeta();
	
}
