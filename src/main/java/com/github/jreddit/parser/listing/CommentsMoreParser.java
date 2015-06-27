package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.More;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.exception.RedditParseException;

public class CommentsMoreParser extends RedditListingParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsMoreParser.class);
    
    private static final JSONParser JSON_PARSER = new JSONParser();
    
    
    /**
     * Parse JSON received from reddit into a list of new additional comment tree elements.
     * This parser expects the JSON to be of a listing of comments and more's.<br>
     * <br>
     * <i>Note: this parsing can only be performed on listings of comments and more's, not on
     * a comment tree of a submission.</i>
     * 
     * @param jsonText JSON Text
     * @return Parsed list of comments
     * 
     * @throws ParseException
     * @throws RedditRequestException
     */
    public List<CommentTreeElement> parse(String jsonText) throws RedditParseException {
        
        try {
            
            // Parse JSON response
            Object response = JSON_PARSER.parse(jsonText);
            
            // Validate main
            this.validate(response);
            
            // Move to the main object
            JSONObject main = (JSONObject) ((JSONObject) response).get("json");
            
            // List of comment and more mixed elements
            List<CommentTreeElement> elements = new LinkedList<CommentTreeElement>();
            
            // If the main has data (it can happen that it does not, when no comments identifiers were passed along)
            if (main.get("data") != null) {
            
                // Parse to a list of things
                List<Thing> things = this.parseGeneric(main.toJSONString(), "things");
            
                // Iterate over things
                for (Thing t : things) {
                    
                    if (t instanceof Comment) {
                        elements.add((Comment) t);
                    } else if (t instanceof More) {
                        elements.add((More) t);
                    } else {
                        LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
                    }
                    
                }
            
            }
            
            // Return resulting element list
            return elements;
        
        } catch (ParseException pe) {
            throw new RedditParseException(pe);
        }
        
    }
    
}
