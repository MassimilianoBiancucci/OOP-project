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
import com.twitterMetrics.engagementAnalyzer.Service.EngagementService;

@RestController
public class EngagementController {
	
	@Autowired()
	EngagementService engagementService;
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR RAW TWEETS BY TWEETS IDS///////////////////////////////////////////
	
	// route that return raw tweets in json format with 
	@RequestMapping(value = "/tweets", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweets(@RequestBody JsonObject requestBody){
		
		engagementService.getRawTweetsData(requestBody);
		
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method that return the metadata request for the /tweets route
	@RequestMapping(value = "/tweets/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweetsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	

	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR RAW TWEETS BY USER ID//////////////////////////////////////////////
	
	// route that return raw tweets for certain user
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserTweets(@PathVariable("userId") int userId, @RequestBody String requestBody){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method that return the metadata request for the /user/{userId} route
	@RequestMapping(value = "/user/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserTweetsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY TWEETS IDS///////////////////////////////////////
	
	// route that return engagement statistics based on passed tweets
	@RequestMapping(value = "/tweets/metrics", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweetsMetrics(@RequestBody String requestBody){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method that return the metadata request of the /tweets/metrics route
	@RequestMapping(value = "/tweets/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getTweetsMetricsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
		
	///////////////////////////////////////////////////////////////////////////////////
	// REQUESTS FOR TWEETS METRICS BY USER ID /////////////////////////////////////////
	
	// route that return engagement statistics based on tweets of specified user
	@RequestMapping(value = "/user/{userId}/metrics", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserTweetsMetrics(@PathVariable("userId") int userId, @RequestBody String requestBody){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method that return the metadata request for the /user/{userId}/metrics route
	@RequestMapping(value = "/user/metrics/metadata", method = RequestMethod.GET)
	public ResponseEntity<Object> getUserTweetsMetricsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
}
