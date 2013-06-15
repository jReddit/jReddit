<<<<<<< HEAD
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.submissions.Submissions.Page;
import im.goel.jreddit.submissions.Submissions.Popularity;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

=======
import com.omrlnr.jreddit.Submission;
import com.omrlnr.jreddit.Submissions;
import com.omrlnr.jreddit.User;
>>>>>>> 655009ae842bf8c3ca033c6d25edb14f022bcc15


/**
 * A simple bot that upvotes every new submission in a list of subreddits.
 * 
 * @author Omer Elnour
 */
public final class GenerousBot {
	public static void main(String[] args) throws Exception {
		String[] subreddits = { "programming", "funny", "wtf", "java",
				"todayilearned", "redditdev" };

<<<<<<< HEAD
		Utils.setUserAgent("Generous-Bot");

		User user = new User("user", "password");
=======
		User user = new User("username", "password");
>>>>>>> 655009ae842bf8c3ca033c6d25edb14f022bcc15
		user.connect();

		for (int i = 0; i < subreddits.length; i++) {
			for (Submission submission : Submissions
<<<<<<< HEAD
					.getSubmissions(subreddits[i], Popularity.NEW,
							Page.FRONTPAGE, user)) {
				submission.upVote();
=======
					.getSubmissions(subreddits[i], Submissions.NEW,
							Submissions.FRONTPAGE, user)) {
                // 
                // TODO fix this
                //
				// submission.upVote();
>>>>>>> 655009ae842bf8c3ca033c6d25edb14f022bcc15
			}
		}
	}
}
