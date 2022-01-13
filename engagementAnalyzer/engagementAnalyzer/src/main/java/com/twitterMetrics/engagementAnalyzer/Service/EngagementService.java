package com.twitterMetrics.engagementAnalyzer.Service;

import com.google.gson.JsonObject;

public interface EngagementService {
	
	public abstract JsonObject getRawTweetsData(JsonObject requestBody);
	public abstract JsonObject getRawTweetsDataMeta();
	
	public abstract JsonObject getTweetsStats(JsonObject requestBody);
	public abstract JsonObject getTweetsStatsMeta();
	
}
