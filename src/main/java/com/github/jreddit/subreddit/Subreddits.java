package com.github.jreddit.subreddit;


import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to deal with Subreddits
 *
 * @author Benjamin Jakobus
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 */
public class Subreddits {

    private final RestClient restClient;

    public Subreddits(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Returns the subreddits that make up the default front page of reddit.
     */
    public List<Subreddit> listDefault() {
        List<Subreddit> subreddits = null;

        try {
            JSONObject object = (JSONObject)  restClient.get(ApiEndpointUtils.SUBREDDITS, null).getResponseObject();
            JSONObject data = (JSONObject) object.get("data");

            subreddits = constructList((JSONArray) data.get("children"));

        } catch (Exception e) {
            System.out.println("Error retrieving subreddits");
        }

        return subreddits;
    }

    /**
     * Get a list of Subreddits of a certain SubredditType
     *
     * @param subredditType SubredditType (POPULAR, NEW, BANNED)
     * @return list of Subreddits of that type, null if none are found
     */
    public List<Subreddit> getSubreddits(SubredditType subredditType) {

        List<Subreddit> subreddits = null;

        try {
            JSONObject object =
                    (JSONObject)  restClient.get(String.format(ApiEndpointUtils.SUBREDDITS_GET, subredditType.getValue()), null).getResponseObject();
            JSONObject data = (JSONObject) object.get("data");

            subreddits = constructList((JSONArray) data.get("children"));

        } catch (Exception e) {
            System.out.println("Error retrieving subreddits of type: " + subredditType.getValue());
        }

        return subreddits;
    }

    /**
     * Get a Subreddit by its name
     *
     * @param subredditName name of the subreddit to retrieve
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
     * Creates a list of subreddits by interpreting a given <code>JSONArray</code> of subreddits.
     *
     * @param subredditJsonArray Array containing child nodes that form the list of subreddits.
     * @return A <code>List</code> of Subreddit objects
     */
    private static List<Subreddit> constructList(JSONArray subredditJsonArray) {
        List<Subreddit> subreddits = new ArrayList<Subreddit>();
        JSONObject subredditJsonObject;
        Subreddit subreddit;

        for (Object object : subredditJsonArray) {
            subredditJsonObject = (JSONObject) object;
            subredditJsonObject = (JSONObject) subredditJsonObject.get("data");

            subreddit = new Subreddit();
            subreddit.setCreated(subredditJsonObject.get("created").toString());
            subreddit.setCreatedUTC(subredditJsonObject.get("created_utc").toString());
            subreddit.setDescription(subredditJsonObject.get("description").toString());
            subreddit.setDisplayName(subredditJsonObject.get("display_name").toString());
            subreddit.setId(subredditJsonObject.get("id").toString());
            subreddit.setName(subredditJsonObject.get("display_name").toString());
            subreddit.setNsfw(Boolean.parseBoolean(subredditJsonObject.get("over18").toString()));
            subreddit.setSubscribers(Integer.parseInt(subredditJsonObject.get("subscribers").toString()));
            subreddit.setTitle(subredditJsonObject.get("title").toString());
            subreddit.setUrl(subredditJsonObject.get("url").toString());
            subreddits.add(subreddit);
        }

        return subreddits;
    }
}
