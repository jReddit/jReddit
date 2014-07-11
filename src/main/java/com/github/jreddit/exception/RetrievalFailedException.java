package com.github.jreddit.exception;

public class RetrievalFailedException extends RuntimeException {

	private static final long serialVersionUID = 8702850928920928051L;
	
	public RetrievalFailedException(String message) {
        super(message);
    }
    
}
