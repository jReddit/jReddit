package examples;

import org.json.simple.parser.ParseException;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.exception.RedditOAuthException;

public class ExampleAppOnlyToken {

    public static void main(String[] args) throws RedditOAuthException, ParseException {

        // Information about the app
        String userAgent = "jReddit: Reddit API Wrapper for Java";
        String clientID = "PfnhLt3VahLrbg";
        String redirectURI = "https://github.com/snkas/jReddit";
        
        // Reddit application
        RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
        
        // Create OAuth agent
        RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);    

        // Create token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);
        System.out.println("Access Token: " + token.getAccessToken());
        System.out.println("Token Type: " + token.getTokenType());
        System.out.println("Refreshable: " + token.isRefreshable());
        System.out.println("Expired: " + token.isExpired());
        System.out.println("Expiration: " + token.getExpiration());
        System.out.println("Will expire in 61 minutes: " + token.willExpireIn((long) 3560));
        System.out.println("Will expire in 59 minutes: " + token.willExpireIn((long) 3540));

    }
    
}
