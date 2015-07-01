package examples;

import java.util.List;

import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.parser.ParseException;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.Subreddit;
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.listing.SubredditsListingParser;
import com.github.jreddit.request.retrieval.param.SubredditsView;
import com.github.jreddit.request.retrieval.subreddits.SubredditsOfUserRequest;

public class ExampleContinuousRequests {

    public static void main(String[] args) throws RedditOAuthException, ParseException, RedditParseException {

        /********************************************
         * Initialization
        */
        
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
        
        /********************************************
         * Perform requests in a loop
        */
        
        // Create token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);

        // Create parser for request
        SubredditsListingParser parser = new SubredditsListingParser();
        
        // Create the request
        SubredditsOfUserRequest request = (SubredditsOfUserRequest) new SubredditsOfUserRequest(SubredditsView.NEW).setLimit(100);

        // Let's assume we want to show the results every 10 minutes
        long lastShow = 0;
        while (true) {
        
            // Create a new token if the current has expired or will expire very soon (e.g. 100s)
            if (token.isExpired() || token.willExpireIn(100)) {
                token = agent.tokenAppOnly(false);
            }

            // Every 10 minutes = 600 seconds = 600000 milliseconds
            if (System.currentTimeMillis() - lastShow > 600000) {
            
                // Perform and parse request, and store parsed result
                List<Subreddit> subreddits = parser.parse(client.get(token, request));
                
                // Now print out the result (don't care about formatting)
                System.out.println(subreddits);
                
                // Result shown, wait for next time
                lastShow = System.currentTimeMillis();
            
            }

        }

    }
    
}
