import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.user.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class MarkNSFWTest {
    @Test
    public void test() throws Exception {
        User user = new User("jReddittest", "jReddittest"); // Add your username and password
        user.connect();

        List<Submission> submissions = user.getSubmissions();

        Submission s = submissions.get(0);

        if (s.isNSFW()) {
            System.out.println("Unmarking NSFW");
            new Submission(user, s.getName(), s.getURL()).unmarkNSFW();
            submissions = user.getSubmissions();

            Submission s2 = submissions.get(0);
            Assert.assertFalse(s2.isNSFW());
        } else {
            System.out.println("Marking NSFW");
            new Submission(user, s.getName(), s.getURL()).markNSFW();
            submissions = user.getSubmissions();

            Submission s2 = submissions.get(0);
            Assert.assertTrue(s2.isNSFW());
        }



    }

}
