package com.twitterMetrics.engagementAnalyzer.Operators;

import com.google.gson.JsonObject;

public abstract class Operator {

	public Operator() {}
	
	
	
	public abstract String toString();
	
	public abstract JsonObject toJson();
}
