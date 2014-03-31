import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.submissions.Submissions.Page;
import com.github.jreddit.submissions.Submissions.Popularity;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * A simple bot that upvotes every new submission in a list of subreddits.
 * 
 * @author Omer Elnour
 */
public final class GenerousBot {
	public static void main(String[] args) throws Exception {
		String[] subreddits = { "programming", "funny", "wtf", "java",
				"todayilearned", "redditdev" };

        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("Generous-Bot");

		User user = new User(restclient, "user", "password");
		user.connect();

		for (int i = 0; i < subreddits.length; i++) {
			for (Submission submission : Submissions
					.getSubmissions(subreddits[i], Popularity.NEW,
							Page.FRONTPAGE, user)) {
				submission.upVote();
			}
		}
	}
}