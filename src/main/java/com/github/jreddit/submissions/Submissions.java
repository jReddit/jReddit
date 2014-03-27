package com.github.jreddit.submissions;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.LinkedList;


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
        RestClient restClient = new HttpRestClient();

        switch (type) {
            case NEW:
                urlString += "/new";
                break;
		default:
			break;
        }

        //TODO Implement Pages

        urlString += ".json";

        JSONObject object = (JSONObject)  restClient.get(urlString, user.getCookie()).getResponseObject();
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        JSONObject data;
        for (int i = 0; i < array.size(); i++) {
            data = (JSONObject) array.get(i);
            data = ((JSONObject) data.get("data"));
            submissions.add(new Submission(user, data.get("id").toString(), (data.get("permalink").toString())));
        }

        return submissions;
    }
}