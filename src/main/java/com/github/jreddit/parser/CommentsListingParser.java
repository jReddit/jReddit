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

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Kind;
import com.github.jreddit.request.error.RedditError;

public class CommentsListingParser extends RedditListingParser {

	/** Logger for this class. */
	final static Logger LOGGER = LoggerFactory.getLogger(FullSubmissionParser.class);
	
	/** JSON parser. */
	private JSONParser jsonParser;
	
	/**
	 * Constructor.
	 */
	public CommentsListingParser() {
		jsonParser = new JSONParser();
	}
	
	/**
	 * Parse JSON received from reddit into a list of comments.
	 * This parser expects the JSON to be of a listing of comments.<br>
	 * <br>
	 * <i>Note: this parsing can only be performed on listings of comments, not on
	 * a comment tree of a submission.</i>
	 * 
	 * @param jsonText JSON Text
	 * @return Parsed list of comments
	 * 
	 * @throws ParseException
	 * @throws RedditError
	 */
	public List<Comment> parse(String jsonText) throws ParseException, RedditError {
    	
    	// List of submissions
        List<Comment> comments = new LinkedList<Comment>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        // Validate the response
        validate(response);

        // Cast to a JSON object
        JSONObject object = (JSONObject) response;
        
        // Get the array of children
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
	        
        // Iterate over the comment results
        for (Object element : array) {
        	
        	// Get the element
        	JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
			if (kind != null && kind.equals(Kind.COMMENT.value())) {

                // Contents of the comment
                data = ((JSONObject) data.get("data"));

                // Create and add the new comment
                Comment comment = new Comment(data);
                comments.add(comment);
                
			}
			
		}

        // Finally return list of comments 
        return comments;
        
	}
    
}
