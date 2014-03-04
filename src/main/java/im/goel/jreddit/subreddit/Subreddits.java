package im.goel.jreddit.subreddit;


import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to deal with Subreddits
 *
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 */
public class Subreddits {

    /**
     * Returns the subreddits that make up the default front page of reddit.
     */
    public List<Subreddit> listDefault() {
        List<Subreddit> subreddits = null;

        try {
            JSONObject object = (JSONObject) Utils.get(new URL("http://www.reddit.com/reddits.json"), null);
            JSONObject data = (JSONObject) object.get("data");

            subreddits = initList((JSONArray) data.get("children"));

        } catch (Exception e) {
            System.out.println("Error retrieving subreddits");
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
     */
    public static List<Subreddit> list(User user, String param) {
        // List of subreddits
        List<Subreddit> subreddits = null;
        try {
            JSONObject object = (JSONObject) Utils.get(new URL(
                    "http://www.reddit.com/reddits/" + param + ".json"), user.getCookie());
            JSONObject data = (JSONObject) object.get("data");

            subreddits = initList((JSONArray) data.get("children"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return subreddits;
    }

    /**
     * Get a Subreddit by its name
     *
     * @param subredditName    name of the subreddit to retrieve
     * @return Subreddit object representing the desired subreddit, or NULL if it does not exist
     */
    public Subreddit getSubredditByName(String subredditName) {

        try {
            for (Subreddit sub : listDefault()) {
                if (sub.getDisplayName().equalsIgnoreCase(subredditName)) {
                    return sub;
                }
            }

        } catch (Exception e) {
            System.out.println("Error retrieving subreddit: " + subredditName);
        }

        return null;
    }

    /**
     * Creates a list of subreddits by interpreting the given <code>JSONArray</code>.
     *
     * @param children Array containing child nodes that form the list of subreddits.
     * @return A <code>List</code> of subreddits.
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
