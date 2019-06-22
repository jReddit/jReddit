package com.github.jreddit.submissions;

import com.github.jreddit.Thing;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Kind;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static com.github.jreddit.utils.restclient.JsonUtils.*;


/**
 * This class represents a vote on a link submission on Reddit.
 *
 * @author Omer Elnour
 * @author Andrei Sfat
 * @author Raul Rene Lepsa
 * @author Jonny Krysh
 */
public class Submission extends Thing {

    private RestClient restClient;

    /* This is the user that will vote on a submission. */
    private User user;

    /* The path to this submission */
    private String url;
    private String permalink;
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
        restClient = new HttpRestClient();
    }

    /**
     * Create a Submission from a JSONObject
     *
     * @param obj The JSONObject to load Submission data from
     */
    public Submission(JSONObject obj) {

        try {
            setKind(Kind.LINK);
            setFullName(safeJsonToString(obj.get("name")));
            setAuthor(safeJsonToString(obj.get("author")));
            setTitle(safeJsonToString(obj.get("title")));
            setNSFW(safeJsonToBoolean(obj.get("over_18")));
            setUrl(safeJsonToString(obj.get("url")));
            setSubreddit(safeJsonToString(obj.get("subreddit")));
            setPermalink(safeJsonToString(obj.get("permalink")));
            setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
            setUpVotes(safeJsonToLong(obj.get("ups")));
            setDownVotes(safeJsonToLong(obj.get("downs")));
            setScore(safeJsonToLong(obj.get("score")));
            setCommentCount(safeJsonToLong(obj.get("num_comments")));

        } catch (Exception e) {
            System.err.println("Error creating Submission");
        }
        restClient = new HttpRestClient();
    }

    // this is very stinky..
    public void setRestClient(RestClient restClient) {
        this.restClient = restClient;
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

    public void setKind(Kind kind) {
        this.kind = kind.getValue();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getURL() {
        return url;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    public RestClient getRestClient() {
        return restClient;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public long getCommentCount() {
        return commentCount;
    }

    public long getUpVotes() {
        return upVotes;
    }

    public long getDownVotes() {
        return downVotes;
    }

    public long getScore() {
        return score;
    }

    public double getCreatedUTC() {
        return createdUTC;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getSubreddit() {
        return subreddit;
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

        if (object.toJSONString().contains(".error.USER_REQUIRED")) {
            throw new InvalidCookieException("Cookie not present");
        } else {
            System.out.println("Commented on thread id " + fullName
                    + " saying: \"" + text + "\"");
        }
    }

    /** Mark a post as NSFW **/
    public void markNSFW() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, user.getCookie());
    }

    /** Unmark a post as NSFW **/
    public void unmarkNSFW() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, user.getCookie());
    }

    /**
     * This function upvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void upVote() throws IOException, ParseException {
        JSONObject object = voteResponse(1);
        if (!(object.toJSONString().length() > 2)) {
            // Will return "{}"
            System.out.println("Successful upvote!");
        } else {
            System.out.println(object.toJSONString());
        }
    }

    /**
     * This function downvotes this submission.
     *
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public void downVote() throws IOException, ParseException {
        JSONObject object = voteResponse(-1);
        if (!(object.toJSONString().length() > 2)) {
            // Will return "{}"
            System.out.println("Successful downvote!");
        } else {
            System.out.println(object.toJSONString());
        }
    }

    /**
     * This function saves a submission with a specific category (Reddit Gold feature).
     *
     * @param category - a category name
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void save(String category) throws IOException, ParseException {
        restClient.post(
                "category=" + category + "&id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_SAVE, user.getCookie());
    }

    /**
     * This function saves a submission with no category.
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void save() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_SAVE, user.getCookie());
    }

    /**
     * This function unsaves a submission.
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public void unsave() throws IOException, ParseException {
        restClient.post(
                "id=" + fullName + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_UNSAVE, user.getCookie());
    }

    /** Upvote/downvote a submission */
    private JSONObject voteResponse(int dir) throws IOException, ParseException {
        return (JSONObject) restClient.post(
                "id=" + fullName + "&dir=" + dir + "&uh=" + user.getModhash(),
                ApiEndpointUtils.SUBMISSION_VOTE, user.getCookie()).getResponseObject();
    }
}
