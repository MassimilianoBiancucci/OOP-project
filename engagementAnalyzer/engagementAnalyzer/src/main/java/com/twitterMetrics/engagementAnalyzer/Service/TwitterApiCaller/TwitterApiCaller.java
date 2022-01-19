package com.twitterMetrics.engagementAnalyzer.Service.TwitterApiCaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

public class TwitterApiCaller {
	
	private String token;
	
	// Debug constructor
	public TwitterApiCaller() throws IOException {
		// load the default API token from file
		// USED ONLY FOR DEBUG PURPOSES
		readDefaultBearerToken();
	}
	
	// principal constructor
	public TwitterApiCaller(String BearerToken) {
		// load the API token passed
		this.token = BearerToken;
	}
	
	// method that retrieve the tweets from the Twitter API with the tweets ids
	public JsonObject getTweetsFromIds(String[] tweetIds) throws IllegalArgumentException{
		
		if(!(tweetIds.length > 0)) throw new IllegalArgumentException("TwitterApiCaller.getTweetsFromIds() must have at least one tweet id as argument!");
		
		OkHttpClient client = new OkHttpClient();
		
		// prepare the ids for the query
		String requestedIds = "";
		for(int i = 0; i < tweetIds.length; i++) {
			if(i>0)requestedIds += ",";
			requestedIds += tweetIds[i];
		}
		
		// Building of the GET request with the call parameters needed for the request
		Request request = new Request.Builder()
			  .url("https://api.twitter.com/2/tweets?ids=" + requestedIds + "&tweet.fields=created_at,public_metrics")
			  .method("GET", null)
			  .addHeader("Authorization", "Bearer " + this.token)
			  .build();

		JsonObject jsonResponseBody = new JsonObject();
		
		// execution of the call with the handle of the responseBody
		try {
			
			Response response = client.newCall(request).execute(); // execute the twitter API call
			String responseBody = response.body().string(); // parse the response in String format
			jsonResponseBody = JsonParser.parseString(responseBody).getAsJsonObject(); // Parse the response from string in JsonObject format		
			
		} catch (IOException e) {
			// catch IO exception
			e.printStackTrace();
		} catch (JsonSyntaxException je) {
			// catch exception raised during the response body parsing from string to Gson object
			je.printStackTrace();
		}
		
		// return the body response as string
		return jsonResponseBody;

	}
	
	// method that retrieve the tweets from the user id
	public JsonObject getTweetsFromUserId(String userId){
		
		OkHttpClient client = new OkHttpClient();
		
		// Building of the GET request with the call parameters needed for the request
		Request request = new Request.Builder()
			  .url("https://api.twitter.com/2/users/" + userId + "/tweets?max_results=100&tweet.fields=created_at,public_metrics")
			  .method("GET", null)
			  .addHeader("Authorization", "Bearer " + this.token)
			  .build();

		JsonObject jsonResponseBody = new JsonObject();
		
		// execution of the call with the handle of the responseBody
		try {
			
			Response response = client.newCall(request).execute(); // execute the twitter API call
			String responseBody = response.body().string(); // parse the response in String format
			jsonResponseBody = JsonParser.parseString(responseBody).getAsJsonObject(); // Parse the response from string in JsonObject format		
			
		} catch (IOException e) {
			// catch IO exception
			e.printStackTrace();
		} catch (JsonSyntaxException je) {
			// catch exception raised during the response body parsing from string to Gson object
			je.printStackTrace();
		}
		
		// return the body response as string
		return jsonResponseBody;
	}
	
	
	// method that read the Bearer token for the API authentication 
	// from file and load it in the property of the class
	private void readDefaultBearerToken() throws IOException {
		
		try {
			
			// recover the stream of the file that contain the Bearer token
			InputStream stream = this.getClass().getResourceAsStream("key.txt");
			
			StringBuilder resultStringBuilder = new StringBuilder();
		    
			// create a buffered stream reader from the file
		    try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
		    	
		    	// read only the first line of the file that contain the token
		        String line = br.readLine();
		        
		        // if the stream is correctly readed and not empty it will be appended to the
		        // string builder that convert the character stream to a usable string
		        if (line != null) {
		            resultStringBuilder.append(line);
		        }
		    }
			
		    // in the end load the token in string format to the private property of the class
			this.token = resultStringBuilder.toString();
			
		}catch(IOException e) {
			throw new IOException("src/main/resources/key.txt file isn't present! Try to create it and put in the Bearer token of the Twitter API V2, then try again or consider to pass the token in the request.");
		}
	}
	
	
}
