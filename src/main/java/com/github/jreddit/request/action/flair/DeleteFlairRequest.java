package com.github.jreddit.request.action.flair;

import com.github.jreddit.request.RedditPostRequest;

public class DeleteFlairRequest extends RedditPostRequest {
	
	/** Endpoint format. */
	private static final String ENDPOINT_FORMAT = "/api/deleteflair.json?";

	public DeleteFlairRequest(String username) {
		this.addBodyParameter("name", username);
	}
	
	@Override
	public String generateRedditURI() {
		return ENDPOINT_FORMAT;
	}
	
}
