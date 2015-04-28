package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.ExtendedSubmissions;
import com.github.jreddit.retrieval.Submissions;
import com.github.jreddit.retrieval.params.SubmissionSort;
import com.github.jreddit.retrieval.params.SearchSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.retrieval.params.UserSubmissionsCategory;
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

		try {
			
			/***************************************************************************************************
			 * First: basic API functionality
			*/
			
			// Handle to Submissions, which offers the basic API functionality
			Submissions subms = new Submissions(restClient, user);
			
			// Retrieve submissions of a submission
			System.out.println("\n============== Basic subreddit submissions ==============");
			List<Submission> submissionsSubreddit = subms.ofSubreddit("flowers", SubmissionSort.TOP, -1, 100, null, null, true);
			printSubmissionsList(submissionsSubreddit);

            System.out.println("\n============== Basic main page submissions ==============");
            List<Submission> hotSubmissions = subms.parse("/hot.json");
            printSubmissionsList(hotSubmissions);

            // Search for submissions
			System.out.println("\n============== Basic search submissions ==============");
			List<Submission> submissionsSearch = subms.search("flowers", null, null, TimeSpan.MONTH, -1, 100, null, null, true);
			printSubmissionsList(submissionsSearch);

			// Retrieve submissions of a user
			System.out.println("\n============== Basic user submissions ==============");
			List<Submission> submissionsUser = subms.ofUser("Unidan", UserSubmissionsCategory.SUBMITTED, UserOverviewSort.TOP, -1, 100, null, null, true);
			printSubmissionsList(submissionsUser);
			
			
			/***************************************************************************************************
			 * Second: extended API functionality
			*/
			
			// Handle to ExtendedSubmissions, which offers functionality beyond the Reddit API
			ExtendedSubmissions extendedSubms = new ExtendedSubmissions(subms);
			
			// Retrieve the top 323 submissions of funny
			System.out.println("\n============== Extended subreddits submissions retrieval ==============");
			List<Submission> submissionsSubredditExtra = extendedSubms.ofSubreddit("funny", SubmissionSort.TOP, 323, null);
			printSubmissionsList(submissionsSubredditExtra);
	
			// Retrieve the top 532 submissions of query "valentine", user is not given (which is optional)
			System.out.println("\n============== Extended search submissions retrieval ==============");
			List<Submission> submissionsSearchExtra = extendedSubms.search("valentine", SearchSort.RELEVANCE, TimeSpan.ALL, 532);
			printSubmissionsList(submissionsSearchExtra);
			
			// Retrieve the top 233 submissions of a user
			System.out.println("\n============== Extended user submissions retrieval ==============");
			List<Submission> submissionsUserExtra = extendedSubms.ofUser("Unidan", UserSubmissionsCategory.SUBMITTED, UserOverviewSort.HOT, 233);
			printSubmissionsList(submissionsUserExtra);
			
		} catch (RetrievalFailedException e) {
			e.printStackTrace();
		} catch (RedditError e) {
			e.printStackTrace();
		}

	}
	
	public static void printSubmissionsList(List<Submission> subs) {
		for (Submission s : subs) {
			System.out.println(s);
		}
	}
	
}
