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
	
	
	/**
	 * Method  associated to the route /tweets
	 * 
	 * @param requestBody a json that encode the parameters of the request, refer to the docs for further details on the requestBody structure.
	 * @return the requested raw tweets in json format
	 */
	@RequestMapping(value = "/tweets", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getRawTweets(@RequestBody JsonObject requestBody){
		
		return getTweetsGeneric(requestBody, null);
	}
	
	
	/**
	 * Method associated to the route /tweets/metadata
	 * 
	 * @return the metadata request for the /tweets route in json format
	 */
	@RequestMapping(value = "/tweets/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getRawTweetsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.rawTweetsRequestMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR RAW TWEETS BY USER ID//////////////////////////////////////////////
	

	/**
	 * Method  associated to the route /user/:userId
	 * 
	 * @param userId the id of the target twitter user
	 * @param requestBody a json that encode the parameters of the request, refer to the docs for further details on the requestBody structure.
	 * @return raw tweets for the given user in json format
	 */
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getRawUserTweets(@PathVariable("userId") String userId, @RequestBody JsonObject requestBody){

		return getTweetsGeneric(requestBody, userId);
	}
	

	/**
	 * Method associated to the route /user/metadata
	 * 
	 * @return the metadata request for the /user/{userId} route in json format
	 */
	@RequestMapping(value = "/user/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getRawUserTweetsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.rawTweetsRequestByUserIdMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY TWEETS IDS///////////////////////////////////////
	
	/**
	 * Method  associated to the route /tweets/metrics
	 * 
	 * @param requestBody a json that encode the parameters of the request, refer to the docs for further details on the requestBody structure.
	 * @return engagement statistics based on passed tweets in json format
	 */
	@RequestMapping(value = "/tweets/metrics", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweetsMetrics(@RequestBody JsonObject requestBody){
		
		return getTweetsMetricsGeneric(requestBody, null);
	}
	

	/**
	 * Method associated to the route /tweets/metadata
	 * 
	 * @return the metadata request of the /tweets/metrics route in json format
	 */
	@RequestMapping(value = "/tweets/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getTweetsMetricsMetadata(){

		JsonObject response = JsonParser.parseString(RoutesMetadata.tweetsStatisticsRequestMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY USER ID /////////////////////////////////////////
	
	/**
	 * Method  associated to the route /user/:userId/metrics
	 * 
	 * @param userId the id of the target twitter user
	 * @param requestBody a json that encode the parameters of the request, refer to the docs for further details on the requestBody structure.
	 * @return engagement statistics based on tweets of specified user in json format
	 */
	@RequestMapping(value = "/user/{userId}/metrics", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweetsMetrics(@PathVariable("userId") String userId, @RequestBody JsonObject requestBody){
		
		return getTweetsMetricsGeneric(requestBody, userId);
	}
	

	/**
	 * Method associated to the route /tweets/metadata
	 * 
	 * @return the metadata request for the /user/{userId}/metrics route in json format
	 */
	@RequestMapping(value = "/user/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<JsonObject> getUserTweetsMetricsMetadata(){
		
		JsonObject response = JsonParser.parseString(RoutesMetadata.tweetsStatisticsRequestByUserIdMetadta).getAsJsonObject();
		return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
	}
	
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////
	// PRIVATE METHODS /////////////////////////////////////////
	
	private ResponseEntity<JsonObject> getTweetsGeneric(JsonObject requestBody, String userId){
		
		try {
			
			JsonObject response = engagementService.getRawTweetsData(requestBody, userId);
			return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
			
		}catch(Exception e) {
			
			JsonObject errorResponse = new JsonObject();
			String exceptionMessage = e.getMessage();
			
			errorResponse.addProperty("status", "Request failed");
			errorResponse.addProperty("detail", exceptionMessage);
			return new ResponseEntity<JsonObject>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
	
	private ResponseEntity<JsonObject> getTweetsMetricsGeneric(JsonObject requestBody, String userId){
		
		try {
			
			JsonObject response = engagementService.getTweetsStats(requestBody, userId);
			return new ResponseEntity<JsonObject>(response, HttpStatus.OK);
			
		}catch(Exception e) {
			
			JsonObject errorResponse = new JsonObject();
			String exceptionMessage = e.getMessage();
			
			errorResponse.addProperty("status", "Request failed");
			errorResponse.addProperty("detail", exceptionMessage);
			return new ResponseEntity<JsonObject>(errorResponse, HttpStatus.BAD_REQUEST);
		}
	}
}
