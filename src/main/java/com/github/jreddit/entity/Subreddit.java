package com.github.jreddit.entity;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToLong;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import org.json.simple.JSONObject;

/**
 * Encapsulates a subreddit.
 *
 * @author Benjamin Jakobus
 * @author Simon Kassing
 */
public class Subreddit extends Thing {
	
    // The subreddit's display name
    private String displayName;

    // The subreddit's title
    private String title;

    // The subreddit's URL
    private String url;

    // Timestamp of when the subreddit was created
    private double created;

    // UTC timestamp of when the subreddit was created
    private double createdUTC;

    // Whether or not the subreddit is for over 18's
    private Boolean nsfw;

    // The number of subscribers for this subreddit
    private long subscribers;

    // Description detailing the subreddit
    private String description;
    
    // Type detailing whether it is public or private
    private String subreddit_type;
    
    // Other possible fields
    
    // Submit text HTML
//	String submit_text_html = null;
	
	// Whether user is banned
//	Boolean user_is_banned = null;
	
	// Submit text
//	String submit_text = "submit text for subreddit";
	
	// Header image
//	String header_img = "http://a.thumbs.redditmedia.com/yyL5sveWcgkCPKbr.png";
	
	// Description in HTML markup
//	String description_html = "&lt;div&gt;HTML description for subreddit&lt;/d&gt;";
	
	// Whether user is moderator
//	Boolean user_is_moderator = null;
	
	// Header title
//	String header_title = "Header title for subreddit";
	
	// Submit link title
//	String submit_link_label = "Submit link label";
	
	// Accounts active
//	String accounts_active = null;
	
	// Whether it allows public traffic
//	Boolean public_traffic = true;
	
	// Size of header
//	JSONArray header_size = JsonHelpers.jsonArrayOf(160, 64);
	
	// Submit text label
//	String submit_text_label = "Submit text label";
	
	// Whether user is contributor
//	Boolean user_is_contributor = null;
	
	// Public description
//	String public_description = "Public description of subreddit";
	
	// Amount of minutes the comment score is hidden
//	long comment_score_hide_mins = 0;
	
	// What types of submissions are allowed
//	String submission_type = "any";
	
	// Whether the user is contributor
//	Boolean user_is_subscriber = null;
    
    /**
     * Create a Submission from a JSONObject
     *
     * @param obj The JSONObject to load Submission data from
     */
    public Subreddit(JSONObject obj) {
    	super(safeJsonToString(obj.get("name")));
    	
        try {
        	
            setDisplayName(safeJsonToString(obj.get("display_name")));
            setTitle(safeJsonToString(obj.get("title")));
            setURL(safeJsonToString(obj.get("url")));
            setCreated(safeJsonToDouble(obj.get("created")));
            setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
            setNSFW(safeJsonToBoolean(obj.get("over_18")));
            setSubscribers(safeJsonToLong(obj.get("subscribers")));
            setDescription(safeJsonToString(obj.get("description")));
            setSubredditType(safeJsonToString(obj.get("subreddit_type")));
            
            
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("Error creating Subreddit");
        }
        
    }
    
    /**
     * Sets the timestamp of when the subreddit was created.
     *
     * @param created Timestamp of when the subreddit was created.
     */
    public void setCreated(double created) {
        this.created = created;
    }

    /**
     * Sets the UTC timestamp of when the subreddit was created.
     *
     * @param createdUTC UTC timestamp of when the subreddit was created.
     */
    public void setCreatedUTC(double createdUTC) {
        this.createdUTC = createdUTC;
    }

    /**
     * Sets the description detailing the subreddit
     *
     * @param description Description detailing the subreddit.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the subreddit's display name.
     *
     * @param displayName The subreddit's display name.
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the flag of whether or not the subreddit is for over 18's.
     *
     * @param nsfw True if the subreddit is for over 18's; false if not.
     */
    public void setNSFW(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    /**
     * Sets the number of subscribers for this subreddit.
     *
     * @param subscribers The number of subscribers for this subreddit.
     */
    public void setSubscribers(long subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * Sets the subreddit's title.
     *
     * @param title The subreddit's title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the subreddit's URL.
     *
     * @param url The subreddit's URL.
     */
    public void setURL(String url) {
        this.url = url;
    }

    /**
     * Returns the timestamp of when the subreddit was created.
     *
     * @return Timestamp of when the subreddit was created.
     */
    public double getCreated() {
        return created;
    }

    /**
     * Returns the UTC timestamp of when the subreddit was created.
     *
     * @return UTC timestamp of when the subreddit was created.
     */
    public double getCreatedUTC() {
        return createdUTC;
    }

    /**
     * Returns the description detailing the subreddit
     *
     * @return Description detailing the subreddit.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the subreddit's display name.
     *
     * @return The subreddit's display name.
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the number of subscribers for this subreddit.
     *
     * @return The number of subscribers for this subreddit.
     */
    public long getSubscribers() {
        return subscribers;
    }

    /**
     * Returns the subreddit's title.
     *
     * @return The subreddit's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the subreddit's URL.
     *
     * @return The subreddit's URL.
     */
    public String getURL() {
        return url;
    }

    /**
     * Returns the flag of whether or not the subreddit is for over 18's.
     *
     * @return True if the subreddit is for over 18's; false if not.
     */
    public Boolean isNSFW() {
        return nsfw;
    }
    
    public String getSubredditType() {
		return subreddit_type;
	}

	public void setSubredditType(String type) {
		this.subreddit_type = type;
	}

	public String toString() {
    	return "Subreddit(" + this.getFullName() + ")<" + this.getDisplayName() + ">";
    }
    
    @Override
    public boolean equals(Object other) {
    	return (other instanceof Subreddit && this.getFullName().equals(((Subreddit) other).getFullName()));
    }

	public int compareTo(Thing o) {
		return this.getFullName().compareTo(o.getFullName());
	}
    
}