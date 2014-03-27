package com.github.jreddit.submissions;

import com.github.jreddit.Thing;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;


/**
 * This class represents a vote on a link submission on Reddit.
 *
 * @author Omer Elnour
 * @author Andrei Sfat
 */
public class Submission extends Thing {

    private RestClient restClient = new HttpRestClient();

    /* This is the user that will vote on a submission. */
    private User user;

    /* The path to this submission */
    private String url;

    private double createdUTC;
    private String author;
    private String title;
    private Boolean nsfw;
    private long commentCount;
    private String subreddit;
    private long upVotes;
    private long downVotes;
    private long score;

    public Submission() {
    }

    /**
     * Create a Submission from a JSONObject
     *
     * @param obj The JSONObject to load Submission data from
     */
    public Submission(JSONObject obj) {

        try {
            setAuthor((String) obj.get("author"));
            setTitle((String) obj.get("title"));
            setNSFW((Boolean) obj.get("over_18"));
            setCreatedUTC((Double) obj.get("created_utc"));
            setDownVotes((Long) obj.get("downs"));
            setFullName((String) obj.get("name"));
            setScore((Long) obj.get("score"));
            setUpVotes((Long) obj.get("ups"));
            setCommentCount((Long) obj.get("num_comments"));
            setUrl((String) obj.get("url"));
        } catch (Exception e) {
            System.err.println("Error creating Submission");
        }
    }

    public void setUpVotes(long upVotes) {
        this.upVotes = upVotes;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    public void setDownVotes(long downVotes) {
        this.downVotes = downVotes;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public void setNSFW(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFullName() {
        return fullName;
    }

    public String getURL() {
        return url;
    }

    public Submission(User user, String fullName, String url) {
        // TODO: Refactor this to use TypePrefix enum
        if (fullName.startsWith("t3_"))
            fullName = fullName.replaceFirst("t3_", "");

        this.user = user;
        this.fullName = "t3_" + fullName;
        this.url = url;
    }

    /**
     * This function comments on this submission saying the comment specified in
     * <code>text</code> (CAN INCLUDE MARKDOWN)
     *
     * @param text The text to comment
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void comment(String text) throws IOException, ParseException {
        JSONObject object = (JSONObject) restClient.post("thing_id=" + fullName + "&text=" + text
                + "&uh=" + user.getModhash(), ApiEndpointUtils.SUBMISSION_COMMENT, user.getCookie()).getResponseObject();

        if (object.toJSONString().contains(".error.USER_REQUIRED"))
            throw new InvalidCookieException("Cookie not present");
        else
            System.out.println("Commented on thread id " + fullName
                    + " saying: \"" + text + "\"");
    }

    /**
     * This function returns the name of the author of this submission.
     *
     * @return The author's name
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public String getAuthor() throws IOException, ParseException {
        if (author != null) {
            return author;
        }
        if (url == null)
            throw new IOException("URL needs to be present");

        return info(url).get("author").toString();
    }

    /**
     * This function returns the title of this submission.
     *
     * @return The title
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public String getTitle() throws IOException, ParseException {
        if (title != null) {
            return title;
        }

        if (url == null)
            throw new IOException("URL needs to be present");

        return info(url).get("title").toString();
    }

    /**
     * This function returns the name of the subreddit that this submission was
     * submitted to.
     *
     * @return The name of the subreddit that this submission was submitted to
     * @throws IOException    If the connection fails
     * @throws ParseException If JSON parsing fails
     */
    public String getSubreddit() throws IOException, ParseException {
        if (subreddit != null) {
            return subreddit;
        }
        if (url == null)
            throw new IOException("URL needs to be present");

        return info(url).get("subreddit").toString();
    }

    /**
     * This function returns the score of this sumbission (issues a new GET request
     * to Reddit.com).
     *
     * @return The score of this submission
     * @throws IOException    If the connection fails
     * @throws ParseException If the JSON parsing fails
     */
    public int score() throws IOException, ParseException {
        if (url == null)
            throw new IOException("URL needs to be present");

        return Integer.parseInt(info(url).get("score").toString());
    }

    /**
     * This function returns the number of upvotes of this sumbission.
     *
     * @return The number of upvotes of this submission
     * @throws IOException    If the connection fails
     * @throws ParseException If the JSON parsing fails
     */
    public int upVotes() throws IOException, ParseException {
        if (url == null)
            throw new IOException("URL needs to be present");

        return Integer.parseInt(info(url).get("ups").toString());
    }

    /**
     * This function returns the number of downvotes of this sumbission.
     *
     * @return The number of downvotes of this submission
     * @throws IOException    If the connection fails
     * @throws ParseException If the JSON parsing fails
     */
    public int downVotes() throws IOException, ParseException {
        if (url == null)
            throw new IOException("URL needs to be present");

        return Integer.parseInt(info(url).get("downs").toString());
    }

    /**
     * This function returns true if this submission is marked as NSFW (18+)
     *
     * @return This submission is NSFW
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public boolean isNSFW() throws IOException, ParseException {
        if (this.nsfw == null) {
            if (url == null)
                throw new IOException("URL needs to be present");

            return Boolean.parseBoolean(info(url).get("over_18").toString());
        } else {
            return this.nsfw;
        }
    }

    public void markNSFW() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, user.getCookie());
    }

    public void unmarkNSFW() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, user.getCookie());
    }

    /**
     * This function returns true if this submission is a self-post
     *
     * @return This submission is a self post
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public boolean isSelfPost() throws IOException, ParseException {
        if (url == null)
            throw new IOException("URL needs to be present");

        return Boolean.parseBoolean(info(url).get("is_self").toString());
    }

    /**
     * This function returns the number of comments in this sumbission.
     *
     * @return The number of comments in this submission
     * @throws IOException    If the connection fails
     * @throws ParseException If the JSON parsing fails
     */
    public int commentCount() throws IOException, ParseException {
        if (url == null)
            throw new IOException("URL needs to be present");
        return Integer.parseInt(info(url).get("num_comments").toString());
    }

    /**
     * This function upvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void upVote() throws IOException, ParseException {
        JSONObject object = voteResponse(1);
        if (!(object.toJSONString().length() > 2))
            // Will return "{}"
            System.out.println("Successful upboat!");
        else
            System.out.println(object.toJSONString());
    }

    /**
     * This function rescinds, or normalizes this submission. <br />
     * (i.e Removes a downvote or upvote)
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void rescind() throws IOException, ParseException {
        JSONObject object = voteResponse(0);
        if (!(object.toJSONString().length() > 2))
            // Will return "{}"
            System.out.println("Successful rescind!");
        else
            System.out.println(object.toJSONString());
    }

    /**
     * This function downvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void downVote() throws IOException, ParseException {
        JSONObject object = voteResponse(-1);
        if (!(object.toJSONString().length() > 2))
            // Will return "{}"
            System.out.println("Successful downvote!");
        else
            System.out.println(object.toJSONString());
    }

    private JSONObject voteResponse(int dir) throws IOException, ParseException {
        return (JSONObject) restClient.post(
                "id=" + fullName + "&dir=" + dir + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_VOTE, user.getCookie()).getResponseObject();
    }

    private JSONObject info(String urlPath) throws IOException, ParseException {
        urlPath += "/info.json";
        Object object =  restClient.get(urlPath, user.getCookie()).getResponseObject();

        JSONArray array = (JSONArray) object;
        JSONObject obj = (JSONObject) array.get(0);
        obj = (JSONObject) obj.get("data");
        array = (JSONArray) obj.get("children");
        obj = (JSONObject) array.get(0);
        return (JSONObject) obj.get("data");
    }

    public double getCreatedUTC() throws IOException, ParseException {
        createdUTC = Double.parseDouble(info(url).get("created_utc").toString());
        return createdUTC;
    }

//    @Override
//    public String toString() {
//        try {
//            return "(" + score() + ") " + getTitle();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return super.toString();
//    }
}