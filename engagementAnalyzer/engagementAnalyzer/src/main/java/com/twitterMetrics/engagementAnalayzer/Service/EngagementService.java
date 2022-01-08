package com.twitterMetrics.engagementAnalayzer.Service;

import com.google.gson.JsonObject;

public interface EngagementService {
	
	public abstract JsonObject getRawTweetsData();
	public abstract JsonObject getRawTweetsDataMeta();
	
	public abstract JsonObject getTweetsStats();
	public abstract JsonObject getTweetsStatsMeta();
	
}
