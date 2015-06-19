package com.github.jreddit.request;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

import com.github.jreddit.request.reddit.app.RedditInstalledApp;
import com.github.jreddit.request.reddit.oauth.RedditOAuthAgent;
import com.github.jreddit.request.reddit.oauth.RedditToken;

public class Example {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException {

		// Information about the app
		String userAgent = "jReddit: Reddit API Wrapper for Java";
		String clientID = "PfnhLt3VahLrbg";
		String redirectURI = "http://www.google.com/";
		
		// Reddit application
		RedditInstalledApp redditApp = new RedditInstalledApp(clientID, redirectURI);
		
		// Create OAuth agent
		RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);	
		
		
		RedditToken token = agent.tokenAppOnly(false);
		

	}
	
}
