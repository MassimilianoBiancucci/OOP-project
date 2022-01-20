package com.twitterAnalyzer.engagementAnalyzer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitterAnalyzer.engagementAnalyzer.TestService.EngagementTestServiceFakeData;
import com.twitterAnalyzer.engagementAnalyzer.TestService.EngagementTestServiceImpl;


class EngagementAnalyzerApplicationTests {
	
	static EngagementTestServiceImpl service;
	
	@BeforeAll
	public static void setup() {
		service = new EngagementTestServiceImpl();
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	// TESTS ON THE RAW TWEETS SERVICE ////////////////////////////////////////////////
	
	@Test
	void rawTweetsNoFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.noFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.rawTweetsNoFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
		
	@Test
	void rawTweetsMetricFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.metricFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.rawTweetsMetricFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void rawTweetsMessageFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.messageFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.rawTweetsMessageFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void rawTweetsDateFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.dateFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.rawTweetsDateFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void rawTweetsTimeFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.timeFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.rawTweetsTimeFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	// TESTS ON THE TWEETS METRICS SERVICE ////////////////////////////////////////////////
	
	@Test
	void tweetsMetricsNoFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.noFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.metricsNoFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void tweetsMetricsMetricFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.metricFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.metricsMetricFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void tweetsMetricsMessageFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.messageFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.metricsMessageFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void tweetsMetricsDateFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.dateFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.metricsDateFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	@Test
	void tweetsMetricsTimeFiltersRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.timeFiltersRequest).getAsJsonObject();
		
		// Loading the expected json Response as JsonObject
		JsonObject expectedResponse = JsonParser.parseString(EngagementTestServiceFakeData.metricsTimeFiltersResponse).getAsJsonObject();
		
		try {
			JsonObject response = service.getRawTweetsData(request, null);
			assertEquals(expectedResponse, response);
			
		}catch(Exception e) {
			fail("This test should not fail!");
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	// TESTS ON WRONG REQUESTS ////////////////////////////////////////////////////////////
	
	@Test
	void wrongTweetIdsRequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.wrongTweetIdsRequest).getAsJsonObject();
		
		// Loading the expected exception message
		String expectedException = EngagementTestServiceFakeData.wrongTweetIdsException;
		
		try {
			service.getRawTweetsData(request, null);
			fail("This run should raise an exception!");
			
		}catch(Exception e) {
			assertEquals(expectedException, e.getMessage());
		}
	}
	
	@Test
	void wrongFilters1RequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.wrongFilters1Request).getAsJsonObject();
		
		// Loading the expected exception message
		String expectedException = EngagementTestServiceFakeData.wrongFilters1Exception;
		
		try {
			service.getRawTweetsData(request, null);
			fail("This run should raise an exception!");
			
		}catch(Exception e) {
			assertEquals(expectedException, e.getMessage());
		}
	}
	
	@Test
	void wrongFilters2RequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.wrongFilters2Request).getAsJsonObject();
		
		// Loading the expected exception message
		String expectedException = EngagementTestServiceFakeData.wrongFilters2Exception;
		
		try {
			service.getRawTweetsData(request, null);
			fail("This run should raise an exception!");
			
		}catch(Exception e) {
			assertEquals(expectedException, e.getMessage());
		}
	}
	
	@Test
	void wrongFilters3RequestTest() {
		
		// Loading the json request as JsonObject
		JsonObject request = JsonParser.parseString(EngagementTestServiceFakeData.wrongFilters3Request).getAsJsonObject();
		
		// Loading the expected exception message
		String expectedException = EngagementTestServiceFakeData.wrongFilters3Exception;
		
		try {
			service.getRawTweetsData(request, null);
			fail("This run should raise an exception!");
			
		}catch(Exception e) {
			assertEquals(expectedException, e.getMessage());
		}	
	}
	
}
