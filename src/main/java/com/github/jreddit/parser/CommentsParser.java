package com.github.jreddit.parser;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Kind;

public class CommentsParser {


	private JSONParser jsonParser;
	
	public CommentsParser() {
		jsonParser = new JSONParser();
	}
	
	public List<Comment> parse(String jsonText) throws ParseException {
    	
    	// List of submissions
        List<Comment> comments = new LinkedList<Comment>();
        
        // Send request to reddit server via REST client
        Object response = jsonParser.parse(jsonText);

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
    protected void parseRecursive(List<Comment> comments, JSONObject object) throws RetrievalFailedException, RedditError {
    	assert comments != null : "List of comments must be instantiated.";
    	assert object != null : "JSON Object must be instantiated.";
    	
    	// Get the comments in an array
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
        
        // Iterate over the submission results
        JSONObject data;
        Comment comment;
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
