package com.omrlnr.jreddit.user;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.Thing;
import com.omrlnr.jreddit.submissions.Submission;
import com.omrlnr.jreddit.utils.Utils;

import java.util.List;

/**
 * This class represents a user connected to reddit.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class User extends Thing {

    private String username, password;
    private String modhash, cookie;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
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
     * This functions returns true if this user has unread mail.
     *
     * @return This user has mail or not
     * @throws ParseException If JSON parsing fails
     * @throws IOException    If connection fails
     */
    public boolean hasMail() throws IOException, ParseException {
        return Boolean.parseBoolean(info().get("has_mail").toString());
    }

    /**
     * This function returns the Unix time that the user's account was created.
     *
     * @return Unix time that the user's account was created
     * @throws NumberFormatException If the "created" property isn't a double
     * @throws IOException           If connection fails
     * @throws ParseException        If JSON parsing fails
     */
    public double created() throws IOException, ParseException {
        return Double.parseDouble(info().get("created").toString());
    }

    /**
     * This function returns the Unix time (in UTC/Coordinated Universal Time)
     * that the user's account was created.
     *
     * @return Unix time that the user's account was created in UTC
     * @throws NumberFormatException If the "created_utc" property isn't a
     *                               double
     * @throws IOException           If connection fails
     * @throws ParseException        If JSON parsing fails
     */
    public double createdUTC() throws IOException, ParseException {
        return Double.parseDouble(info().get("created_utc").toString());
    }

    /**
     * This function returns the amount of link linkKarma this user has. <br />
     * Returns int because I doubt anyone has more than 2,147,483,647 link
     * linkKarma.
     *
     * @return Link Karma
     * @throws NumberFormatException If the "link_karma" property isn't an
     *                               integer
     * @throws IOException           If connection fails
     * @throws ParseException        If JSON parsing fails
     */
    public int linkKarma() throws IOException, ParseException {
        // Return the link linkKarma
        return Integer.parseInt(toString(info().get("link_karma")));
    }

    /**
     * This function returns the amount of comment linkKarma this user has. <br
     * /> Returns int because I doubt anyone has more than 2,147,483,647 comment
     * linkKarma.
     *
     * @return Comment Karma
     * @throws NumberFormatException If the "link_karma" property isn't an
     *                               inteher
     * @throws IOException           If connection fails
     * @throws ParseException        If JSON parsing fails
     */
    public int commentKarma() throws IOException, ParseException {
        // Return comment karma
        return Integer.parseInt(toString(info().get("comment_karma")));
    }

    /**
     * This functions returns true if this user has a gold account.
     *
     * @return This user has a gold account or not
     * @throws ParseException If JSON parsing fails
     * @throws IOException    If connection fails
     */
    public boolean isGold() throws IOException, ParseException {
        return Boolean.parseBoolean(info().get("is_gold").toString());
    }

    /**
     * This functions returns true if this user is a reddit moderator
     * (apparently this means a moderator of any subreddit).
     *
     * @return This user is a moderator or not
     * @throws ParseException If JSON parsing fails
     * @throws IOException    If connection fails
     */
    public boolean isMod() throws IOException, ParseException {
        return Boolean.parseBoolean(info().get("is_mod").toString());
    }

    /**
     * This function returns the user's ID. <br /> The user's ID. This is only
     * used internally, <b>right</b>?
     *
     * @return This user's ID
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public String id() throws IOException, ParseException {
        return info().get("id").toString();
    }

    /**
     * This functions returns true if this user has unread moderator mail.
     *
     * @return This user has unread moderator mail or not
     * @throws ParseException If JSON parsing fails
     * @throws IOException    If connection fails
     */
    public boolean hasModMail() throws IOException, ParseException {
        return Boolean.parseBoolean(info().get("has_mod_mail").toString());
    }

    /**
     * Returns the user's username.
     *
     * @return The user's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the user's password.
     *
     * @return The user's password.
     */
    public String getPassword() {
        return password;
    }

    public String getModhash() {
        return modhash;
    }

    public String getCookie() {
        return cookie;
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
                + "&passwd=" + password, new URL(
                "http://www.reddit.com/api/login/" + username), getCookie());
        JSONObject valuePair = (JSONObject) ((JSONObject) jsonObject.get("json")).get("data");

        values.add(valuePair.get("modhash").toString());
        values.add(valuePair.get("cookie").toString());

        return values;
    }

    /**
     * This function returns a "response" (me.json) JSON data containing info
     * about the user. <br />
     *
     * @return JSON data containing info about the user
     */
    private JSONObject info() throws IOException, ParseException {
        if (cookie == null || modhash == null) {
            System.err.printf("Please invoke the \"connect\" function before attempting to call any other API functions.");
            Runtime.getRuntime().exit(-1);
        }

        Object obj = Utils.get("", new URL(
                "http://www.reddit.com/api/me.json"), getCookie());
        JSONObject jsonObject = (JSONObject) obj;
        return (JSONObject) jsonObject.get("data");
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
    private JSONObject submit(String title, String linkOrText,
                              boolean selfPost, String subreddit) throws IOException,
            ParseException {
        return Utils.post("title=" + title + "&" + (selfPost ? "text" : "url")
                + "=" + linkOrText + "&sr=" + subreddit + "&kind="
                + (selfPost ? "link" : "self") + "&uh=" + getModhash(),
                new URL("http://www.reddit.com/api/submit"), getCookie());
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @return <code>List</code> of submissions made by this user.
     * @author Benjamin Jakobus
     */
    public List<Comment> comments() {
        // Return disliked
        return User.comments(username);
    }

    /**
     * Returns misc info about the user (which consists of comment karma, mod
     * mail identifier, created timestamp, gold member identifier, mod
     * identifier, link karma and mail identifier).
     *
     * @param username The username of the user whose account info you want
     *                 to retrieve.
     * @return Misc info about the user.
     * @author Benjamin Jakobus
     */
    public static UserInfo about(String username) {
        // Wrapper used to hold misc account info
        UserInfo info = null;
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/about.json"), null);
            JSONObject data = (JSONObject) object.get("data");

            // Init account info wrapper
            info = new UserInfo(Integer.parseInt(toString(data.get("comment_karma"))),
                    Integer.parseInt(toString(data.get("link_karma"))),
                    Float.parseFloat(toString(data.get("created_utc"))),
                    Boolean.parseBoolean(toString(data.get("is_gold"))),
                    Boolean.parseBoolean(toString(data.get("is_mod"))),
                    Boolean.parseBoolean(toString(data.get("has_mail"))),
                    Boolean.parseBoolean(toString(data.get("has_mod_mail"))));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return info
        return info;
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose comments you want
     *                 to retrieve.
     * @return <code>List</code> of disliked made by this user.
     * @author Benjamin Jakobus
     */
    public static List<Comment> comments(String username) {
        // List of submissions made by this user
        List<Comment> comments = new ArrayList<Comment>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/comments.json"), null);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            Comment c;
            for (int i = 0; i < children.size(); i++) {
                // Get the object containing the comment
                obj = (JSONObject) children.get(i);
                obj = (JSONObject) obj.get("data");

                // Create a new comment
                c = new Comment(toString(obj.get("body")), toString(obj.get("edited")),
                        toString(obj.get("created_utc")), toString(obj.get("replies")),
                        Integer.parseInt(toString(obj.get("ups"))),
                        Integer.parseInt(toString(obj.get("downs"))));

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
     * @author Benjamin Jakobus
     */
    public static List<Submission> submissions(String username) {
        // List of submissions made by this user
        List<Submission> submissions = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/submitted.json"), null);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;

            Submission s;

            for (int i = 0; i < children.size(); i++) {
                // Get the object containing the comment
                obj = (JSONObject) children.get(i);
                obj = (JSONObject) obj.get("data");

                // Create a new submission
                s = new Submission();
                s.setAuthor(toString(obj.get("author")));
                s.setTitle(toString(obj.get("title")));
                s.setNSFW(Boolean.parseBoolean(toString(obj.get("over_18"))));
                s.setCreatedUTC(Long.parseLong(toString(obj.get("created_utc"))));
                s.setDownVotes(Integer.parseInt(toString(obj.get("downs"))));
                s.setName(toString(obj.get("name")));
                s.setScore(Integer.parseInt(toString(obj.get("score"))));
                s.setUpVotes(Integer.parseInt(toString(obj.get("ups"))));
                s.setCommentCount(Integer.parseInt(toString(obj.get("num_comments"))));
                // Add it to the submissions list
                submissions.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return submissions;
    }

    /**
     * Returns a list of submissions that this user disliked.
     *
     * @return List of disliked links.
     * @author Benjamin Jakobus
     */
    public List<Submission> liked() {
        // List of submissions made by this user
        List<Submission> liked = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/liked.json"), cookie);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            Submission s;
            for (int i = 0; i < children.size(); i++) {
                // Get the object containing the comment
                obj = (JSONObject) children.get(i);
                obj = (JSONObject) obj.get("data");

                // Create a new comment
                s = new Submission();
                s.setAuthor(toString(obj.get("author")));
                s.setTitle(toString(obj.get("title")));
                s.setNSFW(Boolean.parseBoolean(toString(obj.get("over_18"))));
                s.setCreatedUTC(Long.parseLong(toString(obj.get("created_utc"))));
                s.setDownVotes(Integer.parseInt(toString(obj.get("downs"))));
                s.setName(toString(obj.get("name")));
                s.setScore(Integer.parseInt(toString(obj.get("score"))));
                s.setUpVotes(Integer.parseInt(toString(obj.get("ups"))));
                s.setCommentCount(Integer.parseInt(toString(obj.get("num_comments"))));
                // Add it to the submissions list
                liked.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return liked;
    }

    /**
     * Returns a list of submissions that this user chose to hide.
     *
     * @return List of disliked links.
     * @author Benjamin Jakobus
     */
    public List<Submission> hidden() {
        // List of submissions made by this user
        List<Submission> hidden = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/hidden.json"), cookie);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            Submission s;
            for (int i = 0; i < children.size(); i++) {
                // Get the object containing the comment
                obj = (JSONObject) children.get(i);
                obj = (JSONObject) obj.get("data");

                // Create a new comment
                s = new Submission();
                s.setAuthor(toString(obj.get("author")));
                s.setTitle(toString(obj.get("title")));
                s.setNSFW(Boolean.parseBoolean(toString(obj.get("over_18"))));
                s.setCreatedUTC(Long.parseLong(toString(obj.get("created_utc"))));
                s.setDownVotes(Integer.parseInt(toString(obj.get("downs"))));
                s.setName(toString(obj.get("name")));
                s.setScore(Integer.parseInt(toString(obj.get("score"))));
                s.setUpVotes(Integer.parseInt(toString(obj.get("ups"))));
                s.setCommentCount(Integer.parseInt(toString(obj.get("num_comments"))));
                // Add it to the submissions list
                hidden.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return hidden;
    }

    /**
     * Returns a list of submissions that this user disliked.
     *
     * @return List of disliked links.
     * @author Benjamin Jakobus
     */
    public List<Submission> disliked() {
        // List of submissions made by this user
        List<Submission> disliked = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/user/" + username + "/disliked.json"), cookie);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;
            Submission s;
            for (int i = 0; i < children.size(); i++) {
                // Get the object containing the comment
                obj = (JSONObject) children.get(i);
                obj = (JSONObject) obj.get("data");

                // Create a new comment
                s = new Submission();
                s.setAuthor(toString(obj.get("author")));
                s.setTitle(toString(obj.get("title")));
                s.setNSFW(Boolean.parseBoolean(toString(obj.get("over_18"))));
                s.setCreatedUTC(Long.parseLong(toString(obj.get("created_utc"))));
                s.setDownVotes(Integer.parseInt(toString(obj.get("downs"))));
                s.setName(toString(obj.get("name")));
                s.setScore(Integer.parseInt(toString(obj.get("score"))));
                s.setUpVotes(Integer.parseInt(toString(obj.get("ups"))));
                s.setCommentCount(Integer.parseInt(toString(obj.get("num_comments"))));
                // Add it to the submissions list
                disliked.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return disliked;
    }

    /**
     * Safely converts an object into string (used because sometimes
     * JSONObject's get() method returns null).
     *
     * @param obj The object to convert.
     * @return The string.
     * @author Benjamin Jakobus
     */
    private static String toString(Object obj) {
        return (obj == null ? null : obj.toString());
    }
}