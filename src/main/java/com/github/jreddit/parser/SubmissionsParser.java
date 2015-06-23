package com.github.jreddit.parser;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Kind;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.request.error.RedditError;

public class SubmissionsParser extends RedditParser {
	
	/** Logger for this class. */
	final static Logger LOGGER = LoggerFactory.getLogger(SubmissionsParser.class);
	
	/** JSON parser. */
	private JSONParser jsonParser;
	
	/**
	 * Constructor.
	 */
	public SubmissionsParser() {
		jsonParser = new JSONParser();
	}
	
	/**
	 * Parse JSON received from reddit into a list of submissions.
	 * This parser expects the JSON to be of a listing of submissions ('links').
	 * 
	 * @param jsonText JSON Text
	 * @return Parsed list of submissions
	 * 
	 * @throws ParseException
	 */
	public List<Submission> parse(String jsonText) throws ParseException, RedditError {
		
    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        // Cast to a JSON object
        JSONObject object = (JSONObject) response;
        
        // Check for reddit error, can throw a RedditError
        validate(object);
        
        // Get the array of children
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        // Iterate over array of children
        for (Object element : array) {
        	
        	// Get the element
        	JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
			if (kind != null && kind.equals(Kind.LINK.value())) {

				// Add submission
				submissions.add(new Submission(((JSONObject) data.get("data"))));
                
			}
			
		}

        // Finally return list of submissions 
        return submissions;
        
	}
	
}
