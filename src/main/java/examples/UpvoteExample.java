package examples;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * A simple bot that upvotes every new submission in a list of subreddits.
 * 
 * @author Omer Elnour
 */
public class UpvoteExample {
	public static void main(String[] args) throws Exception {
		String[] subreddits = { "programming", "funny", "wtf", "java",
				"todayilearned", "redditdev" };

        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("Generous-Bot");

		User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		user.connect();
/*
		for (int i = 0; i < subreddits.length; i++) {
			for (Submission submission : Submissions
					.getSubmissions(subreddits[i], Popularity.NEW,
							Page.FRONTPAGE, user)) {
				submission.upVote();
			}
		}*/
	}
}