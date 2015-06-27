package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.request.error.RedditException;

public class CommentsMoreParser extends RedditListingParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsMoreParser.class);
    
    private static final JSONParser JSON_PARSER = new JSONParser();
    
    
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
     * @throws RedditException
     */
    public List<Comment> parse(String jsonText) throws ParseException, RedditException {
        
        // Move to the main object
        JSONObject main = ((JSONObject) ((JSONObject) JSON_PARSER.parse(jsonText)).get("json"));
        
        // List of comment and submission mixed elements
        List<Comment> comments = new LinkedList<Comment>();

        // If the main has data (it can happen that it does not, when no comments identifiers were passed along)
        if (main.get("data") != null) {
        
            // Parse to a list of things
            List<Thing> things = this.parseGeneric(main.toJSONString(), "things");
        
            // Iterate over things
            for (Thing t : things) {
                
                if (t instanceof Comment) {
                    comments.add((Comment) t);
                } else {
                    LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
                }
                
            }
        
        }
        
        // Return resulting comments list
        return comments;
        
    }
    
}
