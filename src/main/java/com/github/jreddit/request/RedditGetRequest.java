package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;

import com.github.jreddit.request.util.KeyValueFormatter;

public abstract class RedditGetRequest {

    /** Mapping of all request parameters. */
    private Map<String, String> parameters;
    
    /**
     * Default constructor.
     */
    public RedditGetRequest() {
        parameters = new HashMap<String, String>();
    } 
    
    /**
     * Add a parameter to the request.
     * If the key of the parameter already exists, the previous value will be overwritten.
     * 
     * @param key Key of the parameter (e.g. "limit")
     * @param value Value of the parameter (e.g. "100")
     */
    protected void addParameter(String key, String value) {
        parameters.put(key, value);
    }
    
    /**
     * Generate the query parameters to be added to an URL.
     * 
     * @return Parameters (e.g. "limit=100&sort=top")
     */
    protected String generateParameters() {
        return KeyValueFormatter.format(parameters, true);
    }
    
    /**
     * Generate the URI of the request.
     * Be sure to call {@link #generateParameters()} to add the parameters to the end of the URL.
     * 
     * @return Reddit Uniform Resource Identifier (e.g. "/usr/endpoint?limit=100&sort=top")
     */
    public abstract String generateRedditURI();
    
}
