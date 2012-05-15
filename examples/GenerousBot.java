import org.jreddit.api.submissions.Submission;
import org.jreddit.api.submissions.Submissions;
import org.jreddit.api.user.User;

/**
 * A simple bot that upvotes every new submission in a list of subreddits.
 * 
 * @author Omer Elnour
 */
public final class GenerousBot {
	public static void main(String[] args) throws Exception {
		String[] subreddits = { "programming", "funny", "wtf", "java",
				"todayilearned", "redditdev" };

		User user = new User("username", "password");
		user.connect();

		for (int i = 0; i < subreddits.length; i++) {
			for (Submission submission : Submissions
					.getSubmissions(subreddits[i], Submissions.NEW,
							Submissions.FRONTPAGE, user)) {
				submission.upVote();
			}
		}
	}
}