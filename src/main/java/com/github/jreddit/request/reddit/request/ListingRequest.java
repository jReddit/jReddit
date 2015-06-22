package com.github.jreddit.request.reddit.request;

import com.github.jreddit.entity.Subreddit;

public abstract class ListingRequest extends RedditGetRequest {

	/**
	 * 
	 * @param count
	 * @return
	 */
	public ListingRequest setCount(int count) {
		this.addParameter("count", String.valueOf(count));
		return this;
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public ListingRequest setLimit(int limit) {
		this.addParameter("limit", String.valueOf(limit));
		return this;
	}
	
	/**
	 * 
	 * @param after
	 * @return
	 */
	public ListingRequest setAfter(Subreddit after) {
		this.addParameter("after", after.getFullName());
		return this;
	}
	
	/**
	 * 
	 * @param before
	 * @return
	 */
	public ListingRequest setBefore(Subreddit before) {
		this.addParameter("before", before.getFullName());
		return this;
	}
	
}
