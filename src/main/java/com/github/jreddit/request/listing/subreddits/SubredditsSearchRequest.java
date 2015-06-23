package com.github.jreddit.request.listing.subreddits;

import com.github.jreddit.request.listing.ListingRequest;

public class SubredditsSearchRequest extends ListingRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/subreddits/search.json?%s";
	
	/**
	 * Constructor.
	 * 
	 * @param query Search query (e.g. "programming")
	 */
	public SubredditsSearchRequest(String query) {
		this.addParameter("q", query);
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, this.generateParameters());
	}
	
}
