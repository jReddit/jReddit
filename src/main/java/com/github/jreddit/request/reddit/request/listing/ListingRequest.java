package com.github.jreddit.request.reddit.request.listing;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.request.reddit.request.RedditGetRequest;

public abstract class ListingRequest extends RedditGetRequest {

	/**
	 * 
	 * @param count
	 * @return This request
	 */
	public ListingRequest setCount(int count) {
		this.addParameter("count", String.valueOf(count));
		return this;
	}
	
	/**
	 * 
	 * @param limit
	 * @return This request
	 */
	public ListingRequest setLimit(int limit) {
		this.addParameter("limit", String.valueOf(limit));
		return this;
	}
	
	/**
	 * 
	 * @param after
	 * @return This request
	 */
	public ListingRequest setAfter(Subreddit after) {
		this.addParameter("after", after.getFullName());
		return this;
	}
	
	/**
	 * 
	 * @param before
	 * @return This request
	 */
	public ListingRequest setBefore(Subreddit before) {
		this.addParameter("before", before.getFullName());
		return this;
	}
	
}
