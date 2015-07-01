package com.github.jreddit.parser.exception;

import org.json.simple.parser.ParseException;

public class RedditParseException extends Exception {

    private static final long serialVersionUID = -1031803118041533936L;
    
    public RedditParseException(String custom) {
        super("Could not parse response from reddit (" + custom + ")");
    }
    
    public RedditParseException(String custom, Throwable t) {
        super("Could not parse response from reddit (" + custom + ")", t);
    }
    
    public RedditParseException() {
        this("undefined (null) response");
    }
    
    public RedditParseException(int errorCode) {
        this("contained HTTP error code: " + errorCode);
    }
    
    public RedditParseException(ParseException pe) {
        this("invalid JSON format", pe);
    }
    
}
