package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONObject;

public abstract class RedditPatchRequest {

    
    /** Mapping of all request body parameters. */
    private Map<String, String> bodyParameters;    
    
    /**
     * Default constructor.
     */
    public RedditPatchRequest() {
        bodyParameters = new HashMap<String, String>();        
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
     * Generate the body parameters to be added.<br>
     * <br>
     * <i>Note: values are encoded.</i>
     * 
     * @return Body parameters (JSON object of all values)
     */
    protected String generateBodyParameters() {
        JSONObject obj = new JSONObject();
        for (Entry<String, String> entry : bodyParameters.entrySet()) {
        	obj.put(entry.getKey(), entry.getValue());
        }
        return obj.toString();
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
     * Generate the body for a PATCH request using the PATCH parameters.

     * @return Body (e.g. "limit=100&sort=top" for parameters limit: 100 and sort: "top")
     */
    public String generateBody() {
        return generateBodyParameters();
    }
    
}
