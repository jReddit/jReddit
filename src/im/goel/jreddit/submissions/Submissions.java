package im.goel.jreddit.submissions;

import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
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

    /**
     *
     * @param query The search query
     * @param limit Limit of results(25-100)
     * @return The list of search results
     * @throws IOException
     * @throws ParseException
     */
    public static List<Submission> search(String query,int limit) throws IOException, ParseException {

        JSONObject object = (JSONObject) Utils.get("", new URL(
                "http://www.reddit.com/search.json?q=" + URLEncoder.encode(query, "ISO-8859-1")),
                null);
        List<Submission> submissions = null;
        JSONObject data = (JSONObject) object.get("data");
        submissions = (List) buildList((JSONArray) data.get("children"), limit);
        return submissions;
    }
    /**
     * Builds a list of Submissions from the passed array of children.
     * @author Haukur Rosinkranz
     */
    private static List<Object> buildList(JSONArray children, int maxSubmissions) {
        List<Object> submissions = new ArrayList<Object>(10000);
        JSONObject obj;

        if (maxSubmissions < 0 || maxSubmissions > children.size()) {
            maxSubmissions = children.size();
        }
        for (int i = 0; i < maxSubmissions; i++) {
            obj = (JSONObject) children.get(i);
            JSONObject objd = (JSONObject) obj.get("data");
            Submission s = new Submission(objd);
            submissions.add(s);
            }
        return submissions;
    }
}