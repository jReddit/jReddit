import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.submissions.Submissions.Page;
import im.goel.jreddit.submissions.Submissions.Popularity;
import im.goel.jreddit.user.User;

import java.util.LinkedList;

import org.junit.Test;

public class SubmissionTest {

	@Test
	public void test() {
		LinkedList<Submission> frontpage = null;
		Submission first = null;

		try {
			User user = new User("test_subject_666", "beef");
			user.connect();

			frontpage = Submissions.getSubmissions("all", Popularity.HOT,
					Page.FRONTPAGE, user);
			first = frontpage.get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		assertNotSame(
				"The submission's ID/full name can't be empty, how will reddit identify the submission?",
				first.fullName, "");
		assertNotNull(
				"The submission's ID/full name can't be null, how will reddit identify the submission?",
				first.fullName);
	}
	
}