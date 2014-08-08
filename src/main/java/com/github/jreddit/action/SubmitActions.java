package com.github.jreddit.action;

import org.json.simple.JSONObject;

import com.github.jreddit.entity.User;
import com.github.jreddit.exception.ActionFailedException;
import com.github.jreddit.retrieval.ActorDriven;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmitActions implements ActorDriven {

    private RestClient restClient;
    private User user;

    /**
     * Constructor. Global default user (null) is used.
     * @param restClient REST Client instance
     */
    public SubmitActions(RestClient restClient) {
        this.restClient = restClient;
        this.user = null;
    }
    
    /**
     * Constructor.
     * @param restClient REST Client instance
     * @param actor User instance
     */
    public SubmitActions(RestClient restClient, User actor) {
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
     * This function deletes a submission or comment.
     *
     * @param fullName Full name of the thing
     * @param category Category name
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean delete(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.DELETE, user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }

    /**
     * This function comments on a submission or comment with the given full name.
     *
     * @param fullName Full name of the submission or comment
     * @param text The text to comment (can include markdown)
     */
    public boolean comment(String fullname, String text) throws ActionFailedException {
        
    	JSONObject object = (JSONObject) restClient.post(
        		"thing_id=" + fullname + "&text=" + text + "&uh=" + user.getModhash(), 
        		ApiEndpointUtils.COMMENT, 
        		user.getCookie()
        ).getResponseObject();

        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("User is required for the comment action.");
            return false;
        } else {
            return true;
        }
        
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
     * @throws ActionFailedException    	If the action failed
     */
    public boolean submitLink(String title, String link, String subreddit, String captcha_iden, String captcha_sol) throws ActionFailedException {
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
     * @throws ActionFailedException    	If the action failed
     */
    public boolean submitSelfPost(String title, String text, String subreddit, String captcha_iden, String captcha_sol) throws ActionFailedException {
        return submit(title, text, true, subreddit, captcha_iden, captcha_sol);
    }
    
    /**
     * This function submits a link or self post.
     * 
     * @param title      	The title of the submission
     * @param linkOrText 	The link of the submission or text
     * @param selfPost   	If this submission is a self post
     * @param subreddit  	Which subreddit to submit this to
     * @param captcha_iden	Captcha identifier
     * @param captcha_sol	Captcha solution
     * @return Whether the submission succeeded
     * @throws ActionFailedException    	If the action failed
     */
    private boolean submit(String title, String linkOrText, boolean selfPost, String subreddit, String captcha_iden, String captcha_sol) throws ActionFailedException {
        
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
