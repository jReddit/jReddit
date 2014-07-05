package com.github.jreddit.exception;

public class ActionFailedException extends RuntimeException {

	private static final long serialVersionUID = 1295898598181005029L;
	
	public ActionFailedException(String message) {
        super(message);
    }
    
}
