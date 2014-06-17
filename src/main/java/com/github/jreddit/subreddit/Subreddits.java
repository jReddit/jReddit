package com.github.jreddit.subreddit;


import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * Class to deal with Subreddits
 *
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 * @author Simon Kassing
 */
public class Subreddits {
	
	/**
	 * Limit of subreddits that are retrieved per request.
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
     * Retrieve subreddits from the given URL as the given user.
     * @param user 	User
     * @param url 	URL
     * @return 		Listing of submissions
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON parsing fails
     */
    protected List<Subreddit> retrieve(User user, String url) throws IOException, ParseException {
    	
    	// Determine cookie
    	String cookie = (user == null) ? null : user.getCookie();
    	
    	// List of subreddits
        List<Subreddit> subreddits = new LinkedList<Subreddit>();
        
        // Send request to reddit server via REST client
        System.out.println(url);
        Object response = restClient.get(url, cookie).getResponseObject();
        
        if (response instanceof JSONObject) {
        	
	        JSONObject object = (JSONObject) response;
	        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
	
	        // Iterate over the subreddit results
	        JSONObject data;
	        for (Object anArray : array) {
	            data = (JSONObject) anArray;
	            data = ((JSONObject) data.get("data"));
	            subreddits.add(new Subreddit(data));
	        }
        
        } else {
        	System.err.println("Cannot cast to JSON Object: '" + response.toString() + "'");
        }

        // Finally return list of subreddits 
        return subreddits;
        
    }
    
    /**
     * Search for the subreddit as the given user, using the given query, with the given imposed limit after the given subreddit.
     * @param user		User
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param limit		Limit to retrieve (result <= limit)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> searchLimited(User user, String query, int limit, Subreddit after) throws IOException, ParseException {
    	assert query != null;
        
        // URL string
        String url = "/subreddits/search.json?q=" + URLEncoder.encode(query, "ISO-8859-1");
        String url_params = "";

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
        
        // Retrieve subreddits from the given URL
        return retrieve(user, url);
        
    }
    
    /**
     * Retrieve subreddits as the given user of a specific type with the given imposed limit after the given subreddit.
     * @param user		User
     * @param type		Type of sub reddit, this determines the ordering (e.g. new or mine)
     * @param limit		Limit to retrieve (result <= limit)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> getLimited(User user, SubredditType type, int limit, Subreddit after) throws IOException, ParseException {
        
        // URL string
        String url = String.format(ApiEndpointUtils.SUBREDDITS_GET, type.getValue());
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
        
        // Retrieve subreddits from the given URL
        return retrieve(user, url);
        
    }
    
    /**
     * Search for the subreddit as the given user, using the given query, for the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param user		User
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(User user, String query, int amount, Subreddit after) throws IOException, ParseException {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of subreddits.");
    		return null;
    	}

    	// List of submissions
        List<Subreddit> subreddits = new LinkedList<Subreddit>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
			amount -= limit;
			
			// Retrieve submissions
			List<Subreddit> result = this.searchLimited(user, query, limit, after);
			subreddits.addAll(result);
			
			// If the end of the submission stream has been reached
			if (result.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + result.size() + " subreddits but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = result.get(result.size() - 1);
			
		}
		
		return subreddits;

    }
    
    /**
     * Search for the subreddit using the given query, for the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return 			List of subreddits matching the search query
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(String query, int amount, Subreddit after) throws IOException, ParseException {
    	return search(null, query, amount, after);
    }
    
    /**
     * Search for the subreddit as the given user, using the given query, for the given amount.
     * This concatenates several requests to reach the amount.
     * 
     * @param user		User
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @return 			List of subreddits matching the search query
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(User user, String query, int amount) throws IOException, ParseException {
    	return search(user, query, amount, null);
    }
    
    /**
     * Search for the subreddit using the given query, for the given amount.
     * This concatenates several requests to reach the amount.
     * 
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @param amount	Amount to retrieve (result <= amount)
     * @return 			List of subreddits matching the search query
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(String query, int amount) throws IOException, ParseException {
    	return search(null, query, amount);
    }
    
    /**
     * Search for the subreddit as the given user, using the given query, retrieving as much as possible.
     * This concatenates several requests to reach the amount.
     * 
     * @param user		User
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @return 			List of subreddits matching the search query
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(User user, String query) throws IOException, ParseException {
    	return search(user, query, APPROXIMATE_MAX_LISTING_SIZE);
    }
    
    /**
     * Search for the subreddit using the given query, retrieving as much as possible.
     * This concatenates several requests to reach the amount.
     * 
     * @param query		Query (only simple, Reddit does not yet support field search for subreddits)
     * @return 			List of subreddits matching the search query
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> search(String query) throws IOException, ParseException {
    	return search(null, query);
    }
    
    /**
     * Retrieve subreddits as the given user of a specific type of the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param user		User
     * @param type		Type of sub reddit, this determines the ordering (e.g. new or mine)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return			List of subreddits
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> get(User user, SubredditType type, int amount, Subreddit after) throws IOException, ParseException {
    	
    	if (amount < 0) {
    		System.err.println("You cannot retrieve a negative amount of subreddits.");
    		return null;
    	}

    	// List of submissions
        List<Subreddit> subreddits = new LinkedList<Subreddit>();

        // Do all iterations
		while (amount >= 0) {
			
			// Determine how much still to retrieve in this iteration
			int limit = MAX_LIMIT;
			if (amount < MAX_LIMIT) {
				limit = amount;
			}
			amount -= limit;
			
			// Retrieve submissions
			List<Subreddit> result = this.getLimited(user, type, limit, after);
			subreddits.addAll(result);
			
			// If the end of the submission stream has been reached
			if (result.size() != limit) {
				System.out.println("API Stream finished prematurely: received " + result.size() + " subreddits but wanted " + limit + ".");
				break;
			}
			
			// If nothing is left desired, exit.
			if (amount <= 0) {
				break;
			}
			
			// Previous last submission
			after = result.get(result.size() - 1);
			
		}
		
		return subreddits;

    }
    
    /**
     * Retrieve subreddits of a specific type of the given amount after the given subreddit.
     * This concatenates several requests to reach the amount.
     * 
     * @param type		Type of sub reddit, this determines the ordering (e.g. new or mine)
     * @param amount	Amount to retrieve (result <= amount)
     * @param after		After which subreddit (used for pagination, leave null otherwise)
     * @return			List of subreddits
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> get(SubredditType type, int amount, Subreddit after) throws IOException, ParseException {
    	return get(null, type, amount, after);
    }

    /**
     * Get a list of subreddits of a certain subreddit type of a certain amount as a certain user.
     *
     * @param user			User
     * @param type		 	Type of subreddit 
     * @param amount		Amount to retrieve
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     * @return list of Subreddits of that type
     */
    public List<Subreddit> get(User user, SubredditType type, int amount) throws IOException, ParseException {
		return get(user, type, amount, null);
    }
    
    /**
     * Get a list of subreddits of a certain subreddit type of a certain amount.
     *
     * @param type		 	Type of subreddit 
     * @param amount		Amount to retrieve
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     * @return list of Subreddits of that type
     */
    public List<Subreddit> get(SubredditType type, int amount) throws IOException, ParseException {
		return get(null, type, amount);
    }
    
    /**
     * Get a list of subreddits of a certain subreddit type as big as possible as a certain user.
     *
     * @param user			User
     * @param type		 	Type of subreddit 
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     * @return list of Subreddits of that type
     */
    public List<Subreddit> get(User user, SubredditType type) throws IOException, ParseException {
		return get(user, type, APPROXIMATE_MAX_LISTING_SIZE);
    }
    
    /**
     * Get a list of subreddits of a certain subreddit type as big as possible.
     * 
     * @param type Type of subreddit
     * @return List of subreddits of that type
     * @throws IOException		If connection failed
     * @throws ParseException	If parsing failed
     */
    public List<Subreddit> get(SubredditType type) throws IOException, ParseException {
		return get(null, type);
    }

    /**
     * Get a Subreddit by its name as a given user.
     *
     * @param user			User
     * @param subredditName Name of the subreddit to retrieve
     * @return Subreddit object representing the desired subreddit, or NULL if it does not exist
     */
    public Subreddit getByName(User user, String subredditName) throws IOException, ParseException {
    	List<Subreddit> all = search(user, subredditName, 1, null);

    	if (all.size() == 1 && all.get(0).getDisplayName().equalsIgnoreCase(subredditName)) {
    		return all.get(0);
    	}
    	
    	System.err.println("Could not find the subreddit " + subredditName + ".");
        return null;
    }
    
    /**
     * Get a Subreddit by its name
     *
     * @param subredditName name of the subreddit to retrieve
     * @return Subreddit object representing the desired subreddit, or NULL if it does not exist
     */
    public Subreddit getByName(String subredditName) throws IOException, ParseException {
    	return getByName(null, subredditName);
    }

}
