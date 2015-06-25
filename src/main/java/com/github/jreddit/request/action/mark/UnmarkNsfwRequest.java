package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.ActionRequest;

public class UnmarkNsfwRequest extends ActionRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/unmarknsfw?";

	public UnmarkNsfwRequest(String fullname) {
		super(fullname);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
