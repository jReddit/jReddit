package examples;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.simple.parser.ParseException;

import com.github.jreddit.app.RedditApp;
import com.github.jreddit.app.RedditInstalledApp;
import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.param.RedditScope;
import com.github.jreddit.oauth.param.RedditScopeBuilder;

public class ExampleUserImplicitGrantFlowAuthorization {

	public static void main(String[] args) throws OAuthSystemException, OAuthProblemException, ParseException {

		// Information about the app
		String userAgent = "jReddit: Reddit API Wrapper for Java";
		String clientID = "PfnhLt3VahLrbg";
		String redirectURI = "https://github.com/snkas/jReddit";
		
		// Reddit application
		RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
		
		// Create OAuth agent
		RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);	
		
		// Create scope
		RedditScopeBuilder scope = new RedditScopeBuilder().addScope(RedditScope.FLAIR);
		
		// Show URI for the user
		System.out.println(agent.generateImplicitFlowURI(scope));

	}
	
}
