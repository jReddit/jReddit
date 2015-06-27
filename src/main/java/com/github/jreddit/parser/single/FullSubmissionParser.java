package com.github.jreddit.parser.single;

import static com.github.jreddit.parser.util.JsonUtils.safeJsonToString;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Kind;
import com.github.jreddit.parser.entity.More;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.entity.imaginary.FullSubmission;
import com.github.jreddit.parser.util.JsonUtils;
import com.github.jreddit.request.error.RedditException;

public class FullSubmissionParser {

    /** JSON parser. */
    private JSONParser jsonParser;
    
    /**
     * Constructor.
     */
    public FullSubmissionParser() {
        jsonParser = new JSONParser();
    }    
    
    /**
     * Parse JSON received from reddit into a full submission.
     * A full submissions means it has both (a) the submission, and (b) the comment tree.
     * 
     * @param jsonText JSON Text
     * @return Full submission
     * 
     * @throws ParseException
     */
    public FullSubmission parse(String jsonText) throws ParseException, RedditException {
        
        // Parse JSON text
        Object response = jsonParser.parse(jsonText);
        
        // Validate response
        validate(response);

        // Create submission (casting with JSON is horrible)
        JSONObject main = (JSONObject) ((JSONArray) response).get(0);
        Submission submission = new Submission((JSONObject) ((JSONObject) ((JSONArray)((JSONObject) main.get("data")).get("children")).get(0)).get("data"));
         
        // Create comment tree
        JSONObject mainTree =  (JSONObject) ((JSONArray) response).get(1);
        List<CommentTreeElement> commentTree = parseRecursive(mainTree);
        
        // Return the set of submission and its comment tree
        return new FullSubmission(submission,  commentTree);
        
    }
    
    /**
     * Parse a JSON object consisting of comments and add them
     * to the already existing list of comments. This does NOT create
     * a new comment list.
     * 
     * @param comments     List of comments
     * @param object    JSON Object
     */
    protected List<CommentTreeElement> parseRecursive(JSONObject main) throws RedditException {

        List<CommentTreeElement> commentTree = new ArrayList<CommentTreeElement>();
        
        // Iterate over the comment tree results
        JSONArray array = (JSONArray) ((JSONObject) main.get("data")).get("children");
        for (Object element : array) {
            
            // Get the element
            JSONObject data = (JSONObject) element;
            
            // Make sure it is of the correct kind
            String kind = safeJsonToString(data.get("kind"));
            
            // If it is a comment
            if (kind != null && kind.equals(Kind.COMMENT.value())) {
                
                // Create comment
                Comment comment = new Comment( (JSONObject) data.get("data") );
                
                // Retrieve replies
                Object replies = ((JSONObject) data.get("data")).get("replies");
                
                // If it is an JSON object
                if (replies instanceof JSONObject) {
                    comment.setReplies(parseRecursive( (JSONObject) replies ));
                    
                // If there are no replies, end with an empty one
                } else {
                    comment.setReplies(new ArrayList<CommentTreeElement>());
                }
                
                // Add comment to the tree
                commentTree.add(comment);
            }
            
            // If it is a more
            if (kind != null && kind.equals(Kind.MORE.value())) {
                
                // Add to comment tree
                commentTree.add(new More((JSONObject) data.get("data")));
                
            }
            
        }
        
        return commentTree;
        
    }
    
    /**
     * Validate that it is in fact a full submission.
     * 
     * @param response Object from the JSON parser
     * 
     * @throws RedditException If the JSON is in incorrect format
     */
    public void validate(Object response) throws RedditException {
        
        // Check for null
        if (response == null) {
            throw new RedditException();
        }
        
        // Check it is a JSON response
        if (response instanceof JSONObject) {

            // Cast to JSON object
            JSONObject jsonResponse = (JSONObject) response;
            
            // Check for error
            if (jsonResponse.get("error") != null) {
                throw new RedditException(JsonUtils.safeJsonToInteger(jsonResponse.get("error")));
            } else {
                throw new RedditException("invalid json format, started with object (should start with array)");
            }
        
        }
        
        // It must start with an array
        if (!(response instanceof JSONArray)) {
            throw new RedditException("invalid json format, did not start with array");
        }
        
    }
    
}
