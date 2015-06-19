package com.github.jreddit.request.reddit.app;

public class RedditInstalledApp extends RedditApp {

	public RedditInstalledApp(String clientID, String redirectURI) {
		super(clientID, "", redirectURI); // Empty string is the secret for an installed app
	}
	
}
