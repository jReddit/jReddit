package com.github.jreddit.request.error;

public class RedditError extends Exception {

	private static final long serialVersionUID = -1031803118041533936L;

	public RedditError() {
		super("Request to reddit failed (null response)");
	}
	
	public RedditError(int errorCode) {
		super("Request to reddit failed (HTTP error code: " + errorCode + ")");
	}
	
}
