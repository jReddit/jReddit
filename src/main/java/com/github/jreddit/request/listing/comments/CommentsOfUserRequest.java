package com.github.jreddit.request.listing.comments;

import com.github.jreddit.request.listing.ListingRequest;
import com.github.jreddit.request.param.TimeSpan;
import com.github.jreddit.request.param.UserOverviewSort;

public class CommentsOfUserRequest extends ListingRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/user/%s/comments.json?%s";

	/** Name of the user. */
	String username;
	
	/**
	 * Constructor, with mandatory parameters for the request.
	 * 
	 * @param username Username (e.g. "jRedditBot")
	 */
	public CommentsOfUserRequest(String username) {
		this.username = username;
	}

	/**
	 * Set sorting method.
	 * 
	 * @param sort Sorting method for the user overview
	 * 
	 * @return This builder
	 */
	public CommentsOfUserRequest setSort(UserOverviewSort sort) {
		this.addParameter("sort", sort.value());
		return this;
	}
	
	public CommentsOfUserRequest setShowGiven() {
		this.addParameter("show", "given");
		return this;
	}

	public CommentsOfUserRequest setTime(TimeSpan time) {
		this.addParameter("t", time.value());
		return this;
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, username, this.generateParameters());
	}
	
}
