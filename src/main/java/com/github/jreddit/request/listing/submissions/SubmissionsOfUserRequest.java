package com.github.jreddit.request.listing.submissions;

import com.github.jreddit.request.listing.ListingRequest;
import com.github.jreddit.request.param.UserOverviewSort;
import com.github.jreddit.request.param.UserSubmissionsCategory;

public class SubmissionsOfUserRequest extends ListingRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/user/%s/%s.json?%s";
	
	/** Category. */
	UserSubmissionsCategory category;
	
	/** Name of the user. */
	String username;
	
	/**
	 * Constructor, with mandatory parameters for the request.
	 * 
	 * @param subreddit Subreddit (e.g. "funny")
	 * @param sort Sorting
	 */
	public SubmissionsOfUserRequest(String username, UserSubmissionsCategory category) {
		this.username = username;
		this.category = category;
	}

	public SubmissionsOfUserRequest setSort(UserOverviewSort sort) {
		this.addParameter("sort", sort.value());
		return this;
	}
	
	public SubmissionsOfUserRequest setShowGiven() {
		this.addParameter("show", "given");
		return this;
	}

	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, username, category.value(), this.generateParameters());
	}
	
}
