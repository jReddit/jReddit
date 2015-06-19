package com.github.jreddit.request;

import com.github.jreddit.entity.Subreddit;

public abstract class SubredditsRequest extends Request {

	/**
	 * 
	 * @param count
	 * @return
	 */
	public SubredditsRequest setCount(int count) {
		this.addParameter("count", String.valueOf(count));
		return this;
	}
	
	/**
	 * 
	 * @param limit
	 * @return
	 */
	public SubredditsRequest setLimit(int limit) {
		this.addParameter("limit", String.valueOf(limit));
		return this;
	}
	
	/**
	 * 
	 * @param after
	 * @return
	 */
	public SubredditsRequest setAfter(Subreddit after) {
		this.addParameter("after", after.getFullName());
		return this;
	}
	
	/**
	 * 
	 * @param before
	 * @return
	 */
	public SubredditsRequest setBefore(Subreddit before) {
		this.addParameter("before", before.getFullName());
		return this;
	}
	
}
