package com.github.jreddit.retrieval;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.retrieval.params.QuerySyntax;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.retrieval.params.UserSubmissionsCategory;
import com.github.jreddit.utils.RedditConstants;

public class ExtendedSubmissions {
	
	private Submissions submissions;

	public ExtendedSubmissions(Submissions submissions) {
		this.submissions = submissions;
	}


    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, 
     * attempting to retrieve the desired amount.
     * 
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofSubreddit(String redditName, SubmissionSort sort, int amount, Submission after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> result = new LinkedList<Submission>();

        // Do all iterations
        int counter = 0;
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> subresult = submissions.ofSubreddit(redditName, sort, counter, limit, after, null, true);
			if (subresult == null) {
				return new ArrayList<Submission>();
			}
			result.addAll(subresult);
			
			// Increment counter
			counter += limit;
			
			// If the end of the submission stream has been reached
			if (subresult.size() < limit) {
				// System.out.println("API Stream finished prematurely: received " + subresult.size() + " but wanted " + limit + ".");
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
     * Get submissions from the specified subreddit, as the given user, 
     * attempting to retrieve the desired amount after the given submission.
     * 
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which to get
     * @return					List of the submissions
     */
    public List<Submission> get(String redditName, SubmissionSort sort, int amount, Submission after) {
    	return ofSubreddit(redditName, sort, amount, after);
    }
    
    /**
     * Get submissions from the specified subreddit, as the specified user, using the given sorting method.
     * 
     * @param user			User
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> ofSubreddit(String redditName, SubmissionSort sort) {
    	return ofSubreddit(redditName, sort, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT, null);
    }
    
    /**
     * Get submissions from the specified subreddit attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     */
    public List<Submission> ofSubreddit(String redditName, SubmissionSort sort, int amount) {
    	return ofSubreddit(redditName, sort, amount, null);
    }
    
    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, attempting to retrieve the desired amount.
     * 
     * @param query 			Search query
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time, int amount, Submission after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> result = new LinkedList<Submission>();

        // Do all iterations
        int counter = 0;
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> subresult = submissions.search(query, QuerySyntax.LUCENE, sort, time, counter, limit, after, null, true);
			if (subresult == null) {
				return new ArrayList<Submission>();
			}
			result.addAll(subresult);
			
			// Increment counter
			counter += limit;
			
			// If the end of the submission stream has been reached
			if (subresult.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + subresult.size() + " but wanted " + limit + ".");
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
     * Search for submissions using the query with the given sorting method and within the given time as the given user and with maximum amount returned.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @param amount	How many to retrieve (if possible, result <= amount guaranteed)
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time, int amount) {
    	return search(query, sort, time, amount, null);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time as the given user.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SearchSort sort, TimeSpan time) {
    	return search(query, sort, time, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }

    
    /**
     * Get submissions from the specified user.
     * 
     * @param query 			Search query
     * @param category			Category
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofUser(String username, UserSubmissionsCategory category, UserOverviewSort sort, int amount, Submission after) {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> result = new LinkedList<Submission>();

        // Do all iterations
        int counter = 0;
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = (amount < RedditConstants.MAX_LIMIT_LISTING) ? amount : RedditConstants.MAX_LIMIT_LISTING;
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> subresult = submissions.ofUser(username, category, sort, counter, limit, after, null, true);
			if (subresult == null) {
				return new ArrayList<Submission>();
			}
			result.addAll(subresult);
			
			// Increment counter
			counter += limit;
			
			// If the end of the submission stream has been reached
			if (subresult.size() != limit) {
				// System.out.println("API Stream finished prematurely: received " + subresult.size() + " but wanted " + limit + ".");
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
     * Get submissions from the specified user.
     * 
     * @param query 			Search query
     * @param category			Category
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofUser(String username, UserSubmissionsCategory category, UserOverviewSort sort, int amount) {
    	return ofUser(username, category, sort, amount, null);
    }
	
}
