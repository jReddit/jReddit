package com.github.jreddit.action;

import org.json.simple.JSONObject;

import com.github.jreddit.entity.User;
import com.github.jreddit.exception.ActionFailedException;
import com.github.jreddit.retrieval.ActorDriven;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

public class MarkActions implements ActorDriven {

    private RestClient restClient;
    private User user;

    /**
     * Constructor. Global default user (null) is used.
     * @param restClient REST Client instance
     */
    public MarkActions(RestClient restClient) {
        this.restClient = restClient;
        this.user = null;
    }
    
    /**
     * Constructor.
     * @param restClient REST Client instance
     * @param actor User instance
     */
    public MarkActions(RestClient restClient, User actor) {
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
     * Mark a post as NSFW 
     * 
     * @param fullName Full name of the comment or submission
     * @throws ActionFailedException If the action failed
    */
    public boolean markNSFW(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }

    /** 
     * Unmark a post as NSFW 
     * 
     * @param fullName Full name of the comment or submission
     * @throws ActionFailedException If the action failed
    */
    public boolean unmarkNSFW(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
    }

    /**
     * This function saves a submission with a specific category (Reddit Gold feature).
     *
     * @param fullName Full name of the thing
     * @param category Category name
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean save(String fullName, String category) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
                "category=" + category + "&id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SAVE, user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }
    
    /**
     * This function reports a submission or comment to the moderator of the subreddit it is contained in.
     * 
     * @param fullName Full name of the thing
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean report(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.REPORT, user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }

    /**
     * This function saves a submission or comment with the given full name.
     * @param fullName Full name of the thing
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean save(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
        		"id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SAVE, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }

    /**
     * This function unsaves a submission or comment with the given full name.
     * @param fullName Full name of the thing
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean unsave(String fullName) throws ActionFailedException {
    	JSONObject object = (JSONObject) restClient.post(
        		"id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.UNSAVE, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
    }
    
    /**
     * This function hides a submission or comment with the given full name.
     * @param fullName Full name of the thing
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean hide(String fullName) throws ActionFailedException {
    	
    	JSONObject object = (JSONObject) restClient.post(
        		"id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.HIDE, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
        
    }

    /**
     * This function unhide a submission or comment with the given full name.
     * @param fullName Full name of the thing
     *
     * @throws ActionFailedException If the action failed
     */
    public boolean unhide(String fullName) throws ActionFailedException {
    	JSONObject object = (JSONObject) restClient.post(
        		"id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.UNHIDE, 
                user.getCookie()
        ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
    }
    
    /**
     * Vote for a comment or submission with the given full name.
     * 
     * @param dir 	Direction (precondition: either -1, 0 or 1)
     * @return Response from reddit.
     */
    public boolean vote(String fullName, int dir) throws ActionFailedException {
        
    	if (dir < -1 || dir > 1) {
    		throw new IllegalArgumentException("Vote direction needs to be -1 or 1.");
    	}
    	
    	JSONObject object = (JSONObject) restClient.post(
                "id=" 		+ fullName + 
                "&dir=" 	+ dir + 
                "&uh=" 		+ user.getModhash(),
                ApiEndpointUtils.VOTE, 
                user.getCookie()
         ).getResponseObject();
    	
        return object.toJSONString().length() == 2;
    	
    }
    
}
