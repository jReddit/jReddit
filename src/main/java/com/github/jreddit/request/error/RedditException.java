package com.github.jreddit.request.error;

public class RedditException extends Exception {

    private static final long serialVersionUID = -1031803118041533936L;

    public RedditException() {
        super("Request to reddit failed (null response)");
    }
    
    public RedditException(String custom) {
        super("Request to reddit failed (" + custom + ")");
    }
    
    public RedditException(int errorCode) {
        super("Request to reddit failed (HTTP error code: " + errorCode + ")");
    }
    
}
