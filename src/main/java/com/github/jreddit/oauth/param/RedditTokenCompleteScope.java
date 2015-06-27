package com.github.jreddit.oauth.param;

import java.util.HashSet;
import java.util.Set;

/**
 * Manager of the scopes a response from reddit returns.
 * Used by <i>RedditToken</i> to parse the list of scopes it receives.
 * 
 * @see RedditToken
 * 
 * @author Simon Kassing
 */
public class RedditTokenCompleteScope {

    /** Set of scopes. */
    private Set<String> scopes;
    
    /**
     * @param scopes List of scopes (e.g. "flair,edit")
     */
    public RedditTokenCompleteScope(String scopes) {
        
        // Create set
        this.scopes = new HashSet<String>();
        
        // Split up
        String[] split = scopes.split(RedditScope.SEPARATOR);
        
        // Add each to the set
        for (String s : split) {
            this.scopes.add(s);
        }
        
    }
    
    /**
     * Check whether it has this scope.
     * 
     * @param scope Reddit scope
     * 
     * @return Does it have this scope?
     */
    public boolean has(RedditScope scope) {
        return scopes.contains(scope.value());
    }
    
}
