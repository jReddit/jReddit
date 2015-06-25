package com.github.jreddit.request.action.mark;

import com.github.jreddit.request.action.MarkActionRequest;


public class UnsaveRequest extends MarkActionRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/api/unsave.json?";

	public UnsaveRequest(String fullname) {
		super(fullname);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
