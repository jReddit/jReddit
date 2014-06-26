package com.github.jreddit.submissions;

import java.util.LinkedList;
import java.util.List;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.QuerySyntax;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchSort;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
import com.github.jreddit.utils.UserSubmissionsCategory;

public class ExtendedSubmissions {
	
	private Submissions submissions;

	public ExtendedSubmissions(Submissions submissions) {
		this.submissions = submissions;
	}


    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, 
     * attempting to retrieve the desired amount.
     * 
     * @param user				User session
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofSubreddit(User user, String redditName, SubmissionsGetSort sort, int amount, Submission after) {
    	
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
			List<Submission> subresult = submissions.ofSubreddit(user, redditName, sort, counter, limit, after, null, true);
			result.addAll(subresult);
			
			// Increment counter
			counter += limit;
			
			// If the end of the submission stream has been reached
			if (subresult.size() < limit) {
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
     * Get submissions from the specified subreddit, as the given user, 
     * attempting to retrieve the desired amount after the given submission.
     * 
     * @param user				User session
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which to get
     * @return					List of the submissions
     */
    public List<Submission> get(String redditName, SubmissionsGetSort sort, int amount, Submission after) {
    	return ofSubreddit(null, redditName, sort, amount, after);
    }
    
    /**
     * Get submissions from the specified subreddit, as the given user, attempting to retrieve the desired amount.
     * @param user				User session
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     */
    public List<Submission> ofSubreddit(User user, String redditName, SubmissionsGetSort sort, int amount) {
    	return ofSubreddit(user, redditName, sort, amount, null);
    }
    
    /**
     * Get submissions from the specified subreddit, as the specified user, using the given sorting method.
     * 
     * @param user			User
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> ofSubreddit(User user, String redditName, SubmissionsGetSort sort) {
    	return ofSubreddit(user, redditName, sort, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT, null);
    }
    
    /**
     * Get submissions from the specified subreddit attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     */
    public List<Submission> ofSubreddit(String redditName, SubmissionsGetSort sort, int amount) {
    	return ofSubreddit(null, redditName, sort, amount, null);
    }
    
    /**
     * Returns a list of submissions from a subreddit.
     * 
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> ofSubreddit(String redditName, SubmissionsGetSort sort) {
    	return ofSubreddit(null, redditName, sort, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT, null);
    }
    
    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, attempting to retrieve the desired amount.
     * 
     * @param user				User session
     * @param query 			Search query
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount, Submission after) {
    	
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
			List<Submission> subresult = submissions.search(user, query, QuerySyntax.LUCENE, sort, time, counter, limit, after, null, true);
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
     * @param user		User
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @param amount	How many to retrieve (if possible, result <= amount guaranteed)
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount) {
    	return search(user, query, sort, time, amount, null);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time as the given user.
	 * 
     * @param user		User
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time) {
    	return search(user, query, sort, time, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and 
     * within the given time and with maximum amount returned after the given submission.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @param amount	How many to retrieve (if possible, result <= amount guaranteed)
     * @param after		Submission after which need to be retrieved
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount, Submission after) {
    	return search(null, query, sort, time, amount, after);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time and with maximum amount returned.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @param amount	How many to retrieve (if possible, result <= amount guaranteed)
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount) {
    	return search(query, sort, time, amount, null);
    }
    
    /**
     * Search for submissions using the query with the given sorting method and within the given time.
	 * 
     * @param query 	Search query
     * @param sort		Search sorting method
     * @param time		Search time
     * @return <code>List</code> of submissions that match the query.
     */
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time) {
    	return search(query, sort, time, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT);
    }
    
    /**
     * Get submissions from the specified user.
     * 
     * @param user				User session
     * @param query 			Search query
     * @param category			Category
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofUser(User user, String username, UserSubmissionsCategory category, UserOverviewSort sort, int amount, Submission after) {
    	
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
			List<Submission> subresult = submissions.ofUser(user, username, category, sort, counter, limit, after, null, true);
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
     * Get submissions from the specified user.
     * 
     * @param user				User session
     * @param query 			Search query
     * @param category			Category
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     */
    public List<Submission> ofUser(User user, String username, UserSubmissionsCategory category, UserOverviewSort sort, int amount) {
    	return ofUser(user, username, category, sort, amount, null);
    }
	
}
