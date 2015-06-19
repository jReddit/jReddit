package com.github.jreddit.request.reddit.oauth;

public abstract class Executor {
	public abstract String execute(RedditToken rToken, String endpoint);
}
