package com.github.jreddit.request.reddit.oauth;

import com.github.jreddit.request.reddit.request.RedditRequest;

public class RedditPoliteClient extends RedditClient {
	
	RedditClient redditClient;
	
	// TODO: add timing scheme
	public RedditPoliteClient(RedditClient redditClient) {
		this.redditClient = redditClient;
	}

	@Override
	public String post(RedditToken rToken, RedditRequest request) {
		return redditClient.post(rToken, request);
	}

	@Override
	public String get(RedditToken rToken, RedditRequest request) {
		return redditClient.get(rToken, request);
	}
}
