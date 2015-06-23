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
import com.github.jreddit.parser.SubmissionsParser;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.request.error.RedditError;
import com.github.jreddit.request.listing.SubmissionsOfSubredditRequest;
import com.github.jreddit.request.param.SubmissionSort;

public class ExampleSimpleRequest {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException, ParseException, RedditError {

		// Information about the app
		String userAgent = "jReddit: Reddit API Wrapper for Java";
		String clientID = "PfnhLt3VahLrbg";
		String redirectURI = "https://github.com/snkas/jReddit";
		
		// Reddit application
		RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
		
		// Create OAuth agent
		RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);	
		
		// Create request executor 
		RedditClient client = new RedditHttpClient(userAgent, HttpClientBuilder.create().build());
		
		// Create token (will be valid for 1 hour)
		RedditToken token = agent.tokenAppOnly(false);

		// Create parser for request
		SubmissionsParser parser = new SubmissionsParser();

		// Create the request
		SubmissionsOfSubredditRequest request = (SubmissionsOfSubredditRequest) new SubmissionsOfSubredditRequest("programming", SubmissionSort.HOT).setLimit(100);

		// Perform and parse request, and store parsed result
		List<Submission> submissions = parser.parse(client.get(token, request));
		
		// Now print out the result (don't care about formatting)
		System.out.println(submissions);

	}
	
}
