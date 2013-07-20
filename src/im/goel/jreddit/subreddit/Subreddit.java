package im.goel.jreddit.subreddit;

import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * Encapsulates a subreddit.
 *
 * @author Benjamin Jakobus
 */
public class Subreddit {
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
    private String createdUTC;

    // Whether or not the subreddit is for over 18's
    private boolean nsfw;

    // The number of subscribers for this subreddit
    private int subscribers;

    // The subreddit's unique identifier
    private String id;

    // Description detailing the subreddit
    private String description;

    
    /**Constructs a Subreddit with empty values - probably not what you want
     * 
     */
    public Subreddit(){}
    /** Constructs a SubReddit with supplied values
     * 
     * @param displayName
     * @param name
     * @param title
     * @param url
     * @param created
     * @param createdUTC
     * @param nsfw
     * @param subscribers
     * @param id
     * @param description
     */
    public Subreddit(String displayName, String name, String title, String url,
    		String created, String createdUTC, boolean nsfw, int subscribers,
    		String id, String description){
    	this.displayName = displayName;
    	this.name = name;
    	this.title = title;
    	this.url = url;
    	this.created = created;
    	this.createdUTC = createdUTC;
    	this.nsfw = nsfw;
    	this.subscribers = subscribers;
    	this.id = id;
    	this.description = description;
    }
    /** 
     * Constructs a SubReddit with all supplied values except createdUTC is passed as a double
     * because everywhere else in the API we treat it like a double.
     * @param displayName
     * @param name
     * @param title
     * @param url
     * @param created
     * @param createdUTC
     * @param nsfw
     * @param subscribers
     * @param id
     * @param description
     */
    public Subreddit(String displayName, String name, String title, String url,
    		String created, double createdUTC, boolean nsfw, int subscribers,
    		String id, String description){
    	this(displayName, name, title, url, created, ("" + createdUTC), nsfw,
    			subscribers, id, description);
    }
    /** 
     * Constructs a SubReddit from a subreddit name in the form of a string eg. "funny", also requires a user to be supplied for hidden subreddits
     * Uses the util get method to retrieve a JSON object and converts this to be stored in the subreddit variables
     * @param name
     * @param user
     */
    public Subreddit(String name, User user){
    	URL url;
		try {
			url = new URL("http://www.reddit.com/r/"+name+"/about.json");
			Object obj = Utils.get("", url, user.getCookie());
	        JSONObject object = (JSONObject) obj;
	        JSONObject data = (JSONObject) object.get("data");
	        
	        this.displayName = data.get("display_name").toString();
	        //System.out.println(this.displayName);
	    	this.name = data.get("name").toString();
	    	this.title = data.get("title").toString();
	    	this.url = data.get("url").toString();
	    	this.created = data.get("created").toString();
	    	this.createdUTC = data.get("created_utc").toString();
	    	this.nsfw = (boolean) data.get("over18");
	    	this.subscribers = Integer.parseInt(data.get("subscribers").toString());
	    	this.id = data.get("id").toString();
	    	this.description = data.get("description").toString();
	        
	        
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
    }
    
    public void print(){
    	System.out.println("\n-----------------------");
    	System.out.println("Subreddit: "+this.url);
    	System.out.println("-----------------------");
    	System.out.println("Display Name: "+this.displayName);
    	System.out.println("Name: "+this.name);
    	System.out.println("Title: "+this.title);
    	System.out.println("URL: "+this.url);
    	System.out.println("Created: "+this.created);
    	System.out.println("Created UTC: "+this.createdUTC);
    	System.out.println("NSFW: "+this.nsfw);
    	System.out.println("Subscribers: "+this.subscribers);
    	System.out.println("ID: "+this.id);
    	System.out.println("Description: "+this.description);
    	System.out.println("-----------------------");
    }
    
    /**
     * Sets the timestamp of when the subreddit was created.
     *
     * @param created Timestamp of when the subreddit was created.
     * @author Benjamin Jakobus
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * Sets the UTC timestamp of when the subreddit was created.
     *
     * @param createdUTC UTC timestamp of when the subreddit was created.
     * @author Benjamin Jakobus
     */
    public void setCreatedUTC(String createdUTC) {
        this.createdUTC = createdUTC;
    }

    /**
     * Sets the description detailing the subreddit
     *
     * @param description Description detailing the subreddit.
     * @author Benjamin Jakobus
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets the subreddit's display name.
     *
     * @param displayName The subreddit's display name.
     * @author Benjamin Jakobus
     */
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Sets the subreddit's unique identifier.
     *
     * @param id The subreddit's unique identifier.
     * @author Benjamin Jakobus
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Sets the subreddit's actual name (not its display name).
     *
     * @param name The subreddit's actual name.
     * @author Benjamin Jakobus
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the flag of whether or not the subreddit is for over 18's.
     *
     * @param nsfw True if the subreddit is for over 18's; false if not.
     * @author Benjamin Jakobus
     */
    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    /**
     * Sets the number of subscribers for this subreddit.
     *
     * @param subscribers The number of subscribers for this subreddit.
     * @author Benjamin Jakobus
     */
    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    /**
     * Sets the subreddit's title.
     *
     * @param title The subreddit's title.
     * @author Benjamin Jakobus
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the subreddit's URL.
     *
     * @param url The subreddit's URL.
     * @author Benjamin Jakobus
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Returns the timestamp of when the subreddit was created.
     *
     * @return Timestamp of when the subreddit was created.
     * @author Benjamin Jakobus
     */
    public String getCreated() {
        return created;
    }

    /**
     * Returns the UTC timestamp of when the subreddit was created.
     *
     * @return UTC timestamp of when the subreddit was created.
     * @author Benjamin Jakobus
     */
    public String getCreatedUTC() {
        return createdUTC;
    }

    /**
     * Returns the description detailing the subreddit
     *
     * @return Description detailing the subreddit.
     * @author Benjamin Jakobus
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the subreddit's display name.
     *
     * @return The subreddit's display name.
     * @author Benjamin Jakobus
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns the subreddit's unique identifier.
     *
     * @return The subreddit's unique identifier.
     * @author Benjamin Jakobus
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the subreddit's actual name (not its display name).
     *
     * @return The subreddit's actual name.
     * @author Benjamin Jakobus
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the number of subscribers for this subreddit.
     *
     * @return The number of subscribers for this subreddit.
     * @author Benjamin Jakobus
     */
    public int getSubscribers() {
        return subscribers;
    }

    /**
     * Returns the subreddit's title.
     *
     * @return The subreddit's title.
     * @author Benjamin Jakobus
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns the subreddit's URL.
     *
     * @return The subreddit's URL.
     * @author Benjamin Jakobus
     */
    public String getUrl() {
        return url;
    }

    /**
     * Returns the flag of whether or not the subreddit is for over 18's.
     *
     * @return True if the subreddit is for over 18's; false if not.
     * @author Benjamin Jakobus
     */
    public boolean isNSFW() {
        return nsfw;
    }
}