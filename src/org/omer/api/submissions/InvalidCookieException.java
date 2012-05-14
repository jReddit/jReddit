package org.omer.api.submissions;

import java.io.IOException;

public class InvalidCookieException extends IOException {
	private static final long serialVersionUID = 8495419195363626351L;

	public InvalidCookieException() {
		super();
	}

	public InvalidCookieException(String exception) {
		super(exception);
	}
}