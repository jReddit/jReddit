import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.SortOption;
import im.goel.jreddit.utils.TimeOption;
import im.goel.jreddit.utils.Utils;



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

		//For each of the subreddits listed above, upvote the first 20 posts in new
		for (int i = 0; i < subreddits.length; i++) {
			for (Submission submission : Submissions
					.getSubmissions(subreddits[i], SortOption.NEW, TimeOption.WEEK, 20, user)) { //time option does nothing in this case, but will work with TOP etc.
				submission.upVote();
			}
		}
	}
}