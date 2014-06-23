package com.github.jreddit.subreddit;


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
import com.github.jreddit.utils.SubredditsView;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * This class offers the following functionality:
 * 1) Parsing the results of a request into Subreddit objects (see <code>Subreddits.parse()</code>).
 * 2) The ability to get listings of subreddits (see <code>Subreddits.get()</code>).
 * 3) The ability to search the subreddits (for subreddits) on Reddit (see <code>Subreddits.search()</code>).
 * 
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 * @author Simon Kassing
 */
public class Subreddits {
	
	/**
	 * Handle to the REST client instance.
	 */
    private final RestClient restClient;

    /**
     * Constructor.
     * 
     * @param restClient REST client instance
     */
    public Subreddits(RestClient restClient) {
        this.restClient = restClient;
    }
    
    /**
     * Parses a JSON feed from the Reddit (URL) into a nice list of Subreddit objects.
     * 
     * @param user 	User
     * @param url 	URL
     * @return 		Listing of submissions
     */
    public List<Subreddit> parse(User user, String url) {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of subreddits
        List<Subreddit> subreddits = new LinkedList<Subreddit>();
        
        // Send request to reddit server via REST client
        Object response = restClient.get(url, cookie).getResponseObject();
        
        if (response instanceof JSONObject) {
        	
	        JSONObject object = (JSONObject) response;
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
	
	        // Iterate over the subreddit results
	        JSONObject data;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            
	            // Make sure it is of the correct kind
	            String kind = safeJsonToString(data.get("kind"));
	            if (kind.equals(Kind.SUBREDDIT.value())) {
	            	
	            	// Create and add subreddit
		            data = ((JSONObject) data.get("data"));
		            subreddits.add(new Subreddit(data));
	            
	            }
	            
	        }
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of subreddits 
        return subreddits;
        
    }
    
    /**
     * Searches all subreddits with the given query using the given parameters.
     * The parameters here are in Strings instead of wrapper objects, which allows users
     * to manually adjust the parameters (if the API changes and jReddit is not updated
     * in time yet).
     * 
     * @param user		User
     * @param query		Search query
     * @param count		Count at which the subreddits are started being numbered
     * @param limit		Maximum amount of subreddits that can be returned (0-100, 25 default (see Reddit API))
     * @param after		The subreddit after which needs to be retrieved
     * @param before	The subreddit after which needs to be retrieved
     * 
     * @return	List of subreddits that satisfy the given parameters.
     */
    public List<Subreddit> search(User user, String query, String count, String limit, String after, String before) {
    	
    	// Format parameters
    	String params = "";
    	try {
			params = ParamFormatter.addParameter(params, "q", URLEncoder.encode(query, "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.SUBREDDITS_SEARCH, params));
        
    }

    /**
     * Searches all subreddits with the given query using the given parameters.
     * 
     * @param user		User
     * @param query		Search query
     * @param count		Count at which the subreddits are started being numbered
     * @param limit		Maximum amount of subreddits that can be returned (0-100, 25 default (see Reddit API))
     * @param after		The subreddit after which needs to be retrieved
     * @param before	The subreddit after which needs to be retrieved
     * 
     * @return	List of subreddits that satisfy the given parameters.
     */
    public List<Subreddit> search(User user, String query, int count, int limit, Subreddit after, Subreddit before) {
    	
    	if (query == null || query.isEmpty()) {
    		throw new IllegalArgumentException("The query must be defined.");
    	}
    	
    	return search(
    			user, 
    			query, 
    			String.valueOf(count),
    			String.valueOf(limit),
    			(after != null) ? after.getFullName() : "",
    			(before != null) ? before.getFullName() : ""	
    	);
    	
    }
    
    /**
     * Gets all subreddits using the given parameters.
     * The parameters here are in Strings instead of wrapper objects, which allows users
     * to manually adjust the parameters (if the API changes and jReddit is not updated
     * in time yet).
     * 
     * @param user		User
     * @param type		Type of subreddit, this determines the ordering (e.g. new or mine)
     * @param count		Count at which the subreddits are started being numbered
     * @param limit		Maximum amount of subreddits that can be returned (0-100, 25 default (see Reddit API))
     * @param after		The subreddit after which needs to be retrieved
     * @param before	The subreddit after which needs to be retrieved
     * 
     * @return	List of subreddits that satisfy the given parameters.
     */
    public List<Subreddit> get(User user, String type, String count, String limit, String after, String before) {
    	
    	// Format parameters
    	String params = "";
    	params = ParamFormatter.addParameter(params, "count", count);
    	params = ParamFormatter.addParameter(params, "limit", limit);
    	params = ParamFormatter.addParameter(params, "after", after);
    	params = ParamFormatter.addParameter(params, "before", before);
    	
        // Retrieve submissions from the given URL
        return parse(user, String.format(ApiEndpointUtils.SUBREDDITS_GET, type, params));
        
    }
    
    /**
     * Gets all subreddits using the given parameters.
     * 
     * @param user		User
     * @param type		Type of subreddit, this determines the ordering (e.g. new or mine)
     * @param count		Count at which the subreddits are started being numbered
     * @param limit		Maximum amount of subreddits that can be returned (0-100, 25 default (see Reddit API))
     * @param after		The subreddit after which needs to be retrieved
     * @param before	The subreddit after which needs to be retrieved
     * 
     * @return	List of subreddits that satisfy the given parameters.
     */
    public List<Subreddit> get(User user, SubredditsView type, int count, int limit, Subreddit after, Subreddit before) {
        	return get(
        			user, 
        			(type != null) ? type.value() : "",
        			String.valueOf(count),
        			String.valueOf(limit),
        			(after != null) ? after.getFullName() : "",
        			(before != null) ? before.getFullName() : ""
        	);
    }

}
