package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Comment;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.entity.imaginary.MixedListingElement;
import com.github.jreddit.request.error.RedditException;

/**
 * Parser for a listing that has both submissions and comments mixed together.
 * 
 * @author Simon Kassing
 * 
 * @see MixedListingElement
 */
public class MixedListingParser extends RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(MixedListingParser.class);
    
    /**
     * Parse JSON received from reddit into a list of submissions and comments.
     * This parser expects the JSON to be of a listing of submissions and comments.
     * 
     * @param jsonText JSON Text
     * @return Parsed list of submissions
     * 
     * @throws ParseException
     */
    public List<MixedListingElement> parse(String jsonText) throws ParseException, RedditException {
        
        // Parse to a list of things
        List<Thing> things = this.parseGeneric(jsonText);
        
        // List of comment and submission mixed elements
        List<MixedListingElement> mixedElements = new LinkedList<MixedListingElement>();
        
        // Iterate over things
        for (Thing t : things) {
            
            if (t instanceof Comment) {
                mixedElements.add((Comment) t);
            } else if (t instanceof Submission) {
                mixedElements.add((Submission) t);
            } else {
                LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
            }
            
        }
        
        // Return result
        return mixedElements;
        
    }
    
}
