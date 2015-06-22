package com.github.jreddit.request.reddit.request;

import com.github.jreddit.request.reddit.request.param.SubredditsView;

public class SubredditsGetRequest extends SubredditsRequest {
	
	/** Required field: view of the subreddits. */
	private SubredditsView view;
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/subreddits/%s.json?%s"; // ApiEndpointUtils.SUBREDDITS_GET
	
	/**
	 * Constructor.
	 * 
	 * @param view View of the subreddits
	 */
	public SubredditsGetRequest(SubredditsView view) {
		this.view = view;
	}
	
	@Override
	public String generateRedditURI() {
		return String.format(ENDPOINT_FORMAT, view, this.generateParameters());
	}
	
}
