package com.github.jreddit.request;

import com.github.jreddit.retrieval.params.SubredditsView;

public class SubredditsGetRequest extends SubredditsRequest {
	
	/** Required field: view of the subreddits. */
	private SubredditsView view;
	
	/** Endpoint format. */
	private static final String endpointFormat = "/subreddits/%s.json?%s"; // ApiEndpointUtils.SUBREDDITS_GET
	
	/**
	 * Constructor.
	 * 
	 * @param view View of the subreddits
	 */
	public SubredditsGetRequest(SubredditsView view) {
		this.view = view;
	}
	
	@Override
	public String generateURL() {
		return String.format(endpointFormat, view, this.generateParameters());
	}
	
}
