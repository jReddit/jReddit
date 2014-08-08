package com.github.jreddit.exception;

/**
 * Indicates that the requested URI path is invalid
 *
 * @author Raul Rene Lepsa
 */
public class InvalidURIException extends Exception {

	private static final long serialVersionUID = 2470850982592085993L;
	
	public InvalidURIException() {
        super();
    }

    public InvalidURIException(String exception) {
        super(exception);
    }
}
