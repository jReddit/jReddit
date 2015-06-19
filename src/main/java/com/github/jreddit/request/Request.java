package com.github.jreddit.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Abstract class for requests.
 * 
 * @author Simon Kassing
 *
 */
public abstract class Request {

	/** Mapping of all request parameters. */
	private Map<String, String> parameters;
	
	/**
	 * Default constructor.
	 */
	public Request() {
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
	 * Generate the parameters to be added to an URL.
	 * 
	 * @return Parameters (e.g. "limit=100&sort=top")
	 */
	protected String generateParameters() {
		
		// Key set
		Set<String> keys = parameters.keySet();
		
		// Iterate over keys
		String paramsString = "";
		boolean start = true;
		for (String key : keys) {
			
			// Add separation ampersand
			if (!start) {
				paramsString = paramsString.concat("&");
			} else {
				start = false;
			}
			
			// Add key-value pair
			paramsString = paramsString.concat(key + "=" + parameters.get(key));
			
		}
		
		// Return final parameters
		return paramsString;
		
	}
	
	/**
	 * Generate the URL of the request.
	 * Be sure to call {@link #generateParameters()} to add the parameters to the end of the URL.
	 * 
	 * @return Uniform Resource Locator (e.g. "http://www.example.com/usr/endpoint?limit=100&sort=top")
	 */
	abstract public String generateURL();
	
}
