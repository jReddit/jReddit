package com.github.jreddit.user;

import com.github.jreddit.CommentSort;
import com.github.jreddit.Sort;
import com.github.jreddit.Thing;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.subreddit.Subreddit;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a user connected to Reddit.
 *
 * @author Omer Elnour
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Benjamin Jakobus
 * @author Evin Ugur
 * @author Andrei Sfat
 */
public class User extends Thing {

    private String username, password;
    private String modhash, cookie;

    public User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModhash() {
        return modhash;
    }

    public String getCookie() {
        return cookie;
    }

    /**
     * Call this function to connect the user. <br /> By "connect" I mean
     * effectively sending a POST request to reddit and getting the modhash and
     * cookie, which are required for most reddit API functions.
     *
     * @throws Exception If connection fails.
     */
    public void connect() throws Exception {
        ArrayList<String> hashCookiePair = hashCookiePair(username, password);
        this.modhash = hashCookiePair.get(0);
        this.cookie = hashCookiePair.get(1);
    }

    /**
     * This function submits a link to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param link      The link to the submission
     * @param subreddit The subreddit to submit to
     * @throws IOException    If connection fails
     * @throws ParseException If JSON Parsing fails
     */
    public void submitLink(String title, String link, String subreddit)
            throws IOException, ParseException {
        JSONObject object = submit(title, link, false, subreddit);
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("Please login first.");
        } else if (object.toJSONString().contains(
                ".error.RATELIMIT.field-ratelimit")) {
            System.err.println("You are doing that too much.");
        } else if (object.toJSONString().contains(
                ".error.ALREADY_SUB.field-url")) {
            System.err.println("That link has already been submitted.");
        } else {
            System.out.println("Link submitted to "
                    + ((JSONArray) ((JSONArray) ((JSONArray) object.get("jquery")).get(16)).get(3)).get(0));
        }
    }

    /**
     * This function submits a self post to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param text      The text of the submission
     * @param subreddit The subreddit to submit to
     * @throws IOException    If connection fails
     * @throws ParseException If JSON Parsing fails
     */
    public void submitSelfPost(String title, String text, String subreddit)
            throws IOException, ParseException {
        JSONObject object = submit(title, text, true, subreddit);
        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            System.err.println("Please login first.");
        } else if (object.toJSONString().contains(
                ".error.RATELIMIT.field-ratelimit")) {
            System.err.println("You are doing that too much.");
        } else if (object.toJSONString().contains(
                ".error.ALREADY_SUB.field-url")) {
            System.err.println("That link has already been submitted.");
        } else {
            System.out.println("Self post submitted to "
                    + ((JSONArray) ((JSONArray) ((JSONArray) object.get("jquery")).get(10)).get(3)).get(0));
        }
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
        JSONObject jsonObject = Utils.post("api_type=json&user=" + username
                + "&passwd=" + password, String.format(ApiEndpointUtils.USER_LOGIN, username), getCookie());
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

        JSONObject jsonObject = (JSONObject) Utils.get(ApiEndpointUtils.USER_INFO, getCookie());
        JSONObject info = (JSONObject) jsonObject.get("data");

        return new UserInfo(info);
    }

    /**
     * Returns misc info about the user
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @return UserInfo object consisting of information about the user identified by "username".
     */
    public static UserInfo about(String username) {

        // Send GET request to get the account overview
        JSONObject object = (JSONObject) Utils.get(String.format(ApiEndpointUtils.USER_ABOUT, username), null);
        JSONObject data = (JSONObject) object.get("data");

        // Init account info wrapper
        return data != null ? new UserInfo(data) : null;
    }

    /**
     * This function submits a link or self post.
     *
     * @param title      The title of the submission
     * @param linkOrText The link of the submission or text
     * @param selfPost   If this submission is a self post
     * @param subreddit  Which subreddit to submit this to
     * @return A JSONObject
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    private JSONObject submit(String title, String linkOrText, boolean selfPost, String subreddit) throws IOException, ParseException {
        return Utils.post("title=" + title + "&" + (selfPost ? "text" : "url")
                + "=" + linkOrText + "&sr=" + subreddit + "&kind="
                + (selfPost ? "self" : "link") + "&uh=" + getModhash(),
                ApiEndpointUtils.USER_SUBMIT, getCookie());
    }

    // TODO: Move comment-related and submission-related methods from the User class

    /**
     * Returns a list of submissions made by this user.
     *
     * @return <code>List</code> of submissions made by this user.
     */
    public List<Comment> comments() {
        return User.comments(username);
    }


    public static List<Comment> comments(String username) {
        return comments(username, CommentSort.NEW);
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose comments you want
     *                 to retrieve.
     * @return <code>List</code> of top 500 comments made by this user.
     */
    public static List<Comment> comments(String username, CommentSort commentSort) {
        // List of submissions made by this user
        List<Comment> comments = new ArrayList<Comment>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object =
                    (JSONObject) Utils.get(String.format(ApiEndpointUtils.USER_COMMENTS,
                            username, commentSort.getValue()), null);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            Comment c;
            for (Object aChildren : children) {
                // Get the object containing the comment
                obj = (JSONObject) aChildren;
                obj = (JSONObject) obj.get("data");

                // Create a new comment
                c = new Comment(Utils.toString(obj.get("body")), Utils.toString(obj.get("edited")),
                        Utils.toString(obj.get("created_utc")), Utils.toString(obj.get("replies")),
                        Integer.parseInt(Utils.toString(obj.get("ups"))),
                        Integer.parseInt(Utils.toString(obj.get("downs"))));

                // Add it to the submissions list
                comments.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Return the submissions
        return comments;
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose submissions you want
     *                 to retrieve.
     * @return <code>List</code> of submissions made by this user.
     */
    public static List<Submission> submissions(String username) {
        // List of submissions made by this user
        List<Submission> submissions = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get(String.format(ApiEndpointUtils.USER_SUBMISSIONS, username), null);
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
        } catch (Exception e) {
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
                    (JSONObject) Utils.get(String.format(ApiEndpointUtils.USER_SUBMISSIONS_INTERACTION,
                            username, where, sort.getValue()), cookie);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            for (Object aChildren : children) {
                obj = (JSONObject) aChildren;
                obj = (JSONObject) obj.get("data");
                submissions.add(new Submission(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return submissions;
    }

    /**
     * Returns a list of Subreddits to which the user is subscribed.
     * @return List of Subreddits
     */
    public List<Subreddit> getSubscribed() {
        List<Subreddit> subscibed = new ArrayList<Subreddit>(1000);
        JSONObject object = (JSONObject) Utils.get(ApiEndpointUtils.USER_GET_SUBSCRIBED, cookie);

        JSONObject rawData = (JSONObject) object.get("data");
        JSONArray subreddits = (JSONArray) rawData.get("children");

        for (Object subreddit : subreddits) {
            JSONObject obj = (JSONObject) subreddit;
            obj = (JSONObject) obj.get("data");
            Subreddit sub = new Subreddit(obj.get("display_name").toString(),
                    obj.get("display_name").toString(), obj.get("title").toString(),
                    obj.get("url").toString(), obj.get("created").toString(),
                    obj.get("created_utc").toString(),
                    Boolean.parseBoolean(obj.get("over18").toString()),
                    Integer.parseInt(obj.get("subscribers").toString()),
                    obj.get("id").toString(), obj.get("description").toString());
            subscibed.add(sub);
        }

        return subscibed;
    }
}
