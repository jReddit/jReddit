package com.github.jreddit.submissions;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.Utils;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * This class offers some submission utilities.
 *
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 */
public class Submissions {
    public enum Popularity {
        HOT, NEW
    }

    public enum Page {
        FRONTPAGE
    }

    /**
     * This function returns a linked list containing the submissions on a given
     * subreddit and page. (in progress)
     *
     * @param redditName The subreddit's name
     * @param type       HOT or NEW and some others to come
     * @param frontpage       TODO this
     * @param user       The user
     * @return The linked list containing submissions
     * @throws IOException    If connection fails
     * @throws ParseException If JSON parsing fails
     */
    public static LinkedList<Submission> getSubmissions(String redditName,
                       Popularity type, Page frontpage, User user) throws IOException, ParseException {

        LinkedList<Submission> submissions = new LinkedList<Submission>();
        String urlString = "/r/" + redditName;

        switch (type) {
            case NEW:
                urlString += "/new";
                break;
		default:
			break;
        }

        //TODO Implement Pages

        urlString += ".json";

        JSONObject object = (JSONObject) Utils.get(urlString, user.getCookie());
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        JSONObject data;
        for (int i = 0; i < array.size(); i++) {
            data = (JSONObject) array.get(i);
            data = ((JSONObject) data.get("data"));
            submissions.add(new Submission(user, data.get("id").toString(), (data.get("permalink").toString())));
        }

        return submissions;
    }
    
    /**
     * Returns a list of submissions from a subreddit.
     * @author <a href="https://github.com/trentrand/">Trent Rand</a>
     
     * @param subreddit The subreddit at which submissions you want
     *                 to retrieve submissions.
     * @return <code>List</code> of submissions on the subreddit.
     */
    public static List<Submission> getSubmissions(String subreddit) {
        // List of submissions made by this user
        List<Submission> submissions = new ArrayList<Submission>(500);
        try {
            // Send GET request to get the account overview
            JSONObject object = (JSONObject) Utils.get(String.format(ApiEndpointUtils.SUBMISSIONS, subreddit), null);
            JSONObject data = (JSONObject) object.get("data");
            JSONArray children = (JSONArray) data.get("children");

            JSONObject obj;

            for (Object aChildren : children) {
                // Get the object containing the comment
                obj = (JSONObject) aChildren;
                obj = (JSONObject) obj.get("data");
                //add a new Submission to the list
                submissions.add(new Submission(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Return the submissions
        return submissions;
    }
}