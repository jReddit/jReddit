package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;

import com.github.jreddit.request.util.KeyValueFormatter;

public abstract class RedditPostRequest {

    /** Mapping of all request query parameters. */
    private Map<String, String> queryParameters;
    
    /** Mapping of all request body parameters. */
    private Map<String, String> bodyParameters;    
    
    /**
     * Default constructor.
     */
    public RedditPostRequest() {
        queryParameters = new HashMap<String, String>();
        bodyParameters = new HashMap<String, String>();        
    }
    
    /**
     * Add a parameter to the query of the request.
     * If the key of the parameter already exists, the previous value will be overwritten.
     * 
     * @param key Key of the parameter (e.g. "limit")
     * @param value Value of the parameter (e.g. "100")
     */
    protected void addQueryParameter(String key, String value) {
        queryParameters.put(key, value);
    }
    
    /**
     * Add a parameter to the body of the request.
     * If the key of the parameter already exists, the previous value will be overwritten.
     * 
     * @param key Key of the parameter (e.g. "id")
     * @param value Value of the parameter (e.g. "dajkjsf8")
     */
    protected void addBodyParameter(String key, String value) {
        bodyParameters.put(key, value);
    }
    
    /**
     * Generate the query parameters to be added to an URI.<br>
     * <br>
     * <i>Note: values are encoded.</i>
     * 
     * @return Query parameters (e.g. "limit=100&sort=top")
     */
    protected String generateQueryParameters() {
        return KeyValueFormatter.format(queryParameters, true);
    }
    
    /**
     * Generate the body parameters to be added.<br>
     * <br>
     * <i>Note: values are encoded.</i>
     * 
     * @return Body parameters (e.g. "limit=100&sort=top")
     */
    protected String generateBodyParameters() {
        return KeyValueFormatter.format(bodyParameters, true);
    }
    
    /**
     * Generate the URI of the request.
     * Be sure to call {@link #generateQueryParameters()} in your implementation
     * to add the parameters to the end of the URL.
     * 
     * @return Reddit Uniform Resource Identifier (e.g. "/usr/endpoint?limit=100&sort=top")
     */
    public abstract String generateRedditURI();
    
    /**
     * Generate the body for a POST request using the POST parameters.

     * @return Body (e.g. "limit=100&sort=top" for parameters limit: 100 and sort: "top")
     */
    public String generateBody() {
        return generateBodyParameters();
    }
    
}
