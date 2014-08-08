package com.github.jreddit.exception;

import java.io.IOException;

/**
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class InvalidCookieException extends IOException {
	
    private static final long serialVersionUID = 8495419195363626351L;

    public InvalidCookieException() {
        super();
    }

    public InvalidCookieException(String exception) {
        super(exception);
    }
    
}