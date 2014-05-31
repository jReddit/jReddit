package com.github.jreddit.exception;

/**
 * Indicates that the requested URI path is invalid
 *
 * @author Raul Rene Lepsa
 */
public class InvalidURIException extends Exception {

    public InvalidURIException() {
        super();
    }

    public InvalidURIException(String exception) {
        super(exception);
    }
}
