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

    private RestClient restClient;

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
        restClient = new HttpRestClient();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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