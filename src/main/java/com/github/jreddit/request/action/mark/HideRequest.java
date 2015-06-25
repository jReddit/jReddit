package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.ActionRequest;

public class HideRequest extends ActionRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/hide?";

	public HideRequest(String fullname) {
		super(fullname);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
