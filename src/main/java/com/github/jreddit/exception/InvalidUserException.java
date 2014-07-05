package com.github.jreddit.exception;


/**
 * Thrown to indicate that invalid <code>User</code> credentials
 * have been used to refer to a Reddit <code>User</code>.
 *
 * @author Raul Rene Lepsa
 */
public class InvalidUserException extends Exception {

	private static final long serialVersionUID = 420565640310669470L;

	public InvalidUserException() {
        super();
    }

    public InvalidUserException(String message) {
        super(message);
    }
}
