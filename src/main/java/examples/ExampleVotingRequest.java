package examples;

import org.apache.http.impl.client.HttpClientBuilder;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.app.RedditScriptApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.oauth.param.RedditDuration;
import com.github.jreddit.oauth.param.RedditScope;
import com.github.jreddit.oauth.param.RedditScopeBuilder;
import com.github.jreddit.request.action.mark.VoteRequest;

public class ExampleVotingRequest {

    public static void main(String[] args) throws RedditOAuthException {

        // Information about the app
        String userAgent = "jReddit: Reddit API Wrapper for Java";
        String clientID = "4Y4QrWwnimVICg";
        String redirectURI = "https://github.com/snkas/jReddit";
        String secret = "A5ElXMsO57NwlRnRE72tJRKg-JU";
        
        // Reddit application
        RedditApp redditApp = new RedditScriptApp(clientID, secret, redirectURI);
        
        // Create OAuth agent
        RedditOAuthAgent agent = new RedditOAuthAgent(userAgent, redditApp);    
        
        // Create request executor 
        RedditClient client = new RedditHttpClient(userAgent, HttpClientBuilder.create().build());
        
        RedditScopeBuilder scopeBuilder = new RedditScopeBuilder().addScope(RedditScope.VOTE);
        System.out.println(agent.generateCodeFlowURI(scopeBuilder, RedditDuration.TEMPORARY));
        
        // Input the code below:
        String code = "ZdBHUVaM92e0Rx-w_h5zLf0DU4I";
        
        // Ask for token
        RedditToken token = agent.token(code);
        // To try when not authenticated: RedditToken token = agent.tokenAppOnly(false);

        // Create the request
        VoteRequest request = new VoteRequest("t3_3atvlh", 1);
        
        // Perform and parse request, and store parsed result
        System.out.println(client.post(token, request)); // Should be an empty object if success
        
        System.out.println(agent.revoke(token, true));

    }
    
}
