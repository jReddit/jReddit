package com.github.jreddit.entity;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToLong;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import org.json.simple.JSONObject;

import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * This class represents a vote on a link submission on Reddit.
 *
 * @author Omer Elnour
 * @author Andrei Sfat
 * @author Raul Rene Lepsa
 * @author Jonny Krysh
 * @author Danny Tsegai
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
    private boolean nsfw;
    private long commentCount;
    private String subreddit;
    private long upVotes;
    private long downVotes;
    private long score;
    
    /**
     * Create a Submission from a JSONObject
     *
     * @param obj The JSONObject to load Submission data from
     */
    public Submission(JSONObject obj) {
    	super(safeJsonToString(obj.get("name")));

        try {
        	
            setAuthor(safeJsonToString(obj.get("author")));
            setTitle(safeJsonToString(obj.get("title")));
            setNSFW(safeJsonToBoolean(obj.get("over_18")));
            setURL(safeJsonToString(obj.get("url")));
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

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setURL(String url) {
        this.url = url;
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

    public Boolean getNSFW() {
        return nsfw;
    }

    public void setNSFW(Boolean nsfw) {
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
     * String representation of this Submission.
     * @return String representation
     */
    public String toString() {
    	return "Submission(" + this.getFullName() + ")<" + title + ">";
    }
    
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Submission && this.getFullName().equals(((Comment) other).getFullName()));
    }

	public int compareTo(Thing o) {
		return this.getFullName().compareTo(o.getFullName());
	}
    
}