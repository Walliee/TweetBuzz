package com.anshul.tweetbuzz;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import javax.servlet.http.*;

import twitter4j.Status;

import com.anshul.tweetbuzz.TweetWrapper;
import com.google.gson.Gson;

@SuppressWarnings("serial")
public class TweetBuzzServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Gson gson = new Gson();
		resp.setContentType("application/json");

		String keyword = req.getParameter("keyword");
		String hash = req.getParameter("hash");
		List<Status> tweets;
		if (keyword != null) {
			tweets = TwitterSearch.search(keyword);
		} else {
			hash = "#".concat(hash);
			tweets = TwitterSearch.search(hash);
		}

		List<TweetWrapper> tweetList = new ArrayList<TweetWrapper>();
		for (Status tweet : tweets) {
			TweetWrapper tweetWrapper = TweetWrapper.feel(tweet.getText());
			double latitude, longitude;
			if(tweet.getGeoLocation() != null) {
				latitude = tweet.getGeoLocation().getLatitude();
				longitude = tweet.getGeoLocation().getLongitude();
				tweetWrapper.setGeolocation(latitude, longitude);
			}
			
			tweetWrapper.setUser(tweet.getUser().getScreenName());
			tweetList.add(tweetWrapper);
		}

		resp.setContentType("application/json");
		String callback = req.getParameter("callback");

		if (callback != null) {
			resp.getWriter().print(callback + "(");
		}
		resp.getWriter().print(gson.toJson(tweetList));
		if (callback != null) {
			resp.getWriter().print(");");
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String text = req.getParameter("text");
		TweetWrapper emotions = TweetWrapper.feel(text);
		Gson gson = new Gson();

		resp.setContentType("application/json");
		resp.getWriter().println(gson.toJson(emotions));
	}

}