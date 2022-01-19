package com.twitterMetrics.engagementAnalyzer.Service;

public class RoutesMetadata {
	
	public static final String rawTweetsRequestMetadta = "{\"metadata\":{\"input\":[{\"field\":\"TwitterBearerToken\",\"type\":\"String\",\"description\":\"String that contain your OAuth 2.0 Bearer token issued from Twitter.\"},{\"field\":\"tweetIds\",\"type\":\"JsonArray\",\"description\":\"Array of ids of each tweet that should be returned in the response.\",\"content\":[\"int\"]},{\"field\":\"filters\",\"type\":\"JsonObject\",\"description\":\"Object that contain combinations of filters, follow the documentation for further details on filters.\",\"content\":[\"JsonObject\"]}],\"output\":[{\"field\":\"status\",\"type\":\"String\",\"description\":\"Message that explain the actual status of the request, normaly the value should be 'success'.\"},{\"field\":\"tweets_count\",\"type\":\"int\",\"description\":\"The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api.\"},{\"field\":\"tweets\",\"type\":\"JsonArray\",\"description\":\"An array of tweets, each JsonObject inside this array, is a tweet.\",\"contentTypes\":[\"JsonObject\"],\"content\":{\"obj1\":[{\"field\":\"id\",\"type\":\"String\",\"description\":\"tweet id.\"},{\"field\":\"text\",\"type\":\"String\",\"description\":\"tweet text content.\"},{\"field\":\"created_at\",\"type\":\"String\",\"description\":\"date of creation of the corresponding tweet.\"},{\"field\":\"engagement\",\"type\":\"double\",\"description\":\"function that return a general value of the engagement calculated by public_metrics values.\"},{\"field\":\"public_metrics\",\"type\":\"JsonObject\",\"description\":\"json object that contain all the metrics needed for the engagement anlysis: retweet_count, reply_count, like_count, quote_count.\",\"contentTypes\":[\"int\"],\"content\":[{\"field\":\"retweet_count\",\"type\":\"int\",\"description\":\"number of retweet associated to the corresponding tweet.\"},{\"field\":\"reply_count\",\"type\":\"int\",\"description\":\"Number of comments to the corresponding tweet.\"},{\"field\":\"like_count\",\"type\":\"int\",\"description\":\"number of likes associated to the corresponding tweet.\"},{\"field\":\"quote_count\",\"type\":\"int\",\"description\":\"Number of retweet with a quote by the user that retweeted that tweet.\"}]}]}},{\"field\":\"errors\",\"type\":\"JsonArray\",\"description\":\"An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details.\"}]}}";
	
	public static final String rawTweetsRequestByUserIdMetadta = "{\"metadata\":{\"input\":[{\"field\":\"TwitterBearerToken\",\"type\":\"String\",\"description\":\"String that contain your OAuth 2.0 Bearer token issued from Twitter.\"},{\"field\":\"filters\",\"type\":\"JsonObject\",\"description\":\"Object that contain combinations of filters, follow the documentation for further details on filters.\",\"content\":[\"JsonObject\"]}],\"output\":[{\"field\":\"status\",\"type\":\"String\",\"description\":\"Message that explain the actual status of the request, normaly the value should be 'success'.\"},{\"field\":\"tweets_count\",\"type\":\"int\",\"description\":\"The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api.\"},{\"field\":\"tweets\",\"type\":\"JsonArray\",\"description\":\"An array of tweets, each JsonObject inside this array, is a tweet.\",\"contentTypes\":[\"JsonObject\"],\"content\":{\"obj1\":[{\"field\":\"id\",\"type\":\"String\",\"description\":\"tweet id.\"},{\"field\":\"text\",\"type\":\"String\",\"description\":\"tweet text content.\"},{\"field\":\"created_at\",\"type\":\"String\",\"description\":\"date of creation of the corresponding tweet.\"},{\"field\":\"engagement\",\"type\":\"double\",\"description\":\"function that return a general value of the engagement calculated by public_metrics values.\"},{\"field\":\"public_metrics\",\"type\":\"JsonObject\",\"description\":\"json object that contain all the metrics needed for the engagement anlysis: retweet_count, reply_count, like_count, quote_count.\",\"contentTypes\":[\"int\"],\"content\":[{\"field\":\"retweet_count\",\"type\":\"int\",\"description\":\"number of retweet associated to the corresponding tweet.\"},{\"field\":\"reply_count\",\"type\":\"int\",\"description\":\"Number of comments to the corresponding tweet.\"},{\"field\":\"like_count\",\"type\":\"int\",\"description\":\"number of likes associated to the corresponding tweet.\"},{\"field\":\"quote_count\",\"type\":\"int\",\"description\":\"Number of retweet with a quote by the user that retweeted that tweet.\"}]}]}},{\"field\":\"errors\",\"type\":\"JsonArray\",\"description\":\"An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details.\"}]}}";
	
	public static final String tweetsStatisticsRequestMetadta = "{\"metadata\":{\"input\":[{\"field\":\"TwitterBearerToken\",\"type\":\"String\",\"description\":\"String that contain your OAuth 2.0 Bearer token issued from Twitter.\"},{\"field\":\"tweetIds\",\"type\":\"JsonArray\",\"description\":\"Array of ids of each tweet that should be returned in the response.\",\"content\":[\"int\"]},{\"field\":\"filters\",\"type\":\"JsonObject\",\"description\":\"Object that contain combinations of filters, follow the documentation for further details on filters.\",\"content\":[\"JsonObject\"]}],\"output\":[{\"field\":\"status\",\"type\":\"String\",\"description\":\"Message that explain the actual status of the request, normaly the value should be 'success'.\"},{\"field\":\"tweets_count\",\"type\":\"int\",\"description\":\"The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api.\"},{\"field\":\"tweetsMetrics\",\"type\":\"JsonObject\",\"description\":\"An array of tweets metrics, inside this object there are all the statistics on the engagement of the tweets requested.\",\"contentTypes\":[\"float\"],\"content\":[{\"field\":\"retweet_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of retweets associated to the passed tweets.\"},{\"field\":\"reply_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of comments of the passed tweets.\"},{\"field\":\"like_count_mean\",\"type\":\"float\",\"description\":\"Mean of likes associated to the passed tweets.\"},{\"field\":\"quote_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of quote of the passed tweets.\"},{\"field\":\"engagement_mean\",\"type\":\"float\",\"description\":\"Mean of the engaement metric associated to the passed tweets.\"},{\"field\":\"retweet_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of retweets associated to the passed tweets.\"},{\"field\":\"reply_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of comments of the passed tweets.\"},{\"field\":\"like_count_variance\",\"type\":\"float\",\"description\":\"Variance of likes associated to the passed tweets.\"},{\"field\":\"quote_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of quote of the passed tweets.\"},{\"field\":\"engagement_variance\",\"type\":\"float\",\"description\":\"Variance of the engaement metric associated to the passed tweets.\"}]},{\"field\":\"errors\",\"type\":\"JsonArray\",\"description\":\"An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details.\"}]}}";
	
	public static final String tweetsStatisticsRequestByUserIdMetadta = "{\"metadata\":{\"input\":[{\"field\":\"TwitterBearerToken\",\"type\":\"String\",\"description\":\"String that contain your OAuth 2.0 Bearer token issued from Twitter.\"},{\"field\":\"filters\",\"type\":\"JsonObject\",\"description\":\"Object that contain combinations of filters, follow the documentation for further details on filters.\",\"content\":[\"JsonObject\"]}],\"output\":[{\"field\":\"status\",\"type\":\"String\",\"description\":\"Message that explain the actual status of the request, normaly the value should be 'success'.\"},{\"field\":\"tweets_count\",\"type\":\"int\",\"description\":\"The number of tweets retrived before the filtering process, the maximum should be 100, but the retrived number depend to the twitter api.\"},{\"field\":\"tweetsMetrics\",\"type\":\"JsonObject\",\"description\":\"An array of tweets metrics, inside this object there are all the statistics on the engagement of the tweets requested.\",\"contentTypes\":[\"float\"],\"content\":[{\"field\":\"retweet_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of retweets associated to the passed tweets.\"},{\"field\":\"reply_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of comments of the passed tweets.\"},{\"field\":\"like_count_mean\",\"type\":\"float\",\"description\":\"Mean of likes associated to the passed tweets.\"},{\"field\":\"quote_count_mean\",\"type\":\"float\",\"description\":\"Mean of the number of quote of the passed tweets.\"},{\"field\":\"engagement_mean\",\"type\":\"float\",\"description\":\"Mean of the engaement metric associated to the passed tweets.\"},{\"field\":\"retweet_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of retweets associated to the passed tweets.\"},{\"field\":\"reply_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of comments of the passed tweets.\"},{\"field\":\"like_count_variance\",\"type\":\"float\",\"description\":\"Variance of likes associated to the passed tweets.\"},{\"field\":\"quote_count_variance\",\"type\":\"float\",\"description\":\"Variance of the number of quote of the passed tweets.\"},{\"field\":\"engagement_variance\",\"type\":\"float\",\"description\":\"Variance of the engaement metric associated to the passed tweets.\"}]},{\"field\":\"errors\",\"type\":\"JsonArray\",\"description\":\"An array of errors passed directly from the Twitter API, see the Twitter API V2 documentation for further details.\"}]}}";
}