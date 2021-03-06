package com.anshul.tweetbuzz;

import java.io.IOException;
import javax.servlet.http.*;

import com.google.gson.Gson;

@SuppressWarnings("serial")
public class TestServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Gson gson = new Gson();
		resp.setContentType("application/json");

		String text = req.getParameter("text");
		TweetWrapper emotions = TweetWrapper.feel(text);

		resp.setContentType("application/json");
		String callback = req.getParameter("callback");

		if (callback != null) {
			resp.getWriter().print(callback + "(");
		}
		resp.getWriter().print(gson.toJson(emotions));
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
