package im.goel.jreddit.submissions;

import im.goel.jreddit.InvalidCookieException;
import im.goel.jreddit.subreddit.Subreddit;
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
public class Submission {
    /**
     * This is the user that will vote on a submission.
     */
    private User user;
    /**
     * The path to this submission
     */
    private URL url;
    
    private String domain;
    private Subreddit subreddit;
    private String selftext;
    private String id;
    private String title;
    private int score;
    private Boolean nsfw;
    private String thumbnail;
    private Boolean edited;
    private int downs;
    private int ups;
    private Boolean saved;
    private Boolean is_self;
    private String permalink;
    private String name;
    private Double created;
    private String link_url;
    private String author;
    private Double created_utc;
    private int num_comments;
    

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
    public Submission(JSONObject obj, User user){
    	try{
    		setDomain(Utils.toString(obj.get("domain")));
    		setSubreddit(new Subreddit(Utils.toString(obj.get("subreddit")), user));
    		setSelftext(Utils.toString(obj.get("selftext")));
    		setId(Utils.toString(obj.get("id")));
    		setTitle(Utils.toString(obj.get("title")));
    		setScore(Integer.parseInt(Utils.toString(obj.get("score"))));
    		setNsfw(Boolean.valueOf(Utils.toString(obj.get("over_18"))));
    		setThumbnail(Utils.toString(obj.get("thumbnail")));
    		setEdited(Boolean.valueOf(Utils.toString(obj.get("edited"))));
    		setDowns(Integer.parseInt(Utils.toString(obj.get("downs"))));
    		setUps(Integer.parseInt(Utils.toString(obj.get("ups"))));
    		setSaved(Boolean.valueOf(Utils.toString(obj.get("saved"))));
    		setIs_self(Boolean.valueOf(Utils.toString(obj.get("is_self"))));
    		setPermalink(Utils.toString(obj.get("permalink")));
    		setName(Utils.toString(obj.get("name")));
    		setCreated(Double.parseDouble(Utils.toString(obj.get("created"))));
    		setLink_url(Utils.toString(obj.get("url")));
    		setAuthor(Utils.toString(obj.get("author")));
    		setCreated_utc(Double.parseDouble(Utils.toString(obj.get("created_utc"))));
    		setNum_comments(Integer.parseInt(Utils.toString(obj.get("num_comments"))));
    		
    		url = new URL("http://reddit.com"+getPermalink());
    	}
    	catch(Exception e){e.printStackTrace();}
    }
    
    public Submission(User user, String fullName) {
//		this(user, fullName, url);
    }

    public Submission(User user, String fullName, URL url) {
        if (fullName.startsWith("t3_"))
            fullName = fullName.replaceFirst("t3_", "");

        this.user = user;
        this.name = "t3_" + fullName;
        this.setUrl(url);
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
        JSONObject object = Utils.post("thing_id=" + name + "&text=" + text
                + "&uh=" + user.getModhash(), new URL(
                "http://www.reddit.com/api/comment"), user.getCookie());

        if (object.toJSONString().contains(".error.USER_REQUIRED"))
            throw new InvalidCookieException("Cookie not present");
        else
            System.out.println("Commented on thread id " + name
                    + " saying: \"" + text + "\"");
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
        return Utils.post(
                "id=" + name + "&dir=" + dir + "&uh=" + user.getModhash(),
                new URL("http://www.reddit.com/api/vote"), user.getCookie());
    }

    private JSONObject info(URL url) throws IOException, ParseException {
        url = new URL(url.toString() + "/info.json");
        Object object = Utils.get("", url, user.getCookie());

        JSONArray array = (JSONArray) object;
        JSONObject obj = (JSONObject) array.get(0);
        obj = (JSONObject) obj.get("data");
        array = (JSONArray) obj.get("children");
        obj = (JSONObject) array.get(0);
        obj = (JSONObject) obj.get("data");
        return (JSONObject) obj;
    }

    @Override
    public String toString() {
        try {
            return "(" + score + ") " + getTitle();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return super.toString();
    }

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * Sets the url
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the domain
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Sets the domain
	 * @param domain the domain to set
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * @return the subreddit
	 */
	public Subreddit getSubreddit() {
		return subreddit;
	}

	/**
	 * Sets the subreddit
	 * @param subreddit the subreddit to set
	 */
	public void setSubreddit(Subreddit subreddit) {
		this.subreddit = subreddit;
	}

	/**
	 * @return the selftext
	 */
	public String getSelftext() {
		return selftext;
	}

	/**
	 * Sets the selftext
	 * @param selftext the selftext to set
	 */
	public void setSelftext(String selftext) {
		this.selftext = selftext;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Sets the score
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * @return the nsfw
	 */
	public Boolean getNsfw() {
		return nsfw;
	}

	/**
	 * Sets the nsfw
	 * @param nsfw the nsfw to set
	 */
	public void setNsfw(Boolean nsfw) {
		this.nsfw = nsfw;
	}

	/**
	 * @return the thumbnail
	 */
	public String getThumbnail() {
		return thumbnail;
	}

	/**
	 * Sets the thumbnail
	 * @param thumbnail the thumbnail to set
	 */
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the edited
	 */
	public Boolean getEdited() {
		return edited;
	}

	/**
	 * Sets the edited
	 * @param edited the edited to set
	 */
	public void setEdited(Boolean edited) {
		this.edited = edited;
	}

	/**
	 * @return the downs
	 */
	public int getDowns() {
		return downs;
	}

	/**
	 * Sets the downs
	 * @param downs the downs to set
	 */
	public void setDowns(int downs) {
		this.downs = downs;
	}

	/**
	 * @return the ups
	 */
	public int getUps() {
		return ups;
	}

	/**
	 * Sets the ups
	 * @param ups the ups to set
	 */
	public void setUps(int ups) {
		this.ups = ups;
	}

	/**
	 * @return the saved
	 */
	public Boolean getSaved() {
		return saved;
	}

	/**
	 * Sets the saved
	 * @param saved the saved to set
	 */
	public void setSaved(Boolean saved) {
		this.saved = saved;
	}

	/**
	 * @return the is_self
	 */
	public Boolean getIs_self() {
		return is_self;
	}

	/**
	 * Sets the is_self
	 * @param is_self the is_self to set
	 */
	public void setIs_self(Boolean is_self) {
		this.is_self = is_self;
	}

	/**
	 * @return the permalink
	 */
	public String getPermalink() {
		return permalink;
	}

	/**
	 * Sets the permalink
	 * @param permalink the permalink to set
	 */
	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the created
	 */
	public Double getCreated() {
		return created;
	}

	/**
	 * Sets the created
	 * @param created the created to set
	 */
	public void setCreated(Double created) {
		this.created = created;
	}

	/**
	 * @return the link_url
	 */
	public String getLink_url() {
		return link_url;
	}

	/**
	 * Sets the link_url
	 * @param link_url the link_url to set
	 */
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets the author
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the created_utc
	 */
	public Double getCreated_utc() {
		return created_utc;
	}

	/**
	 * Sets the created_utc
	 * @param created_utc the created_utc to set
	 */
	public void setCreated_utc(Double created_utc) {
		this.created_utc = created_utc;
	}

	/**
	 * @return the num_comments
	 */
	public int getNum_comments() {
		return num_comments;
	}

	/**
	 * Sets the num_comments
	 * @param num_comments the num_comments to set
	 */
	public void setNum_comments(int num_comments) {
		this.num_comments = num_comments;
	}
}