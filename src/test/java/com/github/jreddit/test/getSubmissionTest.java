import java.util.List;

import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.Utils;

/**
 * Test script to show implementation of Submissions.getSubmission("subreddit")
 * 
 * @author Trent Rand
 */

public final class getSubmissionTest {
	public static void main(String[] args) throws Exception {

		Utils.setUserAgent("getSubmissions-Test");

		//connect to a User
		User user = new User("_karmawhore", "xx380813xx,,");
		user.connect();

		//use the new Submissions.getSubmission("subreddit") method
		List<Submission> submissions = Submissions.getSubmissions("funny");
		
		//print out something from every element to show it works
		for (int x = 0; x < submissions.size(); x++) {
			System.out.println(submissions.get(x).getTitle());
		}

	}
}