package com.anshul.tweetbuzz;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.anshul.tweetbuzz.Twokenize;

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
		while (tweets.size() < numberOfTweets) {
			if (numberOfTweets - tweets.size() > 100)
				query.setCount(100);
			else
				query.setCount(numberOfTweets - tweets.size());
			try {
				QueryResult result = twitter.search(query);
				tweets.addAll(result.getTweets());
				List<Status> rejects = new ArrayList<Status>();
				for (Status t : tweets) {
					if (t.getInReplyToUserId() != -1L /*|| t.getGeoLocation() == null*/)
						rejects.add(t);
					if (t.getId() < lastID)
						lastID = t.getId();
				}
				// remove replies, retweets and non-geotagged tweets from
				// results
				tweets.removeAll(rejects);
			} catch (TwitterException te) {
				System.out.println("Couldn't connect: " + te);
				break;
			}
			query.setMaxId(lastID - 1);

		}
		return tweets;
	}

	public static void main(String[] args) throws IOException {
		FileWriter file = new FileWriter("/Users/Walliee/Documents/workspace2/tweet.txt");
		List<Status> tweets = search("Rob Ford");
		//List<String> t = new ArrayList<String>();
		for (Status tweet : tweets) {
			List<String> toks = Twokenize.tokenizeRawTweetText(tweet.getText());
    		for (int i=0; i<toks.size(); i++) {
    			file.write(toks.get(i));
    			if (i < toks.size()-1) {
    				file.write(" ");
    			}
    		}
    		file.write("\n");
			//t.add();
//			try {
//				file.write(tweet.getText() + "\n");
//			} catch (IOException e) {
//			
//				e.printStackTrace();
//			}
			//System.out.println(tweet.getText());
		}
		file.close();
		
	}
}