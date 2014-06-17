package com.github.jreddit.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.Thing;
import com.github.jreddit.captcha.CaptchaDownloader;
import com.github.jreddit.comment.Comment;
import com.github.jreddit.comment.Comments;
import com.github.jreddit.exception.InvalidUserException;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.subreddit.Subreddit;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Sort;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * This class represents a user connected to Reddit.
 *
 * @author Omer Elnour
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Benjamin Jakobus
 * @author Evin Ugur
 * @author Andrei Sfat
 * @author Simon Kassing
 */
public class User {

    private final String username;
    private final RestClient restClient;
    private String modhash, cookie, password;

    /**
     * Create a user.
     * @param restClient REST Client handle
     * @param username User name
     * @param password Password
     */
    public User(RestClient restClient, String username, String password) {
        this.restClient = restClient;
        this.username = username;
        this.password = password;
    }

    /**
     * Get the user name of the user.
     * @return User name
     */
    public String getUsername() {
        return username;
    }

    /**
     * Set the password of the user.
     * @param password Password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieve the modulo hash of the cookie.
     * @return Modulo hash
     */
    public String getModhash() {
        return modhash;
    }

    /**
     * Retrieve the cookie of the user containing all session information.
     * @return Cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * Call this function to connect the user. <br /> By "connect" I mean
     * effectively sending a POST request to reddit and getting the modhash and
     * cookie, which are required for most reddit API functions.
     *
     * @throws IOException If connection fails.
     * @throws ParseException If parsing JSON fails.
     */
    public void connect() throws IOException, ParseException {
        ArrayList<String> hashCookiePair = hashCookiePair(username, password);
        this.modhash = hashCookiePair.get(0);
        this.cookie = hashCookiePair.get(1);
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
    public boolean submitLink(String title, String link, String subreddit, String captcha_iden, String captcha_sol) throws IOException, ParseException {
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
    public boolean submitSelfPost(String title, String text, String subreddit, String captcha_iden, String captcha_sol) throws IOException, ParseException {
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
    public void changePassword(String currentPassword, String newPassword) throws IOException, ParseException {
    	
    	// Make the request
        JSONObject object = (JSONObject) update(currentPassword, "", newPassword).getResponseObject();
        
        // User required
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("Change password failed: please login first.");
        } // Rate limit exceeded
        else if (object.toJSONString().contains(".error.RATELIMIT.field-ratelimit")) {
            System.err.println("Change password failed: you are doing that too much.");
        } // Incorrect password
        else if (object.toJSONString().contains(".error.BAD_PASSWORD")) {
            System.err.println("Change password failed: current password is bad.");
        } else {
            System.out.println("Password successfully changed to " + newPassword + ".");
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
    public Response update(String currentPassword, String email, String newPassword)throws IOException, ParseException {
    	
    	// Format parameters
    	String params = 
    			"api_type=json&curpass=" + currentPassword + 
    			"&dest=http://reddit.com/" + (!email.equals("") ? "&email=" + email : "") + 
    			(!newPassword.equals("") ? "&newpass=" + newPassword + "&verpass=" + newPassword : "")
                + "&uh=" + getModhash();
    	
    	// Post request
    	return restClient.post(params, ApiEndpointUtils.USER_UPDATE, getCookie());
    	
    }

    /**
     * This function logs in to reddit and returns an ArrayList containing a
     * modhash and cookie.
     *
     * @param username The username
     * @param password The password
     * @return An array containing a modhash and cookie
     * @throws IOException    If connection fails
     * @throws ParseException If parsing JSON fails
     */
    private ArrayList<String> hashCookiePair(String username, String password)
            throws IOException, ParseException {
        ArrayList<String> values = new ArrayList<String>();
        JSONObject jsonObject = (JSONObject) restClient.post("api_type=json&user=" + username
                + "&passwd=" + password, String.format(ApiEndpointUtils.USER_LOGIN, username), getCookie()).getResponseObject();
        JSONObject valuePair = (JSONObject) ((JSONObject) jsonObject.get("json")).get("data");

        values.add(valuePair.get("modhash").toString());
        values.add(valuePair.get("cookie").toString());

        return values;
    }

    /**
     * Get info about the currently authenticated user.
     * Corresponds to the API "/me.json" method
     *
     * @return <code>UserInfo</code>object containing the user's info, or null if the retrieval fails
     */
    public UserInfo getUserInformation() {
        if (cookie == null || modhash == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }

        JSONObject jsonObject = (JSONObject) restClient.get(ApiEndpointUtils.USER_INFO, getCookie()).getResponseObject();
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
    private boolean submit(String title, String linkOrText, boolean selfPost, String subreddit, String captcha_iden, String captcha_sol) throws IOException, ParseException {
        
    	// Parameters
    	String params =         		
        		"title=" 							+ title 							+ 
        		(selfPost ? "&text=" : "&url=") 	+ linkOrText 						+ 
        		"&sr=" 								+ subreddit 						+ 
        		"&kind=" 							+ (selfPost ? "self" : "link") 		+ 
        		"&uh=" 								+ getModhash() 						+ 
        		"&iden=" 							+ captcha_iden						+
        		"&captcha=" 						+ captcha_sol;
        
    	// Make the request
    	JSONObject object = (JSONObject) restClient.post(params,ApiEndpointUtils.USER_SUBMIT, getCookie()).getResponseObject();
    	
        // User required
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("User submission failed: please login first.");
            return false;
        } // Rate limit exceeded
        else if (object.toJSONString().contains(
                ".error.RATELIMIT.field-ratelimit")) {
            System.err.println("User submission failed: you are doing that too much.");
            return false;
        } // Already submitted link
        else if (object.toJSONString().contains(
                ".error.ALREADY_SUB.field-url")) {
            System.err.println("User submission failed: that link has already been submitted.");
            return false;
        } // Captcha problem
        else if (object.toJSONString().contains(".error.BAD_CAPTCHA.field-captcha")) {
            System.err.println("User submission failed: the catpcha field was incorrect.");
            return false;
        }
        else { // Success
            System.out.println("User submission succesful.");
            return true;
        }
    	
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @return <code>List</code> of top 500 comments made by this user, or an empty list if the user does not have
     * any comments. In case of an invalid user instance, it returns <code>null</code>.
     */
    public List<Comment> comments() {
        try {
            return new Comments(restClient).comments(username);
        } catch (InvalidUserException e) {
            return null;
        }
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose submissions you want
     *                 to retrieve.
     * @return <code>List</code> of submissions made by this user.
     */
    public List<Submission> submissions(String username) {
        List<Submission> submissions = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) restClient.get(String.format(ApiEndpointUtils.USER_SUBMISSIONS, username), null).getResponseObject();
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;

            for (Object aChildren : children) {
                // Get the object containing the comment
                obj = (JSONObject) aChildren;
                obj = (JSONObject) obj.get("data");
                //add a new Submission to the list
                submissions.add(new Submission(obj));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return submissions;
    }

    /**
     * Returns a list of submissions that this user liked.
     *
     * @return List of liked links with default sorting.
     */
    public List<Submission> getLikedSubmissions() {
        return getLikedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of submissions that this user liked with a Reddit sort
     *
     * @param sort Which sort you'd like to apply
     * @return List of liked submissions with a Reddit sort
     */
    public List<Submission> getLikedSubmissions(Sort sort) {
        return getUserSubmissions("liked", sort);
    }

    /**
     * Returns a list of submissions that this user chose to hide. with the default sorting
     *
     * @return List of disliked links.
     */
    public List<Submission> getHiddenSubmissions() {
        return getHiddenSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that this user chose to hide with a specified sorting
     *
     * @param sort Which sort you'd like to apply
     * @return List of hidden Submissions with a Reddit sort
     */
    public List<Submission> getHiddenSubmissions(Sort sort) {
        return getUserSubmissions("hidden", sort);
    }

    /**
     * Returns a list of Submissions that the user saved with the default sort
     *
     * @return List of saved links
     */
    public List<Submission> getSavedSubmissions() {
        return getSavedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that the user saved with a specified sorting
     *
     * @param sort Which sort you'd like to apply
     * @return List of saved links with a Reddit sort
     */
    public List<Submission> getSavedSubmissions(Sort sort) {
        return getUserSubmissions("saved", sort);
    }

    /**
     * Returns a list of Submissions that the user submitted with the default Reddit sort
     *
     * @return List of submitted Submissions
     */
    public List<Submission> getSubmissions() {
        return getSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that the user submitted with a specified Reddit sort
     *
     * @return List of submitted Submissions with a specified Reddit sort
     */
    public List<Submission> getSubmissions(Sort sort) {
        return getUserSubmissions("submitted", sort);
    }

    /**
     * Returns a list of submissions that this user disliked with the default Reddit sort
     *
     * @return List of disliked links.
     */
    public List<Submission> getDislikedSubmissions() {
        return getDislikedSubmissions(Sort.HOT);
    }

    /**
     * Returns a list of Submissions that this user disliked with a specified Reddit sort
     *
     * @param sort Which sort you'd like to apply
     * @return List of disliked sorts with a specified sort
     */
    public List<Submission> getDislikedSubmissions(Sort sort) {
        return getUserSubmissions("disliked", sort);
    }

    /**
     * Return a list of Submissions that are in the user's overview with the default Reddit sort ('Hot')
     * @return List of submissions in the user overview.
     */
    public List<Submission> getUserOverview() {
        return getUserSubmissions("overview", Sort.HOT);
    }

    /**
     * private function used to get submissions that a user interacts with on Reddit
     *
     * @param where place of Submission - see http://www.reddit.com/dev/api#GET_user_{username}_{where}
     * @return Submissions from the specified location, null if the location is invalid
     */
    private List<Submission> getUserSubmissions(String where, Sort sort) {
        //valid arguments for where the Submission can come from
        final String[] LOCATIONS = {
                //TODO: not all of these JSONs return something that can be processed purely into a Submission class, until those are taken care of, I commented them out
                /*"overview",*/ "submitted", /*"comments",*/ "liked", "disliked", "hidden", "saved"
        };
        //check to see if we have a valid location
        boolean valid = false;
        for (String LOCATION : LOCATIONS) {
            valid = where.equals(LOCATION);
            if (valid) {
                break;
            }

        }
        if (!valid) {
            System.err.println(where + " is an invalid location");
            return null;
        }

        //if we got this far, the location is valid
        List<Submission> submissions = new ArrayList<Submission>();
        try {
            JSONObject object =
                    (JSONObject) restClient.get(String.format(ApiEndpointUtils.USER_SUBMISSIONS_INTERACTION,
                            username, where, sort.getValue()), cookie).getResponseObject();
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            for (Object aChildren : children) {
                obj = (JSONObject) aChildren;
                obj = (JSONObject) obj.get("data");
                submissions.add(new Submission(obj));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    /**
     * Returns a list of Subreddits to which the user is subscribed.
     *
     * @return List of Subreddits
     */
    public List<Subreddit> getSubscribed() {
        List<Subreddit> subscribed = new ArrayList<Subreddit>(1000);
        JSONObject object = (JSONObject) restClient.get(ApiEndpointUtils.USER_GET_SUBSCRIBED, cookie).getResponseObject();

        JSONObject rawData = (JSONObject) object.get("data");
        JSONArray subreddits = (JSONArray) rawData.get("children");

        for (Object subreddit : subreddits) {
            JSONObject obj = (JSONObject) subreddit;
            obj = (JSONObject) obj.get("data");
            Subreddit sub = new Subreddit(obj);
            subscribed.add(sub);
        }

        return subscribed;
    }
}
