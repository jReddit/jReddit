package com.github.jreddit.action;

import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.User;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

public class UserActions {

    private RestClient restClient;
    private User user;

    /**
     * Constructor. Global default user (null) is used.
     * @param restClient REST Client instance
     */
    public UserActions(RestClient restClient) {
        this.restClient = restClient;
        this.user = null;
    }
    
    /**
     * Constructor.
     * @param restClient REST Client instance
     * @param actor User instance
     */
    public UserActions(RestClient restClient, User actor) {
    	this.restClient = restClient;
        this.user = actor;
    }
    
    /**
     * Switch the current user for the new user who will
     * be used when invoking retrieval requests.
     * 
     * @param new_actor New user
     */
    public void switchActor(User new_actor) {
    	this.user = new_actor;
    }
    

    /**
     * This function submits a link to the specified subreddit.
     * Requires authentication.
     *
     * @param title     		The title of the submission
     * @param link      		The link to the submission
     * @param subreddit 		The subreddit to submit to
     * @param captcha_iden		Captcha identifier
     * @param captcha_sol		Captcha solution
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON Parsing fails
     */
    public boolean submitLink(String title, String link, String subreddit, String captcha_iden, String captcha_sol) {
        return submit(title, link, false, subreddit, captcha_iden, captcha_sol);
    }

    /**
     * This function submits a self post to the specified subreddit.
     * Requires authentication.
     *
     * @param title     		The title of the submission
     * @param text      		The text of the submission
     * @param subreddit 		The subreddit to submit to
     * @param captcha_iden		Captcha identifier
     * @param captcha_sol		Captcha solution
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON Parsing fails
     */
    public boolean submitSelfPost(String title, String text, String subreddit, String captcha_iden, String captcha_sol) {
        return submit(title, text, true, subreddit, captcha_iden, captcha_sol);
    }

    /**
     * This function changes user's password
     * Requires authentication.
     *
     * @param currentPassword	Current password
     * @param newPassword		New password
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON Parsing fails
     */
    public boolean changePassword(String currentPassword, String newPassword) {
    	
    	// Make the request
        JSONObject object = (JSONObject) update(currentPassword, "", newPassword).getResponseObject();
        
        // User required
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("Change password failed: please login first.");
            return false;
        } // Rate limit exceeded
        else if (object.toJSONString().contains(".error.RATELIMIT.field-ratelimit")) {
            System.err.println("Change password failed: you are doing that too much.");
            return false;
        } // Incorrect password
        else if (object.toJSONString().contains(".error.BAD_PASSWORD")) {
            System.err.println("Change password failed: current password is bad.");
            return false;
        } else {
        	return true;
        }
        
    }
    
    /**
     * This function updates user's profile.
     * Requires authentication.
     *
     * @param currentPassword   Current password
     * @param email				New e-mail address (can be empty)
     * @param newPassword		New password
     * @throws IOException    	If connection fails
     * @throws ParseException 	If JSON Parsing fails
     */
    public Response update(String currentPassword, String email, String newPassword) {
    	
    	// Format parameters
    	String params = 
    			"api_type=json"
    			+ "&curpass=" + currentPassword + 
    			"&dest=http://reddit.com/" + (!email.equals("") ? "&email=" + email : "") + 
    			(!newPassword.equals("") ? "&newpass=" + newPassword + "&verpass=" + newPassword : "")
                + "&uh=" + user.getModhash();
    	
    	// Post request
    	return restClient.post(params, ApiEndpointUtils.USER_UPDATE, user.getCookie());
    	
    }
    

    /**
     * Get info about the currently authenticated user.
     * Corresponds to the API "/me.json" method
     *
     * @return <code>UserInfo</code>object containing the user's info, or null if the retrieval fails
     */
    public UserInfo getUserInformation() {
        if (user.getCookie() == null || user.getModhash() == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }

        JSONObject jsonObject = (JSONObject) restClient.get(ApiEndpointUtils.USER_INFO, user.getCookie()).getResponseObject();
        JSONObject info = (JSONObject) jsonObject.get("data");

        return new UserInfo(info);
    }

    /**
     * Returns misc info about the user
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @return UserInfo object consisting of information about the user identified by "username".
     */
    public UserInfo about(String username) {

        // Send GET request to get the account overview
        JSONObject object = (JSONObject) restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, username), null).getResponseObject();
        JSONObject data = (JSONObject) object.get("data");

        // Init account info wrapper
        return data != null ? new UserInfo(data) : null;
    }

    /**
     * This function submits a link or self post.
     * TODO: throw exceptions instead of returning a boolean? Or error codes?
     *
     * @param title      	The title of the submission
     * @param linkOrText 	The link of the submission or text
     * @param selfPost   	If this submission is a self post
     * @param subreddit  	Which subreddit to submit this to
     * @param captcha_iden	Captcha identifier
     * @param captcha_sol	Captcha solution
     * @return Was the submission a success?
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    private boolean submit(String title, String linkOrText, boolean selfPost, String subreddit, String captcha_iden, String captcha_sol) {
        
    	// Parameters
    	String params =         		
        		"title=" 							+ title 							+ 
        		(selfPost ? "&text=" : "&url=") 	+ linkOrText 						+ 
        		"&sr=" 								+ subreddit 						+ 
        		"&kind=" 							+ (selfPost ? "self" : "link") 		+ 
        		"&uh=" 								+ user.getModhash() 				+ 
        		"&iden=" 							+ captcha_iden						+
        		"&captcha=" 						+ captcha_sol;
        
    	// Make the request
    	JSONObject object = (JSONObject) restClient.post(params,ApiEndpointUtils.USER_SUBMIT, user.getCookie()).getResponseObject();
    	
        // User required
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
        	
            System.err.println("User submission failed: please login first.");
            return false;
            
        } // Rate limit exceeded
        else if (object.toJSONString().contains(".error.RATELIMIT.field-ratelimit")) {
        	
            System.err.println("User submission failed: you are doing that too much.");
            return false;
            
        } // Already submitted link
        else if (object.toJSONString().contains(".error.ALREADY_SUB.field-url")) {
        	
            System.err.println("User submission failed: that link has already been submitted.");
            return false;
            
        } // Captcha problem
        else if (object.toJSONString().contains(".error.BAD_CAPTCHA.field-captcha")) {
        	
            System.err.println("User submission failed: the catpcha field was incorrect.");
            return false;
            
        }
        else { // Success
            return true;
        }
    	
    }
    
}
