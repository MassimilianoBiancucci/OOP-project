package com.twitterMetrics.engagementAnalayzer.Service;

import com.google.gson.JsonObject;

public interface EngagementService {
	
	public abstract JsonObject getRawTweetsData(String requestBody);
	public abstract JsonObject getRawTweetsDataMeta();
	
	public abstract JsonObject getTweetsStats(String requestBody);
	public abstract JsonObject getTweetsStatsMeta();
	
}
