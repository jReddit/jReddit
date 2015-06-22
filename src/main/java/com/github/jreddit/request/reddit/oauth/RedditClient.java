package com.github.jreddit.request.reddit.oauth;

import com.github.jreddit.request.reddit.request.RedditRequest;

public abstract class RedditClient {
	
	/** API Domain of OAuth */
	public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
	 
	public abstract String post(RedditToken rToken, RedditRequest request);
	public abstract String get(RedditToken rToken, RedditRequest request);
	
}
