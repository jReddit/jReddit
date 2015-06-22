package com.github.jreddit.request.reddit.request.listing;

import com.github.jreddit.request.reddit.request.param.SubmissionSort;

public class SubmissionsOfSubredditRequest extends ListingRequest {

	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/r/%s/%s.json?%s";
	
	/** Sorting. */
	SubmissionSort sort;
	
	/** Subreddit. */
	String subreddit;
	
	/**
	 * Constructor, with mandatory parameters for the request.
	 * 
	 * @param subreddit Subreddit (e.g. "funny")
	 * @param sort Sorting
	 */
	public SubmissionsOfSubredditRequest(String subreddit, SubmissionSort sort) {
		this.subreddit = subreddit;
		this.sort = sort;
	}
	
	public SubmissionsOfSubredditRequest setShowAll() {
		this.addParameter("show", "all");
		return this;
	}

	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, subreddit, sort.value(), this.generateParameters());
	}

}
