package com.anshul.tweetbuzz;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@SuppressWarnings("serial")
public class TweetTrendsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true);
			cb.setJSONStoreEnabled(true);
			cb.setOAuthConsumerKey("g7N0pJBIP8HEmqOhSQqBQ");
			cb.setOAuthConsumerSecret("SnZAT1FtjSFo9D8G1tXArc6fm7UzmLP3S80ZTnmAos");
			cb.setOAuthAccessToken("39796439-yMO7KWnvnouQHDP6r4jyqn2hsKiyVGo2rGaRy8vWa");
			cb.setOAuthAccessTokenSecret("dJdahl2e8xctmhgpIYKngB4D4XD24dHX7pcxU68kdjrPq");
			
			Gson gson = new Gson();
			resp.setContentType("application/json");
			
			Twitter twitter = new TwitterFactory(cb.build()).getInstance();
			Trends trends;
			trends = twitter.getPlaceTrends(2459115);
			String callback = req.getParameter("callback");

			if (callback != null) {
				resp.getWriter().print(callback + "(");
			}
			resp.getWriter().print(gson.toJson(trends));
			if (callback != null) {
				resp.getWriter().print(");");
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
        
	}
}