package com.github.jreddit.parser;

import org.json.simple.JSONObject;

import com.github.jreddit.parser.util.JsonUtils;
import com.github.jreddit.request.error.RedditError;

public class RedditParser {
	
	public void validate(JSONObject response) throws RedditError {
		
		if (response == null) {
			throw new RedditError();
		}
		
        if (response.get("error") != null) {
        	throw new RedditError(JsonUtils.safeJsonToInteger(response.get("error")));
        }
        
	}

}
