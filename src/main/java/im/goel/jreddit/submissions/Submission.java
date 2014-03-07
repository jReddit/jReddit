package im.goel.jreddit.submissions;

import im.goel.jreddit.InvalidCookieException;
import im.goel.jreddit.Thing;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.io.IOException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;


/**
 * This class represents a vote on a link submission on reddit.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class Submission extends Thing {
    /**
     * This is the user that will vote on a submission.
     */
    private User user;
    /**
     * The path to this submission
     */
    private URL url;

    private double createdUTC;
    private String author;
    private String title;
    private Boolean nsfw;
    private String name;
    private int commentCount;
    private String subreddit;
    private int upVotes;
    private int downVotes;
    private int score;

    /** 
     * An empty Submission
     */
    public Submission() {
    }
    
    /** A Submission that is loaded from a JSONObject
     * 
     * @author Evin Ugur
     * @param obj The JSONObject to load Submission data from
     * @throws Exception The JSONObject is somehow bad
     */
    public Submission(JSONObject obj){
    	try{
    		setAuthor(Utils.toString(obj.get("author")));
			setTitle(Utils.toString(obj.get("title")));
			setNSFW(Boolean.parseBoolean(Utils.toString(obj.get("over_18"))));
			setCreatedUTC(Double.parseDouble(Utils.toString(obj.get("created_utc"))));
			setDownVotes(Integer.parseInt(Utils.toString(obj.get("downs"))));
			setName(Utils.toString(obj.get("name")));
			setScore(Integer.parseInt(Utils.toString(obj.get("score"))));
			setUpVotes(Integer.parseInt(Utils.toString(obj.get("ups"))));
			setCommentCount(Integer.parseInt(Utils.toString(obj.get("num_comments"))));
            setUrl(new URL(Utils.toString(obj.get("url"))));
    	}
    	catch(Exception e){e.printStackTrace();}
    }
    
    public Submission(User user, String fullName) {
//		this(user, fullName, url);
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    public void setDownVotes(int downVotes) {
        this.downVotes = downVotes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCommentCount(int commentCount) {
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

    public void setUpVotes(int upVotes) {
        this.upVotes = upVotes;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Submission(User user, String fullName, URL url) {
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
        JSONObject object = Utils.post("thing_id=" + fullName + "&text=" + text
                + "&uh=" + user.getModhash(), new URL(
                "http://www.reddit.com/api/comment"), user.getCookie());

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
     * This function returns the score of this sumbission (does not issue a new GET request
     * to Reddit.com but instead returns the private member).
     *
     * @return The score of this submission
     */
    public int getScore() {
        return score;
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
            return this.nsfw.booleanValue();
        }
    }

    public void markNSFW() throws IOException, ParseException {
        Utils.post(
            "id=" + fullName + "&uh=" + user.getModhash(),
            new URL("http://www.reddit.com/api/marknsfw"), user.getCookie());
    }

    public void unmarkNSFW() throws IOException, ParseException {
        Utils.post(
            "id=" + fullName + "&uh=" + user.getModhash(),
            new URL("http://www.reddit.com/api/unmarknsfw"), user.getCookie());
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

    public User getUser() {
        return user;
    }

    public String getFullName() {
        return fullName;
    }

    public URL getURL() {
        return url;
    }

    private JSONObject voteResponse(int dir) throws IOException, ParseException {
        return Utils.post(
                "id=" + fullName + "&dir=" + dir + "&uh=" + user.getModhash(),
                new URL("http://www.reddit.com/api/vote"), user.getCookie());
    }

    private JSONObject info(URL url) throws IOException, ParseException {
        url = new URL(url.toString() + "/info.json");
        Object object = Utils.get(url, user.getCookie());

        JSONArray array = (JSONArray) object;
        JSONObject obj = (JSONObject) array.get(0);
        obj = (JSONObject) obj.get("data");
        array = (JSONArray) obj.get("children");
        obj = (JSONObject) array.get(0);
        return (JSONObject) obj.get("data");
    }

    public String getName() {
        return name;
    }

    public double getCreatedUTC() throws IOException, ParseException {
        createdUTC = Double.parseDouble(info(url).get("created_utc").toString());
        return createdUTC;
    }

    @Override
    public String toString() {
        try {
            return "(" + score() + ") " + getTitle();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return super.toString();
    }
}