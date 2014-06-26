package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.submissions.ExtendedSubmissions;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchSort;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
import com.github.jreddit.utils.UserSubmissionsCategory;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmissionsExample {

	public static void main(String[] args) {
		
		// Initialize REST Client
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("bot/1.0 by name");

		// Connect the user 
	    User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		try {
			user.connect();
		} catch (IOException e1) {
			System.err.println("I/O Exception occured when attempting to connect user.");
			e1.printStackTrace();
			return;
		} catch (ParseException e1) {
			System.err.println("I/O Exception occured when attempting to connect user.");
			e1.printStackTrace();
			return;
		}

		
		/***************************************************************************************************
		 * First: basic API functionality
		*/
		
		// Handle to Submissions, which offers the basic API functionality
		Submissions subms = new Submissions(restClient);
		
		// Retrieve submissions of a submission
		System.out.println("\n============== Basic subreddit submissions ==============");
		List<Submission> submissionsSubreddit = subms.ofSubreddit(user, "flowers", SubmissionsGetSort.TOP, -1, 100, null, null, true);
		printSubmissionsList(submissionsSubreddit);
		
		// Search for submissions
		System.out.println("\n============== Basic search submissions ==============");
		List<Submission> submissionsSearch = subms.search(user, "flowers", null, null, SubmissionsSearchTime.MONTH, -1, 100, null, null, true);
		printSubmissionsList(submissionsSearch);
		
		// Retrieve submissions of a user
		System.out.println("\n============== Basic user submissions ==============");
		List<Submission> submissionsUser = subms.ofUser(user, "Unidan", UserSubmissionsCategory.SUBMITTED, UserOverviewSort.TOP, -1, 100, null, null, true);
		printSubmissionsList(submissionsUser);
		
		
		/***************************************************************************************************
		 * Second: extended API functionality
		*/
		
		// Handle to ExtendedSubmissions, which offers functionality beyond the Reddit API
		ExtendedSubmissions extendedSubms = new ExtendedSubmissions(subms);
		
		// Retrieve the top 323 submissions of funny
		System.out.println("\n============== Extended subreddits submissions retrieval ==============");
		List<Submission> submissionsSubredditExtra = extendedSubms.ofSubreddit(user, "funny", SubmissionsGetSort.TOP, 323, null);
		printSubmissionsList(submissionsSubredditExtra);

		// Retrieve the top 532 submissions of query "valentine", user is not given (which is optional)
		System.out.println("\n============== Extended search submissions retrieval ==============");
		List<Submission> submissionsSearchExtra = extendedSubms.search("valentine", SubmissionsSearchSort.RELEVANCE, SubmissionsSearchTime.ALL, 532);
		printSubmissionsList(submissionsSearchExtra);
		
		// Retrieve the top 233 submissions of a user
		System.out.println("\n============== Extended user submissions retrieval ==============");
		List<Submission> submissionsUserExtra = extendedSubms.ofUser(user, "Unidan", UserSubmissionsCategory.SUBMITTED, UserOverviewSort.HOT, 233);
		printSubmissionsList(submissionsUserExtra);

	}
	
	public static void printSubmissionsList(List<Submission> subs) {
		for (Submission s : subs) {
			System.out.println(s);
		}
	}
	
}
