package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.exception.RedditParseException;

public class SubmissionsListingParser extends RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SubmissionsListingParser.class);
    
    /**
     * Parse JSON received from reddit into a list of submissions.
     * This parser expects the JSON to be of a listing of submissions ('links').
     * 
     * @param jsonText JSON Text
     * @return Parsed list of submissions
     * 
     * @throws ParseException
     */
    public List<Submission> parse(String jsonText) throws RedditParseException {
        
        // Parse to a list of things
        List<Thing> things = this.parseGeneric(jsonText);
        
        // List of comment and submission mixed elements
        List<Submission> submissions = new LinkedList<Submission>();
        
        // Iterate over things
        for (Thing t : things) {
            
            if (t instanceof Submission) {
                submissions.add((Submission) t);
            } else {
                LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
            }
            
        }
        
        // Return resulting comments list
        return submissions;
        
    }
    
}
