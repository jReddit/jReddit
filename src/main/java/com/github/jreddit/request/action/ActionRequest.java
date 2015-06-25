package com.github.jreddit.request.action;

import com.github.jreddit.request.RedditPostRequest;

public abstract class ActionRequest extends RedditPostRequest {

	/**
	 * Action request.
	 * 
	 * @param fullname The fullname of the target (e.g. "t3_djkfsjka")
	 */
	public ActionRequest(String fullname) {
		this.addBodyParameter("id", fullname);
	}

}
