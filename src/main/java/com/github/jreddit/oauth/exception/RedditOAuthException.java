package com.github.jreddit.oauth.exception;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

public class RedditOAuthException extends Exception {
    
    private static final long serialVersionUID = 2403104136770312353L;

    public RedditOAuthException(OAuthSystemException e) {
        super("A OAuth system exception was thrown when authenticating with reddit.", e);
    }
    
    public RedditOAuthException(OAuthProblemException e) {
        super("A OAuth problem exception was thrown when authenticating with reddit.", e);
    }
    
}
