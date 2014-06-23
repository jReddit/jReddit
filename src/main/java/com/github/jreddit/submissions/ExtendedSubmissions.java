package com.github.jreddit.submissions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.QuerySyntax;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchSort;
import com.github.jreddit.utils.SubmissionsSearchTime;

public class ExtendedSubmissions {
	
	private Submissions submissions;
	
	/**
	 * Limit of submissions that are retrieved per request.
	 * Minimum: 0.
	 * Default: 25.
	 * Maximum: 100.
	 * 
	 * According to Reddit API.
	 */
	public static final int MAX_LIMIT = 100;
	
	/**
	 * Approximately the maximum listing size, including pagination until
	 * the end. This differs from time to time, but after some observations
	 * this is a nice upper bound.
	 */
	public static final int APPROXIMATE_MAX_LISTING_SIZE = 1300;
	
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
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public List<Submission> get(User user, String redditName, SubmissionsGetSort sort, int amount, Submission after) throws IOException, ParseException {
    	
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
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> subresult = submissions.ofSubreddit(user, redditName, sort, counter, limit, after, null, true);
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
     * Get submissions from the specified subreddit, as the given user, 
     * attempting to retrieve the desired amount after the given submission.
     * 
     * @param user				User session
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which to get
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public List<Submission> get(String redditName, SubmissionsGetSort sort, int amount, Submission after) throws IOException, ParseException {
    	return get(null, redditName, sort, amount, after);
    }
    
    /**
     * Get submissions from the specified subreddit, as the given user, attempting to retrieve the desired amount.
     * @param user				User session
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public List<Submission> get(User user, String redditName, SubmissionsGetSort sort, int amount) throws IOException, ParseException {
    	return get(user, redditName, sort, amount, null);
    }
    
    /**
     * Get submissions from the specified subreddit, as the specified user, using the given sorting method.
     * 
     * @param user			User
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> get(User user, String redditName, SubmissionsGetSort sort) throws IOException, ParseException {
    	return get(user, redditName, sort, APPROXIMATE_MAX_LISTING_SIZE, null);
    }
    
    /**
     * Get submissions from the specified subreddit attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param sort				Subreddit sorting method
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public List<Submission> get(String redditName, SubmissionsGetSort sort, int amount) throws IOException, ParseException {
    	return get(null, redditName, sort, amount, null);
    }
    
    /**
     * Returns a list of submissions from a subreddit.
     * 
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> get(String redditName, SubmissionsGetSort sort) throws IOException, ParseException {
    	return get(null, redditName, sort, APPROXIMATE_MAX_LISTING_SIZE, null);
    }
    
    /**
     * Get submissions from the specified subreddit after a specific submission, as the given user, attempting to retrieve the desired amount.
     * FIXME Essentially, this is code duplication of the get method.
     * 
     * @param user				User session
     * @param query 			Search query
     * @param sort				Search sorting method (e.g. new or top)
     * @param time				Search time (e.g. day or all)
     * @param amount			Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount, Submission after) throws IOException, ParseException {
    	
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
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
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
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount) throws IOException, ParseException {
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
    public List<Submission> search(User user, String query, SubmissionsSearchSort sort, SubmissionsSearchTime time) throws IOException, ParseException {
    	return search(user, query, sort, time, APPROXIMATE_MAX_LISTING_SIZE);
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
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount, Submission after) throws IOException, ParseException {
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
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time, int amount) throws IOException, ParseException {
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
    public List<Submission> search(String query, SubmissionsSearchSort sort, SubmissionsSearchTime time) throws IOException, ParseException {
    	return search(query, sort, time, APPROXIMATE_MAX_LISTING_SIZE);
    }
    
	
}
