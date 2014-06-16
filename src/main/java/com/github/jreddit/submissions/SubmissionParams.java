package com.github.jreddit.submissions;

/**
 * Parameter class for the different parameters that can be passed
 * along to search through the submissions.
 * 
 * @author Simon Kassing
 */
public class SubmissionParams {
	
    /**
     * Search sort parameter.
     * @author Simon Kassing
     */
    public enum SearchSort {
    	HOT, RELEVANCE, NEW, TOP, COMMENTS
    }
	
    /**
     * Subreddit sort parameter.
     * @author Simon Kassing
     */
    public enum SubredditSort {
        HOT, NEW, RISING, CONTROVERSIAL, TOP
    }
    
    /**
     * Search time parameter.
     * @author Simon Kassing
     */
    public enum SearchTime {
        HOUR, DAY, WEEK, MONTH, YEAR, ALL
    }
    
    /**
     * Translate the search sorting method to the corresponding URL parameter.
     * @param s Search sort method
     * @return Search sort URL parameter (e.g. 'new' or 'top')
     */
    protected static String translate(SearchSort s) {
    	
    	switch (s) {
			case RELEVANCE:
				return "relevance";
			case NEW:
				return "new";
			case HOT:
				return "hot";
			case TOP:
				return "top";
			case COMMENTS:
				return "comments";
			default:
				System.err.println("Unknown search sort: " + s);
				return null;
    	}
		
    }
	
    /**
     * Translate the subreddit sorting method to the corresponding URL parameter.
     * @param s Subreddit sort method
     * @return Sort URL parameter (e.g. 'new' or 'top')
     */
    protected static String translate(SubredditSort s) {
    	
    	switch (s) {
			case CONTROVERSIAL:
				return "controversial";
			case NEW:
				return "new";
			case HOT:
				return "hot";
			case RISING:
				return "rising";
			case TOP:
				return "top";
			default:
				System.err.println("Unknown sort: " + s);
				return null;
    	}
		
    }
	
    /**
     * Translate the search time to the corresponding URL parameter.
     * @param s Search time
     * @return Time URL parameter (e.g. 'hour' or 'all')
     */
    protected static String translate(SearchTime t) {
    	
    	switch (t) {
			case HOUR:
				return "hour";
			case DAY:
				return "day";
			case WEEK:
				return "week";
			case MONTH:
				return "month";
			case YEAR:
				return "year";
			case ALL:
				return "all";
			default:
				System.err.println("Unknown time: " + t);
				return null;
    	}
		
    }
    
}
