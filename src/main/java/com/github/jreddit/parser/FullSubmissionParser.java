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
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.request.error.RedditError;

public class FullSubmissionParser extends RedditListingParser {
	// TODO: Refactor this

	/** Logger for this class. */
	final static Logger LOGGER = LoggerFactory.getLogger(FullSubmissionParser.class);
	
	/** JSON parser. */
	private JSONParser jsonParser;
	
	/**
	 * Constructor.
	 */
	public FullSubmissionParser() {
		jsonParser = new JSONParser();
	}
	
	public List<Comment> parseSubmission(String jsonText) throws ParseException, RedditError {
		// TODO: Create submission
	}
    	
	
	/**
	 * Parse JSON received from reddit into a tree of comments.
	 * This parser expects the JSON to be of a listing of submissions ('links').
	 * 
	 * @param jsonText JSON Text
	 * @return Parsed list of submissions
	 * 
	 * @throws ParseException
	 */
	public List<Comment> parseCommentList(String jsonText) throws ParseException, RedditError {
    	
    	// List of submissions
        List<CommentTreeElement> comments = new LinkedList<CommentTreeElement>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);
        
        
        validate(response);

        if (response instanceof JSONArray) {
        	
        	JSONObject object =  (JSONObject) ((JSONArray) response).get(1);
        	parseRecursive(comments, object);
	        
        } else {
        	throw new IllegalArgumentException("Parsing failed because JSON input is not from a submission.");
        }
        
        return comments;
	}
	
    /**
     * Parse a JSON object consisting of comments and add them
     * to the already existing list of comments. This does NOT create
     * a new comment list.
     * 
     * @param comments 	List of comments
     * @param object	JSON Object
     */
    protected void parseRecursive(List<CommentTreeElement> comments, JSONObject object) throws RedditError {
    	assert comments != null : "List of comments must be instantiated.";
    	assert object != null : "JSON Object must be instantiated.";
    	
    	// Get the comments in an array
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
        
        // Iterate over the submission results
        JSONObject data;
        CommentTreeElement comment;
        for (Object anArray : array) {
            data = (JSONObject) anArray;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
			if (kind != null) {
				if (kind.equals(Kind.COMMENT.value())) {

                    // Contents of the comment
                    data = ((JSONObject) data.get("data"));

                    // Create and add the new comment
                    comment = new Comment(data);
                    comments.add(comment);

                    Object o = data.get("replies");
                    if (o instanceof JSONObject) {

                        // Dig towards the replies
                        JSONObject replies = (JSONObject) o;
                        parseRecursive(comment.getReplies(), replies);

                    }

                } else if (kind.equals(Kind.MORE.value())) {
                	// TODO: create MORE endpoint for the comment tree
                    //data = (JSONObject) data.get("data");
                    //JSONArray children = (JSONArray) data.get("children");
                    //System.out.println("\t+ More children: " + children);

                }
			}

		}
        
    }
    
}
