package com.github.jreddit.subreddit;

import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToBoolean;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToDouble;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToLong;
import static com.github.jreddit.utils.restclient.JsonUtils.safeJsonToString;

import org.json.simple.JSONObject;

import com.github.jreddit.Thing;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Encapsulates a subreddit.
 * TODO: Subreddit info
 *
 * @author Benjamin Jakobus
 * @author Simon Kassing
 */
public class Subreddit extends Thing {
	
    private RestClient restClient;
    
    // The subreddit's display name
    private String displayName;

    // The subreddit's actual name
    private String name;

    // The subreddit's title
    private String title;

    // The subreddit's URL
    private String url;

    // Timestamp of when the subreddit was created
    private String created;

    // UTC timestamp of when the subreddit was created
    private double createdUTC;

    // Whether or not the subreddit is for over 18's
    private boolean nsfw;

    // The number of subscribers for this subreddit
    private long subscribers;

    // The subreddit's unique identifier
    private String id;

    // Description detailing the subreddit
    private String description;
    
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
            setUrl(safeJsonToString(obj.get("url")));
            setCreated(safeJsonToString(obj.get("subreddit")));
            setCreatedUTC(safeJsonToDouble(obj.get("created_utc")));
            setNSFW(safeJsonToBoolean(obj.get("over_18")));
            setSubscribers(safeJsonToLong(obj.get("subscribers")));
            setDescription(safeJsonToString(obj.get("description")));
            
        } catch (Exception e) {
        	e.printStackTrace();
            System.err.println("Error creating Subreddit");
        }
        
        restClient = new HttpRestClient();
    }
    
    /**
     * Sets the timestamp of when the subreddit was created.
     *
     * @param created Timestamp of when the subreddit was created.
     */
    public void setCreated(String created) {
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
     * Sets the subreddit's unique identifier.
     *
     * @param id The subreddit's unique identifier.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the subreddit's actual name (not its display name).
     *
     * @param name The subreddit's actual name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the flag of whether or not the subreddit is for over 18's.
     *
     * @param nsfw True if the subreddit is for over 18's; false if not.
     */
    public void setNSFW(boolean nsfw) {
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
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the timestamp of when the subreddit was created.
     *
     * @return Timestamp of when the subreddit was created.
     */
    public String getCreated() {
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
     * Returns the subreddit's unique identifier.
     *
     * @return The subreddit's unique identifier.
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the subreddit's actual name (not its display name).
     *
     * @return The subreddit's actual name.
     */
    public String getName() {
        return name;
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
    public String getUrl() {
        return url;
    }

    /**
     * Returns the flag of whether or not the subreddit is for over 18's.
     *
     * @return True if the subreddit is for over 18's; false if not.
     */
    public boolean isNSFW() {
        return nsfw;
    }
    
    public String toString() {
    	return "Subreddit(" + this.getFullName() + ")<" + this.getDisplayName() + ">";
    }
    
}