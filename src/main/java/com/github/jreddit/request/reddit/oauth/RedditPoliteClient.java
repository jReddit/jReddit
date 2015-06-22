package com.github.jreddit.request.reddit.oauth;

import com.github.jreddit.request.reddit.request.RedditGetRequest;
import com.github.jreddit.request.reddit.request.RedditPostRequest;

public class RedditPoliteClient extends RedditClient {
	
	RedditClient redditClient;
	
	// TODO: add timing scheme
	public RedditPoliteClient(RedditClient redditClient) {
		this.redditClient = redditClient;
	}

	@Override
	public String post(RedditToken rToken, RedditPostRequest request) {
		return redditClient.post(rToken, request);
	}

	@Override
	public String get(RedditToken rToken, RedditGetRequest request) {
		return redditClient.get(rToken, request);
	}
}
