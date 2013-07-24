package im.goel.jreddit.subreddit;


import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Lists all subreddits.
 *
 * @author Benjamin Jakobus
 */
public class Subreddits {

    /**
     * Returns the subreddits that make up the default front page of reddit.
     *
     * @param user The user account with which to connect.
     * @author Benjamin Jakobus
     */
    public static List<Subreddit> listDefault(User user) {
        // List of subreddits
        List<Subreddit> subreddits = null;
        try {
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/reddits.json"), user.getCookie());
            JSONObject data = (JSONObject) object.get("data");

            subreddits = initList((JSONArray) data.get("children"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subreddits;
    }

    /**
     * Returns the subreddits that match the given parameter.
     *
     * @param user  The user account with which to connect.
     * @param param Should be <code>popular</code> (for listing popular subreddits),
     *              <code>banned</code> (for listing banned subreddits),
     *              <code>new</code> (for listing new subreddits)
     * @author Benjamin Jakobus
     */
    public static List<Subreddit> list(User user, String param) {
        // List of subreddits
        List<Subreddit> subreddits = null;
        try {
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/reddits/" + param + ".json"), user.getCookie());
            JSONObject data = (JSONObject) object.get("data");

            subreddits = initList((JSONArray) data.get("children"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subreddits;
    }

    /**
     * @param user User who wishes to access the subreddit.
     * @param subName Name of the subreddit that needs to be accessed.
     * @return Subreddit with the name subName
     * @author ThatBox
     */
    public static Subreddit getSubredditFromName(User user, String subName) {
        List<Subreddit> subreddits = null;
        try {
            JSONObject object = (JSONObject) Utils.get("", new URL(
                    "http://www.reddit.com/reddits/.json"), user.getCookie());
            JSONObject data = (JSONObject) object.get("data");

            subreddits = initList((JSONArray) data.get("children"));

            for(Subreddit sub : subreddits) {
                if(sub.getDisplayName().equalsIgnoreCase(subName))
                    return sub;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a list of subreddits by interpreting the given <code>JSONArray</code>.
     *
     * @param children Array containing child nodes that form the list of subreddits.
     * @return A <code>List</code> of subreddits.
     * @author Benjamin Jakobus
     */
    private static List<Subreddit> initList(JSONArray children) {
        // List of subreddits
        List<Subreddit> subreddits = new ArrayList<Subreddit>(10000);
        JSONObject obj;
        Subreddit r;

        // Iterate through the available subreddits
        for (int i = 0; i < children.size(); i++) {
            obj = (JSONObject) children.get(i);
            obj = (JSONObject) obj.get("data");
            r = new Subreddit();
            r.setCreated(obj.get("created").toString());
            r.setCreatedUTC(obj.get("created_utc").toString());
            r.setDescription(obj.get("description").toString());
            r.setDisplayName(obj.get("display_name").toString());
            r.setId(obj.get("id").toString());
            r.setName(obj.get("display_name").toString());
            r.setNsfw(Boolean.parseBoolean(obj.get("over18").toString()));
            r.setSubscribers(Integer.parseInt(obj.get("subscribers").toString()));
            r.setTitle(obj.get("title").toString());
            r.setUrl(obj.get("url").toString());
            subreddits.add(r);
        }
        return subreddits;
    }
}
