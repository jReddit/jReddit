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
import com.github.jreddit.parser.entity.Subreddit;
import com.github.jreddit.request.error.RedditError;

public class SubredditsListingParser extends RedditListingParser {
	
	/** Logger for this class. */
	final static Logger LOGGER = LoggerFactory.getLogger(SubredditsListingParser.class);
	
	/** JSON parser. */
	private JSONParser jsonParser;
	
	/**
	 * Constructor.
	 */
	public SubredditsListingParser() {
		jsonParser = new JSONParser();
	}
	
	/**
	 * Parse JSON received from reddit into a list of subreddits.
	 * This parser expects the JSON to be of a listing of subreddits ('links').
	 * 
	 * @param jsonText JSON Text
	 * @return Parsed list of subreddits
	 * 
	 * @throws ParseException
	 */
	public List<Subreddit> parse(String jsonText) throws ParseException, RedditError {
		
    	// List of subreddits
        List<Subreddit> subreddits = new LinkedList<Subreddit>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        // Check for reddit error, can throw a RedditError
        validate(response);
        
        // Cast to JSON object
        JSONObject object = (JSONObject) response;
        
        // Get the array of children
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        // Iterate over the subreddit results
        for (Object element : array) {
            JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
			if (kind != null && kind.equals(Kind.SUBREDDIT.value())) {

                // Create and add subreddit
                data = ((JSONObject) data.get("data"));
                subreddits.add(new Subreddit(data));
                
			}
        }
        
        return subreddits;
        
	}
	
}
