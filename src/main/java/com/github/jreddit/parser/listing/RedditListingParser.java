package com.github.jreddit.parser.listing;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Kind;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.Subreddit;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.util.JsonUtils;
import com.github.jreddit.request.error.RedditError;

public class RedditListingParser {
	
	/** Logger for this class. */
	final static Logger LOGGER = LoggerFactory.getLogger(SubmissionsListingParser.class);
	
	/** JSON parser. */
	protected JSONParser jsonParser;
	
	/**
	 * Constructor.
	 */
	public RedditListingParser() {
		jsonParser = new JSONParser();
	}
	
	/**
	 * Validate that it is indeed the starting of a listing of reddit things.
	 * 
	 * @param response Object returned by JSON parser
	 * 
	 * @throws RedditError If the response is not valid listing of reddit things
	 */
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
        
		// Check that data exists
        if (jsonResponse.get("data") == null) {
        	System.out.println(jsonResponse.toJSONString());
        	throw new RedditError("data is missing");
        }
        
	}
	
	/**
	 * Parse JSON received from reddit into a list of things.
	 * This parser expects the JSON to be of a listing of things, and supports 
	 * the following things: <i>Comment</i>, <i>Submission</i>, and <i>Subreddit</i>.
	 * 
	 * @param jsonText JSON Text
	 * @return Parsed list of things
	 * 
	 * @throws ParseException
	 */
	public List<Thing> parseGeneric(String jsonText) throws ParseException, RedditError {
		return parseGeneric(jsonText, "children");
	}
	
	/**
	 * Parse JSON received from reddit into a list of things.
	 * This parser expects the JSON to be of a listing of things, and supports 
	 * the following things: <i>Comment</i>, <i>Submission</i>, and <i>Subreddit</i>.
	 * 
	 * @param jsonText JSON Text
	 * @param listingName Name of the listing name within the data
	 * 
	 * @return Parsed list of things
	 * 
	 * @throws ParseException
	 */
	public List<Thing> parseGeneric(String jsonText, String listingName) throws ParseException, RedditError {
		
    	// List of submissions
        List<Thing> things = new LinkedList<Thing>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        // Check for reddit error, can throw a RedditError
        validate(response);
        
        // Cast to a JSON object
        JSONObject object = (JSONObject) response;
        
        // Get the array of children'
        System.out.println(object.toJSONString());
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get(listingName);

        // Iterate over array of children
        for (Object element : array) {
        	
        	// Get the element
        	JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
            
            // If no kind is given
            if (kind == null) {
            	LOGGER.warn("Could not detect kind in a reddit listing element, skipping it.");
            	
            // For a comment
            } else if (kind.equals(Kind.COMMENT.value())) { 
            	things.add(new Comment(((JSONObject) data.get("data"))));
            
            // For a submission
            } else if (kind.equals(Kind.LINK.value())) {
            	things.add(new Submission(((JSONObject) data.get("data"))));
            
            // For a subreddit
            } else if (kind.equals(Kind.SUBREDDIT.value())) { 
            	things.add(new Subreddit(((JSONObject) data.get("data"))));
            }
			
		}

        // Finally return list of submissions 
        return things;
        
	}

}
