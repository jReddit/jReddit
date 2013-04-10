package com.omrlnr.jreddit.submissions;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.user.User;
import com.omrlnr.jreddit.utils.Utils;

/**
 * This class offers some submission utilties.
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
        URL url;
        String urlString = "http://www.reddit.com/r/" + redditName;

        switch (type) {
            case NEW:
                urlString += "/new";
                break;
		default:
			break;
        }

        /**
         * TODO Implement Pages
         */

        urlString += ".json";
        url = new URL(urlString);

        Object obj = Utils.get("", url, user.getCookie());
        JSONObject object = (JSONObject) obj;
        JSONArray array = (JSONArray) ((JSONObject) object.get("data"))
                .get("children");
        JSONObject data;
        for (int i = 0; i < array.size(); i++) {
            data = (JSONObject) array.get(i);
            data = ((JSONObject) ((JSONObject) data).get("data"));
            submissions.add(new Submission(user, data.get("id").toString(),
                    new URL("http://www.reddit.com" + (data.get("permalink").toString()))));
        }

        return submissions;
    }
}