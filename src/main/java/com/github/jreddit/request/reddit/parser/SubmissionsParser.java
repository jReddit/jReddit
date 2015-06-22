package com.github.jreddit.request.reddit.parser;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Kind;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.exception.RedditError;

public class SubmissionsParser {

	private JSONParser jsonParser;
	
	public SubmissionsParser() {
		jsonParser = new JSONParser();
	}
	
	public List<Submission> parse(String jsonText) throws ParseException {
    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        if (response instanceof JSONObject) {
        	
	        JSONObject object = (JSONObject) response;
	        if (object.get("error") != null) {
	        	throw new RedditError("Response contained error code " + object.get("error") + ".");
	        }
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

	        // Iterate over the submission results
	        JSONObject data;
	        Submission submission;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            
	            // Make sure it is of the correct kind
	            String kind = safeJsonToString(data.get("kind"));
				if (kind != null) {
					if (kind.equals(Kind.LINK.value())) {

                        // Create and add submission
                        data = ((JSONObject) data.get("data"));
                        submission = new Submission(data);
                        // TODO: What is this: submission.setUser(user);
                        submissions.add(submission);
                    }
				}
			}
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of submissions 
        return submissions;
        
	}
	
}
