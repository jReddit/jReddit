package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.ActionRequest;

public class MarkNsfwRequest extends ActionRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/marknsfw?";

	public MarkNsfwRequest(String fullname) {
		super(fullname);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
