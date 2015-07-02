package examples;

import org.json.simple.parser.ParseException;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.exception.RedditOAuthException;

public class ExampleCodeFlowToken {

    public static void main(String[] args) throws RedditOAuthException, ParseException {
        
        // Information about the app
        String userAgent = "jReddit: Reddit API Wrapper for Java";
        String clientID = "PfnhLt3VahLrbg";
        String redirectURI = "https://github.com/snkas/jReddit";
        
        // Reddit application
        RedditApp redditApp = new RedditInstalledApp(clientID, redirectURI);
        
        // Create OAuth agent
        RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);    
        
        // Input the code below:
        String code = "rVrguWH-NL2EHrEAmP2KgiAS_wU";
        
        // Ask for token
        RedditToken token = agent.token(code);
        
        // Show some information about the token:
        System.out.println("Access Token: " + token.getAccessToken());
        System.out.println("Token Type: " + token.getTokenType());
        System.out.println("Expired: " + token.isExpired());
        System.out.println("Expiration moment: " + token.getExpiration());
        System.out.println("Expiration span: " + token.getExpirationSpan());
        System.out.println("Will expire in 61 minutes: " + token.willExpireIn((long) (61 * 60)));
        System.out.println("Will expire in 59 minutes: " + token.willExpireIn((long) (59 * 60)));
        System.out.println("Refreshable: " + token.isRefreshable());
        
        // If it is refreshable, do it and show it
        if (token.isRefreshable()) {
            
            System.out.println("Refresh token: " + token.getRefreshToken());
        
            // Try to refresh it
            if (agent.refreshToken(token)) {
                System.out.println("\nRefreshed Access Token: " + token.getAccessToken());
            }
        
        }

    }
    
}
