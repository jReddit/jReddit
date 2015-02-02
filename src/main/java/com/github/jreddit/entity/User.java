package com.github.jreddit.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;
import com.github.jreddit.retrieval.Subreddits;

/**
 * This class represents a user connected to Reddit.
 *
 * Implement: gildSelf, giveGold, 
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
     * This function logs in to reddit and returns an ArrayList containing a
     * modhash and cookie.
     *
     * @param username The username
     * @param password The password
     * @return An array containing a modhash and cookie
     * @throws IOException    If connection fails
     * @throws ParseException If parsing JSON fails
     */
    private ArrayList<String> hashCookiePair(String username, String password) throws IOException, ParseException {
        ArrayList<String> values = new ArrayList<String>();
        JSONObject jsonObject = (JSONObject) restClient.post("api_type=json&user=" + username + "&passwd=" + password, String.format(ApiEndpointUtils.USER_LOGIN, username), getCookie()).getResponseObject();
        JSONObject valuePair = (JSONObject) ((JSONObject) jsonObject.get("json")).get("data");

        values.add(valuePair.get("modhash").toString());
        values.add(valuePair.get("cookie").toString());

        return values;
    }


    /**
     * This function returns all the subreddits the user is subscribed to.
     * @return A list of subreddit objects
     * @throws RetrievalFailedException    If retrieval of subreddits fails
     * @throws RedditError
     */
    public List<Subreddit> getSubscribed() throws RetrievalFailedException, RedditError {
        if (this.getCookie() == null || this.getModhash() == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }
        Subreddits sub = new Subreddits(restClient, this);

        // Automatically returns maximum allowed number of objects, should this be a parameter?
        return sub.parse(ApiEndpointUtils.USER_GET_SUBSCRIBED + "?&limit=100");
    }

    /**
     * This function returns all the subreddits the user is an approved contributor to.
     * @return A list of subreddit objects
     * @throws RetrievalFailedException    If retrieval of subreddits fails
     * @throws RedditError
     */
    public List<Subreddit> getContributedTo() throws RetrievalFailedException, RedditError {
        if (this.getCookie() == null || this.getModhash() == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }
        Subreddits sub = new Subreddits(restClient, this);

        // Automatically returns maximum allowed number of objects, should this be a parameter?
        return sub.parse(ApiEndpointUtils.USER_GET_CONTRIBUTED_TO + "?&limit=100");
    }

    /**
     * This function returns all the subreddits the user is a moderator of.
     * @return A list of subreddit objects
     * @throws RetrievalFailedException    If retrieval of subreddits fails
     * @throws RedditError
     */
    public List<Subreddit> getModeratorOf() throws RetrievalFailedException, RedditError {
        if (this.getCookie() == null || this.getModhash() == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }
        Subreddits sub = new Subreddits(restClient, this);

        // Automatically returns maximum allowed number of objects, should this be a parameter?
        return sub.parse(ApiEndpointUtils.USER_GET_MODERATOR_OF + "?&limit=100");
    }

}
