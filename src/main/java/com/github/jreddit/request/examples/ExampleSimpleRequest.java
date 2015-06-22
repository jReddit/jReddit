package com.github.jreddit.request.examples;

import java.util.List;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.request.reddit.app.RedditApp;
import com.github.jreddit.request.reddit.app.RedditInstalledApp;
import com.github.jreddit.request.reddit.oauth.RedditClient;
import com.github.jreddit.request.reddit.oauth.RedditHttpClient;
import com.github.jreddit.request.reddit.oauth.RedditOAuthAgent;
import com.github.jreddit.request.reddit.oauth.RedditToken;
import com.github.jreddit.request.reddit.parser.SubredditsParser;
import com.github.jreddit.request.reddit.request.SubredditsGetRequest;
import com.github.jreddit.request.reddit.request.param.SubredditsView;

public class ExampleSimpleRequest {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException, ParseException {

		// Information about the app
		String userAgent = "jReddit: Reddit API Wrapper for Java";
		String clientID = "PfnhLt3VahLrbg";
		String redirectURI = "https://github.com/snkas/jReddit";
		
		// Reddit application
		RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
		
		// Create OAuth agent
		RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);	
		
		// Create request executor 
		RedditClient client = new RedditHttpClient(HttpClientBuilder.create().build());
		
		// Create token (will be valid for 1 hour)
		RedditToken token = agent.tokenAppOnly(false);

		// Create parser for request
		SubredditsParser parser = new SubredditsParser();
		
		// Create the request
		SubredditsGetRequest request = (SubredditsGetRequest) new SubredditsGetRequest(SubredditsView.NEW).setLimit(100);

		// Perform and parse request, and store parsed result
		List<Subreddit> subreddits = parser.parse(client.get(token, request));
		
		// Now print out the result (don't care about formatting)
		System.out.println(subreddits);

	}
	
}
