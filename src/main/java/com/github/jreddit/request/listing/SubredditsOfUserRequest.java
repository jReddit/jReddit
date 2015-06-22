package com.github.jreddit.request.listing;

import com.github.jreddit.request.param.SubredditsView;

public class SubredditsOfUserRequest extends ListingRequest {
	
	/** Required field: view of the subreddits. */
	private SubredditsView view;
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/subreddits/%s.json?%s"; // ApiEndpointUtils.SUBREDDITS_GET
	
	/**
	 * Constructor.
	 * 
	 * @param view View of the subreddits
	 */
	public SubredditsOfUserRequest(SubredditsView view) {
		this.view = view;
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, view, this.generateParameters());
	}
	
	public SubredditsOfUserRequest setShowAll() {
		this.addParameter("show", "all");
		return this;
	}
	
}
