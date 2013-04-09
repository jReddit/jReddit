import com.omrlnr.jreddit.submissions.Submission;
import com.omrlnr.jreddit.submissions.Submissions;
import com.omrlnr.jreddit.submissions.Submissions.Page;
import com.omrlnr.jreddit.submissions.Submissions.Popularity;
import com.omrlnr.jreddit.user.User;

import com.omrlnr.jreddit.utils.Utils;

/**
 * A simple bot that upvotes every new submission in a list of subreddits.
 * 
 * @author Omer Elnour
 */
public final class GenerousBot {
	public static void main(String[] args) throws Exception {
		String[] subreddits = { "programming", "funny", "wtf", "java",
				"todayilearned", "redditdev" };

		Utils.setUserAgent("Generous-Bot");

		User user = new User("user", "password");
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