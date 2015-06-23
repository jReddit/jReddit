package com.github.jreddit.parser;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.parser.util.JsonUtils;
import com.github.jreddit.request.error.RedditError;

public class RedditListingParser {
	
	public void validate(Object response) throws RedditError {
		
		// Check for null
		if (response == null) {
			throw new RedditError();
		}
		
		// Check it is a JSON response
		if (!(response instanceof JSONObject)) {
			throw new RedditError("not a JSON response");
		}
		
		// Cast to JSON object
		JSONObject jsonResponse = ((JSONObject) response);
		
		// Check for error
        if (jsonResponse.get("error") != null) {
        	throw new RedditError(JsonUtils.safeJsonToInteger(jsonResponse.get("error")));
        }
        
	}

}
