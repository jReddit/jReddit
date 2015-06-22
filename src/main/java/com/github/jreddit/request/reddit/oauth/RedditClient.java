package com.github.jreddit.request.reddit.oauth;

import com.github.jreddit.request.reddit.request.RedditGetRequest;
import com.github.jreddit.request.reddit.request.RedditPostRequest;

public abstract class RedditClient {
	
	/** API Domain of OAuth */
	public static final String OAUTH_API_DOMAIN = "https://oauth.reddit.com";
	 
	/**
	 * Perform a POST reddit request using the given reddit token.
	 * 
	 * @param rToken Reddit token
	 * @param request Reddit POST request
	 * 
	 * @return Response from reddit (raw)
	 */
	public abstract String post(RedditToken rToken, RedditPostRequest request);
	
	/**
	 * Perform a GET reddit request using the given reddit token.
	 * 
	 * @param rToken Reddit token
	 * @param request Reddit GET request
	 * 
	 * @return Response from reddit (raw)
	 */
	public abstract String get(RedditToken rToken, RedditGetRequest request);
	
}
