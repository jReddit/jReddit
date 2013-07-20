import static org.junit.Assert.*;
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.SortOption;
import im.goel.jreddit.utils.TimeOption;

import java.util.LinkedList;

import org.junit.Test;


public class SubmissionTest {
	@Test
	public void test() {
		LinkedList<Submission> list = null;
		Submission first = null;

		try {
			User user = new User("test_subject_666", "beef");
			user.connect();

			list = Submissions.getSubmissions("all", SortOption.HOT, TimeOption.DAY, 20, user);
			first = list.get(0);
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		assertNotSame(
				"The submission's ID/full name can't be empty, how will reddit identify the submission?",
				first.getTitle(), "");
		assertNotNull(
				"The submission's ID/full name can't be null, how will reddit identify the submission?",
				first.getTitle());
	}
}