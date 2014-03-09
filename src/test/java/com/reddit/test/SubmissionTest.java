package com.reddit.test;

import com.reddit.utils.TestUtils;
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.submissions.Submissions.Page;
import im.goel.jreddit.submissions.Submissions.Popularity;
import im.goel.jreddit.user.User;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

/**
 * Class for testing Submission-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class SubmissionTest {
	@Test
	public void test() {

		try {
			User user = new User("test_subject_666", "beef");
			user.connect();

			List<Submission> frontPage = Submissions.getSubmissions("all", Popularity.HOT, Page.FRONTPAGE, user);
			Submission first = frontPage.get(0);

            assertNotSame(
                    "The submission's ID/full name can't be empty, how will reddit identify the submission?",
                    first.fullName, "");
            assertNotNull(
                    "The submission's ID/full name can't be null, how will reddit identify the submission?",
                    first.fullName);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

    @Test
    public void testMarkUnmarkNSFW() {
        User user = TestUtils.createAndConnectUser();

        try {
            List<Submission> submissions = user.getSubmissions();
            if (submissions.size() > 0) {
                // Get a submission - the first one
                Submission initialSubmission = submissions.get(0);

                if (initialSubmission.isNSFW()) {
                    System.out.println("Unmarking NSFW");

                    new Submission(user, initialSubmission.getName(), initialSubmission.getURL()).unmarkNSFW();
                    List<Submission> submissionList = user.getSubmissions();

                    for (Submission submission: submissionList) {
                        if (submission.getURL().equals(initialSubmission.getURL())) {
                            assertFalse(submission.isNSFW());
                            break;
                        }
                    }

                } else {
                    System.out.println("Marking NSFW");

                    new Submission(user, initialSubmission.getName(), initialSubmission.getURL()).markNSFW();
                    List<Submission> submissionList = user.getSubmissions();
                    for (Submission submission: submissionList) {
                        if (submission.getURL().equals(initialSubmission.getURL())) {
                            assertTrue(submission.isNSFW());
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error while trying to mark/unmark a submission as NSFW");
            assertTrue(false);
        }
    }
}