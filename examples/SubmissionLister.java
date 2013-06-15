import com.omrlnr.jreddit.Submission;
import com.omrlnr.jreddit.Submissions;
import com.omrlnr.jreddit.User;

import com.omrlnr.jreddit.utils.Utils;

import java.util.List;

/**
 *
 * A simple example that lists submissions in a subreddit
 * 
 */
public class SubmissionLister {

    public static void main(String[] args) throws Exception {

        String username     = args[0];
        String password     = args[1];
        String subreddit    = args[2];

        User user = new User(username, password);
        user.connect();
        
        List<Submission> submissions = Submissions.getSubmissions(
                                                subreddit,
                                                Submissions.NEW,
                                                Submissions.FRONTPAGE,
                                                user);

        for(Submission submission: submissions) {
            System.out.println(submission);
        }

        System.out.println("Total submissions: " + submissions.size());
    }

}
