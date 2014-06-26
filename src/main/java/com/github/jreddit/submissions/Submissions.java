package com.github.jreddit.submissions;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Kind;
import com.github.jreddit.utils.ParamFormatter;
import com.github.jreddit.utils.QuerySyntax;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchSort;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
import com.github.jreddit.utils.UserSubmissionsCategory;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * This class offers the following functionality:
 * 1) Parsing the results of a request into Submission objects (see <code>Submissions.parse()</code>).
 * 2) The ability to get submissions from a subreddit (see <code>Submissions.get()</code>).
 * 3) The ability to search submissions on Reddit (see <code>Submissions.search()</code>).
 * 
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 * @author <a href="http://www.deltacdev.com">Simon Kassing</a>
 */
public class Submissions {
	
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
     * Parses a JSON feed received from Reddit (URL) into a nice list of Submission objects.
     * 
     * @param user 	User
     * @param url 	URL
     * @return 		Listing of submissions
     */
    public List<Submission> parse(User user, String url) {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of submissions
        List<Submission> submissions = new LinkedList<Submission>();
        
        // Send request to reddit server via REST client
        Object response = restClient.get(url, cookie).getResponseObject();
        
        if (response instanceof JSONObject) {
        	
	        JSONObject object = (JSONObject) response;
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

	        // Iterate over the submission results
	        JSONObject data;
	        Submission submission;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            
	            // Make sure it is of the correct kind
	            String kind = safeJsonToString(data.get("kind"));
	            if (kind.equals(Kind.LINK.value())) {
	            	
	            	// Create and add submission
		            data = ((JSONObject) data.get("data"));
		            submission = new Submission(data);
		            submission.setUser(user);
		            submissions.add(submission);
	            
	            }
	            
	        }
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of submissions 
        return submissions;
        
    }

    /**
     * Gets all the submissions of a particular subreddit using the given parameters.
     * The parameters here are in Strings instead of wrapper objects, which allows users
     * to manually adjust the parameters (if the API changes and jReddit is not updated
     * in time yet).
     * 
     * @param user 				The user (set null if the user is not needed)
     * @param subreddit			Name of the reddit (e.g. "funny")
     * @param sort				Sorting method
     * @param count				Count at which the submissions are started being numbered
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @param before			The submission before which needs to be retrieved
     * @param show_all			Show all (disables filters such as "hide links that I have voted on")
     * @return 					The linked list containing submissions
     */
    protected List<Submission> ofSubreddit(User user, String subreddit, String sort, String count, String limit, String after, String before, String show) {
    	assert subreddit != null && user != null;
    	
    	// Encode the reddit name for the URL:
    	try {
			subreddit = URLEncoder.encode(subreddit, "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	// Format parameters
    	String params = "";
    	
    	params = ParamFormatter.addParameter(params, "sort", sort);
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	params = ParamFormatter.addParameter(params, "show", show);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.SUBMISSIONS_GET, subreddit, params));
        
    }
    
    /**
     * Gets all the submissions of a particular subreddit using the given parameters.
     * 
     * @param user 				The user (set null if the user is not needed)
     * @param subreddit			Name of the reddit (e.g. "funny")
     * @param sort				Sorting method
     * @param count				Count at which the submissions are started being numbered
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @param before			The submission before which needs to be retrieved
     * @param show_all			Show all (disables filters such as "hide links that I have voted on")
     * @return 					The linked list containing submissions
     */
    public List<Submission> ofSubreddit(User user, String subreddit, SubmissionsGetSort sort, int count, int limit, Submission after, Submission before, boolean show_all) {
    	
    	if (subreddit == null || subreddit.isEmpty()) {
    		throw new IllegalArgumentException("The subreddit must be defined.");
    	}
    	
    	return ofSubreddit(
    			user, 
    			subreddit, 
    			(sort != null) ? sort.value() : "",
    			String.valueOf(count),
    			String.valueOf(limit),
    			(after != null) ? after.getFullName() : "",
    			(before != null) ? before.getFullName() : "",
    			(show_all) ? "all" : ""	
    	);
    }
    
    /**
     * Searches with the given query using the constraints given as parameters.
     * The parameters here are in Strings instead of wrapper objects, which allows users
     * to manually adjust the parameters (if the API changes and jReddit is not updated
     * in time yet).
     * 
     * @param user 				The user (set null if the user is not needed)
     * @param query 			The query
     * @param syntax			The query syntax
     * @param sort				Search sorting method
     * @param time				Search time
     * @param count				Count at which the submissions are started being numbered
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @param before			The submission before which needs to be retrieved
     * @param show_all			Show all (disables filters such as "hide links that I have voted on")
     * @return 					The linked list containing submissions
     */
    protected List<Submission> search(User user, String query, String syntax, String sort, String time, String count, String limit, String after, String before, String show) {
    	assert query != null && user != null;
    	
    	// Format parameters
    	String params = "";
    	try {
			params = ParamFormatter.addParameter(params, "q", URLEncoder.encode(query, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	params = ParamFormatter.addParameter(params, "syntax", syntax);
    	params = ParamFormatter.addParameter(params, "sort", sort);
    	params = ParamFormatter.addParameter(params, "t", time);
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	params = ParamFormatter.addParameter(params, "show", show);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.SUBMISSIONS_SEARCH, params));
        
    }
    
    /**
     * Searches with the given query using the constraints given as parameters.
     * 
     * @param user 				The user (set null if the user is not needed)
     * @param query 			The query
     * @param syntax			The query syntax
     * @param sort				Search sorting method
     * @param time				Search time
     * @param count				Count at which the submissions are started being numbered
     * @param limit				Maximum amount of submissions that can be returned (0-100, 25 default (see Reddit API))
     * @param after				The submission after which needs to be retrieved
     * @param before			The submission before which needs to be retrieved
     * @param show_all			Show all (disables filters such as "hide links that I have voted on")
     * @return 					The linked list containing submissions
     */
    public List<Submission> search(User user, String query, QuerySyntax syntax, SubmissionsSearchSort sort, SubmissionsSearchTime time, int count, int limit, Submission after, Submission before, boolean show_all) {
    	
    	if (query == null || query.isEmpty()) {
    		throw new IllegalArgumentException("The query must be defined.");
    	}
    	
    	if (limit < -1 || limit > RedditConstants.MAX_LIMIT_LISTING) {
    		throw new IllegalArgumentException("The limit needs to be between 0 and 100 (or -1 for default).");
    	}
    	
    	return search(
    			user, 
    			query, 
    			(syntax != null) ? syntax.value() : "",
    			(sort != null) ? sort.value() : "",
    			(time != null) ? time.value() : "",
    			String.valueOf(count),
    			String.valueOf(limit),
    			(after != null) ? after.getFullName() : "",
    			(before != null) ? before.getFullName() : "",
    			(show_all) ? "all" : ""		
    	);
    }
    
    /**
     * Get the submissions of a user.
     * In this variant all parameters are Strings.
     *
     * @param user				(Optional, set null/empty if not used) The user as whom to retrieve the comments
     * @param username	 		Username of the user you want to retrieve from.
     * @param category    		(Optional, set null/empty if not used) Category in the user overview to retrieve submissions from
     * @param sort	    		(Optional, set null/empty if not used) Sorting method.
     * @param time		 		(Optional, set null/empty is not used) Time window
     * @param count        		(Optional, set null/empty if not used) Number at which the counter starts
     * @param limit        		(Optional, set null/empty if not used) Integer representing the maximum number of comments to return
     * @param after				(Optional, set null/empty if not used) After which comment to retrieve
     * @param before			(Optional, set null/empty if not used) Before which comment to retrieve
     * @param show				(Optional, set null/empty if not used) Show parameter ('given' is only acceptable value)
     * 
     * @return Comments of a user.
     */
    protected List<Submission> ofUser(User user, String username, String category, String sort, String count, String limit, String after, String before, String show) {
    	
    	// Format parameters
    	String params = "";
    	params = ParamFormatter.addParameter(params, "sort", sort);
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	params = ParamFormatter.addParameter(params, "show", show);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.USER_SUBMISSIONS_INTERACTION, username, category, params));
        
    }
    
    /**
     * Get the submissions of a user.
     * In this variant all parameters are Strings.
     *
     * @param user				(Optional, set null if not used) The user as whom to retrieve the comments
     * @param username	 		Username of the user you want to retrieve from.
     * @param category    		Category in the user overview to retrieve submissions from
     * @param sort	    		(Optional, set null if not used) Sorting method.
     * @param time		 		(Optional, set null is not used) Time window
     * @param count        		(Optional, set -1 if not used) Number at which the counter starts
     * @param limit        		(Optional, set -1 if not used) Integer representing the maximum number of comments to return
     * @param after				(Optional, set null if not used) After which comment to retrieve
     * @param before			(Optional, set null if not used) Before which comment to retrieve
     * @param show				(Optional, set false if not used) Show parameter ('given' is only acceptable value)
     * 
     * @return Submissions of a user.
     */
    public List<Submission> ofUser(User user, String username, UserSubmissionsCategory category, UserOverviewSort sort, int count, int limit, Submission after, Submission before, boolean show_given) {
    	
    	if (username == null || username.isEmpty()) {
    		throw new IllegalArgumentException("The username must be defined.");
    	}

    	if (category == null) {
    		throw new IllegalArgumentException("The category must be defined.");
    	}
    	
    	if (limit < -1 || limit > RedditConstants.MAX_LIMIT_LISTING) {
    		throw new IllegalArgumentException("The limit needs to be between 0 and 100 (or -1 for default).");
    	}
    	
    	return ofUser(
    			user, 
    			username,
    			(category != null) ? category.value() : "",
    			(sort != null) ? sort.value() : "",
    			String.valueOf(count),
    			String.valueOf(limit),
    			(after != null) ? after.getFullName() : "",
    			(before != null) ? before.getFullName() : "",
    			(show_given) ? "given" : ""		
    	);
    }

}