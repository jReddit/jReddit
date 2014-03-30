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

    private final RestClient restClient;

    public enum Popularity {
        HOT, NEW
    }

    public enum Page {
        FRONTPAGE
    }

    public Submissions(RestClient restClient) {
        this.restClient = restClient;
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
    public LinkedList<Submission> getSubmissions(String redditName,
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

        JSONObject object = (JSONObject)  restClient.get(urlString, user.getCookie()).getResponseObject();
        JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");

        JSONObject data;
        for (Object anArray : array) {
            data = (JSONObject) anArray;
            data = ((JSONObject) data.get("data"));
            submissions.add(new Submission(user, data.get("id").toString(), (data.get("permalink").toString())));
        }

        return submissions;
    }
}