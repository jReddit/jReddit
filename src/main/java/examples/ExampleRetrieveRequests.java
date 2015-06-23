package examples;

import java.util.List;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.simple.parser.ParseException;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.client.RedditPoliteClient;
import com.github.jreddit.parser.SubmissionsListingParser;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.request.error.RedditError;
import com.github.jreddit.request.listing.submissions.SubmissionsOfSubredditRequest;
import com.github.jreddit.request.listing.submissions.SubmissionsOfUserRequest;
import com.github.jreddit.request.param.SubmissionSort;
import com.github.jreddit.request.param.UserSubmissionsCategory;

public class ExampleRetrieveRequests {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException, ParseException, RedditError {
		ExampleRetrieveRequests example = new ExampleRetrieveRequests();
		example.exampleSubmissionsOfSubreddit();
		example.exampleSubmissionsOfUser();
	}

	// Information about the app
	public static final String USER_AGENT = "jReddit: Reddit API Wrapper for Java";
	public static final String CLIENT_ID = "PfnhLt3VahLrbg";
	public static final String REDIRECT_URI = "https://github.com/snkas/jReddit";
	
	// Variables
	private RedditApp redditApp;
	private RedditOAuthAgent agent;
	private RedditClient client;
	
	public ExampleRetrieveRequests() throws OAuthSystemException, OAuthProblemException {
		
		// Reddit application
		redditApp = new RedditInstalledApp(CLIENT_ID, REDIRECT_URI);
		
		// Create OAuth agent
		agent = new RedditOAuthAgent(USER_AGENT, redditApp);	
		
		// Create client
		client = new RedditPoliteClient(new RedditHttpClient(USER_AGENT, HttpClientBuilder.create().build()));

	}
	
	public void exampleSubmissionsOfSubreddit() throws RedditError, ParseException, OAuthSystemException, OAuthProblemException {
		
		// Create token (will be valid for 1 hour)
		RedditToken token = agent.tokenAppOnly(false);
		
		// Create parser for request
		SubmissionsListingParser parser = new SubmissionsListingParser();

		// Create the request
		SubmissionsOfSubredditRequest request = (SubmissionsOfSubredditRequest) new SubmissionsOfSubredditRequest("programming", SubmissionSort.HOT).setLimit(100);

		// Perform and parse request, and store parsed result
		List<Submission> submissions = parser.parse(client.get(token, request));
		
		// Now print out the result (don't care about formatting)
		System.out.println(submissions);

	}
	
	public void exampleSubmissionsOfUser() throws RedditError, ParseException, OAuthSystemException, OAuthProblemException {
		
		// Create token (will be valid for 1 hour)
		RedditToken token = agent.tokenAppOnly(false);
		
		// Create parser for request
		SubmissionsListingParser parser = new SubmissionsListingParser();

		// Create the request
		SubmissionsOfUserRequest request = (SubmissionsOfUserRequest) new SubmissionsOfUserRequest("jRedditBot", UserSubmissionsCategory.SUBMITTED);

		// Perform and parse request, and store parsed result
		List<Submission> submissions = parser.parse(client.get(token, request));
		
		// Now print out the result (don't care about formatting)
		System.out.println(submissions);

	}
	
}
