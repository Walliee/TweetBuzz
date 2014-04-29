package com.anshul.tweetbuzz;

import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterSearch {
	
	public static List<Status> search(String keyword) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setJSONStoreEnabled(true);
        cb.setOAuthConsumerKey("g7N0pJBIP8HEmqOhSQqBQ");
        cb.setOAuthConsumerSecret("SnZAT1FtjSFo9D8G1tXArc6fm7UzmLP3S80ZTnmAos");
        cb.setOAuthAccessToken("39796439-yMO7KWnvnouQHDP6r4jyqn2hsKiyVGo2rGaRy8vWa");
        cb.setOAuthAccessTokenSecret("dJdahl2e8xctmhgpIYKngB4D4XD24dHX7pcxU68kdjrPq");
        
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		Query query = new Query(keyword);
		query.setLang("en");
		int numberOfTweets = 300;
		long lastID = Long.MAX_VALUE;
		List<Status> tweets = new ArrayList<Status>();
		while (tweets.size () < numberOfTweets) {
		  if (numberOfTweets - tweets.size() > 100)
		    query.setCount(100);
		  else 
		    query.setCount(numberOfTweets - tweets.size());
		  try {
		    QueryResult result = twitter.search(query);
		    tweets.addAll(result.getTweets());
		    List<Status> rejects = new ArrayList<Status>();
		    for (Status t: tweets) {
		    	if(t.getInReplyToUserId()  != -1L || t.isRetweet() || t.getGeoLocation() != null) rejects.add(t);
		    	if(t.getId() < lastID) lastID = t.getId();
		    }
		    //remove replies, retweets and non-geotagged tweets from results
		    tweets.removeAll(rejects);
		  }
		  catch (TwitterException te) {
		    System.out.println("Couldn't connect: " + te);
		    break;
		  } 
		  query.setMaxId(lastID-1);
	
		}
		return tweets;
	}
	
	public static void main(String[] args) {
		List<Status> tweets = search("lol");
		for(Status tweet:tweets){
			System.out.println(tweet.getText());
		}
	}
}