package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.ActionRequest;

public class UnhideRequest extends ActionRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/unhide?";

	public UnhideRequest(String fullname) {
		super(fullname);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
