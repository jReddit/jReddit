package com.github.jreddit.parser.listing;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.jreddit.parser.entity.Subreddit;
import com.github.jreddit.parser.entity.Thing;
import com.github.jreddit.parser.exception.RedditParseException;

public class SubredditsListingParser extends RedditListingParser {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(SubredditsListingParser.class);
    
    /**
     * Parse JSON received from reddit into a list of subreddits.
     * This parser expects the JSON to be of a listing of subreddits.
     * 
     * @param jsonText JSON Text
     * @return Parsed list of subreddits
     * 
     * @throws ParseException
     */
    public List<Subreddit> parse(String jsonText) throws RedditParseException {
        
        // Parse to a list of things
        List<Thing> things = this.parseGeneric(jsonText);
        
        // List of comment and submission mixed elements
        List<Subreddit> subreddits = new LinkedList<Subreddit>();
        
        // Iterate over things
        for (Thing t : things) {
            
            if (t instanceof Subreddit) {
                subreddits.add((Subreddit) t);
            } else {
                LOGGER.warn("Encountered an unexpected reddit thing (" + t.getKind().value() + "), skipping it.");
            }
            
        }
        
        // Return resulting comments list
        return subreddits;
        
    }
    
}
