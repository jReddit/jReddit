package examples;

import java.util.List;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * A simple bot that upvotes every new submission in a subreddit (first 25).
 *
 * @author Omer Elnour
 * @author Simon Kassing
 */
public class UserSubredditsExample {
    private static String USER_NAME = "";
    private static String PASSWORD = "";
    public static void main(String[] args) throws Exception {


        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("Generous-Bot");

        // Set variables to test
        User user = new User(restClient, USER_NAME, PASSWORD);
        user.connect();
        List<Subreddit> subscribed;

        try {
            subscribed = user.getSubscribed();
            for(Subreddit s : subscribed) {
                System.out.println(s.getDisplayName());
            }

        } catch (RetrievalFailedException e) {
            e.printStackTrace();
        } catch (RedditError e) {
            e.printStackTrace();
        }

    }

}