package com.github.jreddit.test.submissions;

import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class SubmissionsTest {

    private static User user;

    @BeforeClass
    public static void initUser() {
        try {
            user = TestUtils.createAndConnectUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        try {
            List<Submission> frontPage = Submissions.getSubmissions("all", Submissions.Popularity.HOT, Submissions.Page.FRONTPAGE, user);
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
}
