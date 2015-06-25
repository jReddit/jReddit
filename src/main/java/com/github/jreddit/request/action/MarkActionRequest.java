package com.github.jreddit.request.action;

import com.github.jreddit.request.RedditPostRequest;

public abstract class MarkActionRequest extends RedditPostRequest {

	/**
	 * Action request.
	 * 
	 * @param fullname The fullname of the target (e.g. "t3_djkfsjka")
	 */
	public MarkActionRequest(String fullname) {
		this.addBodyParameter("id", fullname);
	}

}
