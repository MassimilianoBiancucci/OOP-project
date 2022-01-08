package com.twitterMetrics.engagementAnalyzer.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.twitterMetrics.engagementAnalayzer.Service.EngagementService;

@RestController
public class EngagementController {
	
	@Autowired()
	EngagementService engagementService;
	
	
	// route that return raw tweets in json format with 
	@RequestMapping(value = "/tweets", method = RequestMethod.POST)
	public ResponseEntity<Object> getTweets(@RequestBody String requestParameters){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method for the metadata request of the /tweets route
	@RequestMapping(value = "/tweets/metadata")
	public ResponseEntity<Object> getTweetsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
	// route that return raw tweets for certain user
	@RequestMapping(value = "/user/{userId}", method = RequestMethod.POST)
	public ResponseEntity<Object> getUserTweets(@PathVariable("userId") int userId, @RequestBody String requestParameters){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method for the metadata request of the /user/{userId} route
	@RequestMapping(value = "/user/metadata")
	public ResponseEntity<Object> getUserTweetsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
	// route that return engagement statistics based on passed tweets
	@RequestMapping(value = "/tweets/metrics", method = RequestMethod.POST)
	public ResponseEntity<Object> getTweetsMetrics(@RequestBody String requestParameters){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method for the metadata request of the /tweets/metrics route
	@RequestMapping(value = "/tweets/metrics/metadata")
	public ResponseEntity<Object> getTweetsMetricsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	
	// route that return engagement statistics based on tweets of specified user
	@RequestMapping(value = "/user/{userId}/metrics", method = RequestMethod.POST)
	public ResponseEntity<Object> getUserTweetsMetrics(@PathVariable("userId") int userId, @RequestBody String requestParameters){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
	// method for the metadata request of the /user/{userId}/metrics route
	@RequestMapping(value = "/user/metrics/metadata")
	public ResponseEntity<Object> getUserTweetsMetricsMetadata(){
		// TODO implement
		return new ResponseEntity<>("success", HttpStatus.OK);
	}
	
}
