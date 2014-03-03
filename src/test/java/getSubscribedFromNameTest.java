import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.subreddit.Subreddits;
import im.goel.jreddit.user.User;

public class getSubscribedFromNameTest {

    /**
     * @author ThatBox
     * @param args
     */
    public static void main(String[] args) {
        User user = new User("jReddittest", "jReddittest"); // Add your username and password
        try {
            user.connect();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        Subreddit sub = Subreddits.getSubredditFromName(user, "funny");
        System.out.println(sub.getDisplayName());
        System.out.println(sub.getSubscribers());
    }
}
