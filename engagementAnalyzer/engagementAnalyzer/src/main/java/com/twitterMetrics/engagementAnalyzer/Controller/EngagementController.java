package com.twitterMetrics.engagementAnalyzer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.twitterMetrics.engagementAnalyzer.Service.EngagementService;
import com.twitterMetrics.engagementAnalyzer.Service.RoutesMetadata;

@RestController
public class EngagementController {
	
	@Autowired()
	EngagementService engagementService;
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR RAW TWEETS BY TWEETS IDS///////////////////////////////////////////
	
	// route that return raw tweets in json format with 
	@RequestMapping(value = "/tweets", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweets(@RequestBody JsonObject requestBody){
		
		try {
			
			JsonObject response = engagementService.getRawTweetsData(requestBody, null);
			return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
			
		}catch(Exception e) {
			
			JsonObject errorResponse = new JsonObject();

			String exceptionMessage = e.getMessage();
			if(exceptionMessage == null) exceptionMessage = "for unknown error.";
			
			errorResponse.addProperty("status", "Request failed");
			errorResponse.addProperty("detail", exceptionMessage);
			return new ResponseEntity<JsonObject>(errorResponse, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	// method that return the metadata request for the /tweets route
	@RequestMapping(value = "/tweets/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweetsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.rawTweetsRequestMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR RAW TWEETS BY USER ID//////////////////////////////////////////////
	
	// route that return raw tweets for certain user
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweets(@PathVariable("userId") String userId, @RequestBody JsonObject requestBody){

		try {
			
			JsonObject response = engagementService.getRawTweetsData(requestBody, userId);
			return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
			
		}catch(Exception e) {
			
			JsonObject errorResponse = new JsonObject();

			String exceptionMessage = e.getMessage();
			if(exceptionMessage == null) exceptionMessage = "for unknown error.";
			
			errorResponse.addProperty("status", "Request failed");
			errorResponse.addProperty("detail", exceptionMessage);
			return new ResponseEntity<JsonObject>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	// method that return the metadata request for the /user/{userId} route
	@RequestMapping(value = "/user/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweetsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.rawTweetsRequestByUserIdMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY TWEETS IDS///////////////////////////////////////
	
	// route that return engagement statistics based on passed tweets
	@RequestMapping(value = "/tweets/metrics", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweetsMetrics(@RequestBody JsonObject requestBody){
		
		return new ResponseEntity<JsonObject>(new JsonObject(), HttpStatus.OK);
	}
	
	// method that return the metadata request of the /tweets/metrics route
	@RequestMapping(value = "/tweets/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweetsMetricsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.tweetsStatisticsRequestMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY USER ID /////////////////////////////////////////
	
	// route that return engagement statistics based on tweets of specified user
	@RequestMapping(value = "/user/{userId}/metrics", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweetsMetrics(@PathVariable("userId") String userId, @RequestBody JsonObject requestBody){
		
		return new ResponseEntity<JsonObject>(new JsonObject(), HttpStatus.OK);
	}
	
	// method that return the metadata request for the /user/{userId}/metrics route
	@RequestMapping(value = "/user/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweetsMetricsMetadata(){
		
		JsonObject response = JsonParser.parseString(RoutesMetadata.tweetsStatisticsRequestByUserIdMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
}
