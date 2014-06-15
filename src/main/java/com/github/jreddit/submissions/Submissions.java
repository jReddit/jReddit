package com.github.jreddit.submissions;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * This class offers some submission utilities.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author Simon Kassing (sk-TUD, email: deltacdev@gmail.com)
 */
public class Submissions {

    private final RestClient restClient;

    public enum Sort {
        POPULAR, NEW, RISING, CONTROVERSIAL, TOP, GILDED
    }

    public Submissions(RestClient restClient) {
        this.restClient = restClient;
    }
    
    /**
     * Get the reddit URL sort tag for the given sort method.
     * @param s Sort method
     * @return Sort tag (e.g. 'new' or 'top')
     */
    protected String getSortTag(Sort s) {
    	
    	switch (s) {
			case CONTROVERSIAL:
				return "controversial";
			case GILDED:
				return "gilded";
			case NEW:
				return "new";
			case POPULAR:
				return "popular";
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
     * This function returns a linked list containing the submissions on a given subreddit.
     * 
     * Preconditions: redditName is not null, user is not null
     * 
     * Notes:
     *  > This is only possible when using the New sorting, as popular can shift based on current trends.
     *
     * @param redditName 	The subreddit's name
     * @param user 				The user (set null if the user is not needed)
     * @param sort				Sorting method
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @return 					The linked list containing submissions
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON parsing fails
     */
    public LinkedList<Submission> getSubmissionsWithinLimit(String redditName, User user, Sort sort, int limit, Submission after) throws IOException, ParseException {
    	assert redditName != null && user != null;
    	
    	// List of submissions
        LinkedList<Submission> submissions = new LinkedList<Submission>();
        
        // Sort tag
        String sort_tag = getSortTag(sort);
        if (sort_tag == null) {
        	return null;
        }
        
        // URL string
        String url = "/r/" + redditName + "/" + sort_tag + ".json";
        String url_params = "";

        if (after != null) {
        	url_params += "&after=" + after.getFullName();
        }

        if (limit != -1) {
        	url_params += "&limit=" + limit;
        }
        
        if (url_params.length() > 0) {
        	url = String.format("%s%s%s", url, "?", url_params.substring(1, url_params.length()));
        }
        
        //System.out.println(url);
        
        // Retrieve session information from user via cookie
        String cookie = (user == null) ? null : user.getCookie();
       	
        // Send request to reddit server via REST client
        Response r =  restClient.get(url, cookie);
        
        JSONObject object = (JSONObject) r.getResponseObject();
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
     * Get submissions from the specified subreddit after a specific submission, as the given user, attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param user				User session
     * @param desiredAmount		Desired amount which will be attempted. No guarantee! See request limits.
     * @param after				Submission after which the submissions need to be fetched.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public LinkedList<Submission> getSubmissions(String redditName, User user, Sort sort, int desiredAmount, Submission after) throws IOException, ParseException {
    	
    	if (desiredAmount < 0) {
    		System.err.println("You cannot retrieve a negative amount of submissions.");
    		return null;
    	}
    	
    	// Limit per iteration, between 0 and 100, 25 is default in API
    	int limit_step = 100;

    	// List of submissions
        LinkedList<Submission> submissions = new LinkedList<Submission>();

        // Do all iterations
		while (desiredAmount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = limit_step;
			if (desiredAmount < limit_step) {
				limit = desiredAmount;
			}
			desiredAmount -= limit;
			
			// Retrieve submissions
			LinkedList<Submission> result = this.getSubmissionsWithinLimit(redditName, user, sort, limit, after);
			submissions.addAll(result);
			
			// If not enough are returned, we can stop.
			// Reasons:
			// > There are not enough posts left after the 'after' submission
			// > Reddit denies, because limit was surpassed
			if (result.size() != limit) {
				System.out.println("API Response failure: received " + result.size() + " but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (desiredAmount <= 0) {
				break;
			}
			
			// Previous last submission
			after = result.get(result.size() - 1);
			
			// TODO: Is a time limitation what is required? API suggests 1 call per 2 secs maximum, or 30 per minute with some burstiness allowed.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return submissions;
    	
    }
    
    /**
     * Get submissions from the specified subreddit, as the given user, attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param user				User session
     * @param desiredAmount		Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public LinkedList<Submission> getSubmissions(String redditName, User user, Sort sort, int desiredAmount) throws IOException, ParseException {
    	return getSubmissions(redditName, user, sort, desiredAmount, null);
    }
    
    /**
     * Get submissions from the specified subreddit attempting to retrieve the desired amount.
     * @param redditName 		Subreddit name (e.g. 'fun', 'wtf', 'programming')
     * @param desiredAmount		Desired amount which will be attempted. No guarantee! See request limits.
     * @return					List of the submissions
     * @throws IOException		Thrown if the connection failed
     * @throws ParseException 	Thrown if the parsing of JSON failed
     */
    public LinkedList<Submission> getSubmissions(String redditName, Sort sort, int desiredAmount) throws IOException, ParseException {
    	return getSubmissions(redditName, null, sort, desiredAmount, null);
    }
    
    /**
     * Returns a list of submissions from a subreddit.
     * TODO: This is a very simple method that uses all default values, delete this?
     *
     * @param redditName The subreddit at which submissions you want to retrieve submissions.
     * @return <code>List</code> of submissions on the subreddit.
     */
    public List<Submission> getSubmissions(String redditName, Sort sort) throws IOException, ParseException {
    	return getSubmissionsWithinLimit(redditName, null, sort, -1, null);
    }
    
}