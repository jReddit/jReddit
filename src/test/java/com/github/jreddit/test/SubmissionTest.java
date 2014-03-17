package com.github.jreddit.test;

import com.github.jreddit.utils.TestUtils;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.submissions.Submissions.Page;
import com.github.jreddit.submissions.Submissions.Popularity;
import com.github.jreddit.user.User;
import org.junit.Before;
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

    private static User user = TestUtils.createAndConnectUser();

	@Test
	public void test() {
		try {
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

        try {
            List<Submission> submissions = user.getSubmissions();
            if (submissions.size() > 0) {
                // Get a submission - the first one
                Submission initialSubmission = submissions.get(0);

                if (initialSubmission.isNSFW()) {
                    System.out.println("Unmarking NSFW");

                    initialSubmission.setUser(user);
                    initialSubmission.unmarkNSFW();
                    List<Submission> submissionList = user.getSubmissions();

                    for (Submission submission: submissionList) {
                        if (submission.getURL().equals(initialSubmission.getURL())) {
                            assertFalse(submission.isNSFW());
                            break;
                        }
                    }

                } else {
                    System.out.println("Marking NSFW");

                    initialSubmission.setUser(user);
                    initialSubmission.markNSFW();

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