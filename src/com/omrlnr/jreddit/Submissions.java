package com.omrlnr.jreddit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.omrlnr.jreddit.User;
import com.omrlnr.jreddit.utils.Utils;

/**
 * This class offers some submission utilties.
 * 
 * @author <a href="http://www.omrlnr.com">Omer Elnour</a>
 *
 */
public class Submissions {

    public static final int HOT = 0;
    public static final int NEW = 1;

    public static final int FRONTPAGE = 0;

    /**
     * This function returns a list containing the submissions on a given
     * subreddit and page. 
     * 
     * @param subRedditName     The subreddit's name
     * @param type              HOT or NEW and some others to come
     * @param page              TODO this
     * @param user              The user
     *
     * @return The list containing submissions
     *
     * @throws IOException      If connection fails
     * @throws ParseException   If JSON parsing fails
     */
    public static List<Submission> getSubmissions(
                                            String subRedditName,
                                            int type, 
                                            int page, 
                                            User user) 
                                    throws IOException, ParseException {

        ArrayList<Submission> submissions = new ArrayList<Submission>();
        URL url;
        String urlString = "http://www.reddit.com/r/" + subRedditName;

        //
        // TODO fix "type"
        // Make this not a primitive. 
        //
        switch (type) {
            case NEW:
                urlString += "/new";
                break;
        }

        /**
         * TODO Implement Pages
         */

        urlString += ".json";
        url = new URL(urlString);

        JSONObject object = (JSONObject)Utils.get("", url, user.getCookie());
        JSONObject data = (JSONObject)object.get("data");
        JSONArray array = (JSONArray)data.get("children");

        for (int i = 0; i < array.size(); i++) {
            JSONObject jsonData = (JSONObject)array.get(i);
            submissions.add(new Submission(jsonData));
        }

        return submissions;
    }

}
