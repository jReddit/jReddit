package com.github.jreddit.submissions;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.submissions.SubmissionParams.SearchSort;
import com.github.jreddit.submissions.SubmissionParams.SearchTime;
import com.github.jreddit.submissions.SubmissionParams.SubredditSort;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * This class offers some submission utilities.
 * 
 * Considerations: there is a ~1000 maximum amount of submissions that are available in listings
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author Simon Kassing (sk-TUD, email: deltacdev@gmail.com)
 */
public class Submissions {

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
	
	/**
	 * Handle to REST client instance.
	 */
    private final RestClient restClient;

    /**
     * Constructor.
     * @param restClient REST client handle
     */
    public Submissions(RestClient restClient) {
        this.restClient = restClient;
    }
    
    /**
     * Retrieve submissions from the given URL as the given user.
     * @param user 	User
     * @param url 	URL
     * @return 		Listing of submissions
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON parsing fails
     */
    protected List<Submission> retrieve(User user, String url) throws IOException, ParseException {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();
        
        // Send request to reddit server via REST client
        // System.out.println(url);
        JSONObject object = (JSONObject) restClient.get(url, cookie).getResponseObject();
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        // Iterate over the submission results
        JSONObject data;
        Submission submission;
        for (Object anArray : array) {
            data = (JSONObject) anArray;
            data = ((JSONObject) data.get("data"));
            submission = new Submission(data);
            submission.setUser(user);
            submissions.add(submission);
        }

        // Finally return list of submissions 
        return submissions;
        
    }

    /**
     * This function returns a linked list containing the submissions on a given subreddit.
     * Preconditions: redditName is not null, user is not null
	 * 
     * @param user 				The user (set null if the user is not needed)
     * @param redditName 		The subreddit's name
     * @param sort				SubredditSorting method
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @return 					The linked list containing submissions
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON parsing fails
     */
    public List<Submission> getLimited(User user, String redditName, SubredditSort sort, int limit, Submission after) throws IOException, ParseException {
    	assert redditName != null && user != null;

        // Subreddit sort tag
        String sort_tag = SubmissionParams.translate(sort);
        if (sort_tag == null) {
        	return null;
        }
        
        // URL string
        String url = "/r/" + URLEncoder.encode(redditName, "ISO-8859-1") + "/" + sort_tag + ".json";
        String url_params = "";

        // After
        if (after != null) {
        	url_params += "&after=" + after.getFullName();
        }

        // Limit
        if (limit != -1) {
        	url_params += "&limit=" + limit;
        }
        
        // Concatenate parameters behind url
        if (url_params.length() > 0) {
        	url = url
        			.concat("?")
        			.concat(url_params.substring(1, url_params.length()));
        }
        
        // Retrieve submissions from the given URL
        return retrieve(user, url);
        
    }
    
    /**
     * Searches with the given query using the constraints given as parameters.
     * Preconditions: query is not null, user is not null
     * 
     * @param user 				The user (set null if the user is not needed)
     * @param query 			The query
     * @param sort				Search sorting method
     * @param time				Search time
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @return 					The linked list containing submissions
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON parsing fails
     */
    public List<Submission> searchLimited(User user, String query, SearchSort sort, SearchTime time, int limit, Submission after) throws IOException, ParseException {
    	assert query != null && user != null;
        
        // URL string
        String url = "/search.json?q=" + URLEncoder.encode(query, "ISO-8859-1");
        String url_params = "";
        
        // Search sort
        String sort_tag = SubmissionParams.translate(sort);
        if (sort_tag != null) {
        	url_params += "&sort=" + sort_tag;
        }
        
        // Search time
        String time_tag = SubmissionParams.translate(time);
        if (time_tag != null) {
        	url_params += "&t=" + time_tag;
        }

        // After
        if (after != null) {
        	url_params += "&after=" + after.getFullName();
        }

        // Limit
        if (limit != -1) {
        	url_params += "&limit=" + limit;
        }
        
        // Concatenate all the parameters
        if (url_params.length() > 0) {
        	url = url.concat(url_params);
        }
        
        // Retrieve submissions from the given URL
        return retrieve(user, url);
        
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
    public List<Submission> get(User user, String redditName, SubredditSort sort, int amount, Submission after) throws IOException, ParseException {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> result = this.getLimited(user, redditName, sort, limit, after);
			submissions.addAll(result);
			
			// If the end of the submission stream has been reached
			if (result.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + result.size() + " but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = result.get(result.size() - 1);
			
		}
		
		return submissions;
    	
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
    public List<Submission> get(String redditName, SubredditSort sort, int amount, Submission after) throws IOException, ParseException {
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
    public List<Submission> get(User user, String redditName, SubredditSort sort, int amount) throws IOException, ParseException {
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
    public List<Submission> get(User user, String redditName, SubredditSort sort) throws IOException, ParseException {
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
    public List<Submission> get(String redditName, SubredditSort sort, int amount) throws IOException, ParseException {
    	return get(null, redditName, sort, amount, null);
    }
    
    /**
     * Returns a list of submissions from a subreddit.
     * 
     * @param redditName 	The subreddit at which submissions you want to retrieve submissions.
     * @param sort			Subreddit sorting method
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> get(String redditName, SubredditSort sort) throws IOException, ParseException {
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
    public List<Submission> search(User user, String query, SearchSort sort, SearchTime time, int amount, Submission after) throws IOException, ParseException {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}

    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
			amount -= limit;
			
			// Retrieve submissions
			List<Submission> result = this.searchLimited(user, query, sort, time, limit, after);
			submissions.addAll(result);
			
			// If the end of the submission stream has been reached
			if (result.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + result.size() + " but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = result.get(result.size() - 1);
			
		}
		
		return submissions;
    	
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
    public List<Submission> search(User user, String query, SearchSort sort, SearchTime time, int amount) throws IOException, ParseException {
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
    public List<Submission> search(User user, String query, SearchSort sort, SearchTime time) throws IOException, ParseException {
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
    public List<Submission> search(String query, SearchSort sort, SearchTime time, int amount, Submission after) throws IOException, ParseException {
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
    public List<Submission> search(String query, SearchSort sort, SearchTime time, int amount) throws IOException, ParseException {
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
    public List<Submission> search(String query, SearchSort sort, SearchTime time) throws IOException, ParseException {
    	return search(query, sort, time, APPROXIMATE_MAX_LISTING_SIZE);
    }
    
}