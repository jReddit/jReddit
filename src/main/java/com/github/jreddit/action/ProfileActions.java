package com.github.jreddit.action;

import org.json.simple.JSONObject;

import com.github.jreddit.entity.User;
import com.github.jreddit.entity.UserInfo;
import com.github.jreddit.exception.ActionFailedException;
import com.github.jreddit.retrieval.ActorDriven;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * User actions.
 * 
 * @author Omer Elnour
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Benjamin Jakobus
 * @author Evin Ugur
 * @author Andrei Sfat
 * @author Simon Kassing
 */
public class ProfileActions implements ActorDriven {

    private RestClient restClient;
    private User user;

    /**
     * Constructor. Global default user (null) is used.
     * @param restClient REST Client instance
     */
    public ProfileActions(RestClient restClient) {
        this.restClient = restClient;
        this.user = null;
    }
    
    /**
     * Constructor.
     * @param restClient REST Client instance
     * @param actor User instance
     */
    public ProfileActions(RestClient restClient, User actor) {
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
     * This function changes user's password
     * Requires authentication.
     *
     * @param currentPassword	Current password
     * @param newPassword		New password
     * @throws ActionFailedException    	If the action failed
     */
    public boolean changePassword(String currentPassword, String newPassword) throws ActionFailedException {
    	
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
     * @throws ActionFailedException    	If the action failed
     */
    public Response update(String currentPassword, String email, String newPassword) throws ActionFailedException {
    	
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
     * @throws ActionFailedException    	If the action failed
     */
    public UserInfo getUserInformation() throws ActionFailedException {
        if (user.getCookie() == null || user.getModhash() == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }

        JSONObject jsonObject = (JSONObject) restClient.get(ApiEndpointUtils.USER_INFO, user.getCookie()).getResponseObject();
        JSONObject info = (JSONObject) jsonObject.get("data");

        return new UserInfo(info);
    }

    /**
     * Returns miscellaneous info about the user
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @return UserInfo object consisting of information about the user identified by "username".
     * @throws ActionFailedException    	If the action failed
     */
    public UserInfo about(String username) throws ActionFailedException {

        // Send GET request to get the account overview
        JSONObject object = (JSONObject) restClient.get(String.format(ApiEndpointUtils.USER_ABOUT, username), null).getResponseObject();
        JSONObject data = (JSONObject) object.get("data");

        // Init account info wrapper
        return data != null ? new UserInfo(data) : null;
    }
    
}
