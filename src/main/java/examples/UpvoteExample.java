package examples;

import java.util.List;

import com.github.jreddit.action.SubmissionActions;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;


/**
 * A simple bot that upvotes every new submission in a subreddit (first 25).
 * 
 * @author Omer Elnour
 * @author Simon Kassing
 */
public class UpvoteExample {
	
	public static void main(String[] args) throws Exception {

        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("Generous-Bot");

		User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		user.connect();
		
		Submissions subms = new Submissions(restClient, user);
		List<Submission> submissions = subms.ofSubreddit("programming", SubmissionSort.HOT, 0, 25, null, null, true);

		SubmissionActions submAct = new SubmissionActions(restClient, user);
		
		for (Submission submission : submissions) {
			submAct.upVote(submission);
		}
		
	}
	
}