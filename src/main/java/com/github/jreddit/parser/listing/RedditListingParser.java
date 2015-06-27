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
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.util.JsonUtils;

public class RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionsListingParser.class);
    
    protected static final JSONParser JSON_PARSER = new JSONParser();
    
    /**
     * Validate that it is indeed the starting of a listing of reddit things.
     * 
     * @param response Object returned by JSON parser
     * 
     * @throws RedditRequestException If the response is not valid listing of reddit things
     */
    public void validate(Object response) throws RedditParseException {
        
        // Check for null
        if (response == null) {
            throw new RedditParseException();
        }
        
        // Check it is a JSON response
        if (!(response instanceof JSONObject)) {
            throw new RedditParseException("not a JSON response");
        }
        
        // Cast to JSON object
        JSONObject jsonResponse = ((JSONObject) response);
        
        // Check for error
        if (jsonResponse.get("error") != null) {
            throw new RedditParseException(JsonUtils.safeJsonToInteger(jsonResponse.get("error")));
        }
        
        // Check that data exists
        if (jsonResponse.get("data") == null) {
            System.out.println(jsonResponse.toJSONString());
            throw new RedditParseException("data is missing from listing");
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
    public List<Thing> parseGeneric(String jsonText) throws RedditParseException {
        return parseGeneric(jsonText, "children");
    }
    
    /**
     * Parse JSON received from reddit into a list of things.
     * This parser expects the JSON to be of a listing of things, and supports 
     * the following things: <i>Comment</i>, <i>Submission</i>, and <i>Subreddit</i>.<br>
     * <br>
     * <i>Note: if it encounters an invalid element (e.g. missing kind or data), it will
     * log a warning using SLF4J and would return null.</i>
     * 
     * @param jsonText JSON Text
     * @param listingName Name of the listing name within the data
     * 
     * @return Parsed list of things
     * 
     * @throws ParseException
     */
    public List<Thing> parseGeneric(String jsonText, String listingName) throws RedditParseException {
        
        try {
        
            // List of submissions
            List<Thing> things = new LinkedList<Thing>();
            
            // Send request to reddit server via REST client
            Object response = JSON_PARSER.parse(jsonText);
            
            // Check for reddit error, can throw a RedditError
            validate(response);
            
            // Cast to a JSON object
            JSONObject object = (JSONObject) response;
            
            // Get the array of children
            JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get(listingName);
    
            // Iterate over array of children
            for (Object element : array) {
                
                // Get the element
                JSONObject data = (JSONObject) element;
                
                // Make sure it is of the correct kind
                String kindData = safeJsonToString(data.get("kind"));
                Object objData = data.get("data");
                
                // If no kind is given
                if (kindData == null) {
                    LOGGER.warn("Kind data missing, skipping it.");
                    
                // If no data is given
                } else if (objData == null || !(objData instanceof JSONObject)) {
                    LOGGER.warn("Object data missing, skipping it.");
                    
                } else {
                    
                    // Attempt to match
                    Kind kind = Kind.match(kindData);
                    
                    // Parse the thing
                    Thing thing = parseThing(kind, ((JSONObject) data.get("data")));
                    
                    // Show warning if failed
                    if (thing == null) {
                        LOGGER.warn("Encountered invalid kind for a listing (" + kindData + "), skipping it.");
    
                    } else {
                        things.add(thing);
                    }
                    
                }
                
            }
            
            // Finally return list of submissions 
            return things;
        
        } catch (ParseException pe) {
            throw new RedditParseException(pe);
        }
        
    }
    
    /**
     * Parse the data into a thing if possible.
     * 
     * @param kind Kind of data
     * @param data Data for the thing
     * @return The thing generated from the data, if failed <i>null</i>
     */
    private Thing parseThing(Kind kind, JSONObject data) {
        
        // For a comment
        if (kind == Kind.COMMENT) { 
            return new Comment(data);
            
        // For a submission
        } else if (kind == Kind.LINK) {
            return new Submission(data);
        
        // For a subreddit
        } else if (kind == Kind.SUBREDDIT) { 
            return new Subreddit(data);
            
        // In all other cases (null, or of a different type)
        } else {
            return null;
        }
        
    }



}
