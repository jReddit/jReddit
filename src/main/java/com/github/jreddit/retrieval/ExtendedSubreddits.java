package com.github.jreddit.retrieval;

import java.util.LinkedList;
import java.util.List;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.retrieval.params.SubredditsView;
import com.github.jreddit.utils.RedditConstants;

public class ExtendedSubreddits {
	
	private Subreddits subreddits;
	
	public ExtendedSubreddits(Subreddits subreddits) {
		this.subreddits = subreddits;
	}
	
	/**
     * Search for the subreddit as the given user, using the given query, for the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return			List of subreddits matching the search query
     */
    public List<Subreddit> search(String query, int amount, Subreddit after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of subreddits.");
    		return null;
    	}

    	// List of submissions
        List<Subreddit> result = new LinkedList<Subreddit>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Subreddit> subresult = subreddits.search(query, -1, limit, after, null);
			if (subresult == null) {
				return null;
			}
			result.addAll(subresult);
			
			// If the end of the submission stream has been reached
			if (subresult.size() != limit) {
				//System.out.println("API Stream finished prematurely: received " + subresult.size() + " subreddits but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = subresult.get(subresult.size() - 1);
			
		}
		
		return result;

    }
    
    /**
     * Search for the subreddit as the given user, using the given query, for the given amount.
     * This concatenates several requests to reach the amount.
     * 
     * @param user		User
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @return 			List of subreddits matching the search query
     */
    public List<Subreddit> search(String query, int amount) {
    	return search(query, amount, null);
    }
    
    /**
     * Search for the subreddit as the given user, using the given query, retrieving as much as possible.
     * This concatenates several requests to reach the amount.
     * 
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @return 			List of subreddits matching the search query
     */
    public List<Subreddit> search(String query) {
    	return search(query, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }
    
    /**
     * Retrieve subreddits as the given user of a specific type of the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param type		Type of sub reddit, this determines the ordering (e.g. new or mine)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return			List of subreddits
     */
    public List<Subreddit> get(SubredditsView type, int amount, Subreddit after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of subreddits.");
    		return null;
    	}

    	// List of submissions
        List<Subreddit> result = new LinkedList<Subreddit>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Subreddit> subresult = subreddits.get(type, -1, limit, after, null);
			if (subresult == null) {
				return null;
			}
			result.addAll(subresult);
			
			// If the end of the submission stream has been reached
			if (subresult.size() != limit) {
				//System.out.println("API Stream finished prematurely: received " + subresult.size() + " subreddits but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = subresult.get(subresult.size() - 1);
			
		}
		
		return result;

    }
    
    /**
     * Get a list of subreddits of a certain subreddit type of a certain amount.
     *
     * @param type		 	Type of subreddit 
     * @param amount		Amount to retrieve
     * @return list of Subreddits of that type
     */
    public List<Subreddit> get(SubredditsView type, int amount) {
		return get(type, amount, null);
    }
    
    /**
     * Get a list of subreddits of a certain subreddit type as big as possible as a certain user.
     *
     * @param type		 	Type of subreddit 
     * @return list of Subreddits of that type
     */
    public List<Subreddit> get(SubredditsView type) {
		return get(type, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }

    /**
     * Get a Subreddit by its name as a given user.
     *
     * @param user			User
     * @param subredditName Name of the subreddit to retrieve
     * @return Subreddit object representing the desired subreddit, or NULL if it does not exist
     */
    public Subreddit getByName(String subredditName) {
    	List<Subreddit> all = search(subredditName, 1, null);

    	if (all.size() == 1 && all.get(0).getDisplayName().equalsIgnoreCase(subredditName)) {
    		return all.get(0);
    	}
    	
        return null;
    }
    
}
