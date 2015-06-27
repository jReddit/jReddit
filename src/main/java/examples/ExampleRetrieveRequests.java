package examples;

import java.util.List;

import org.apache.http.impl.client.HttpClientBuilder;

import com.github.jreddit.oauth.RedditOAuthAgent;
import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.client.RedditClient;
import com.github.jreddit.oauth.client.RedditHttpClient;
import com.github.jreddit.oauth.client.RedditPoliteClient;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.parser.entity.More;
import com.github.jreddit.parser.entity.Submission;
import com.github.jreddit.parser.entity.imaginary.CommentTreeElement;
import com.github.jreddit.parser.entity.imaginary.FullSubmission;
import com.github.jreddit.parser.entity.imaginary.MixedListingElement;
import com.github.jreddit.parser.exception.RedditParseException;
import com.github.jreddit.parser.listing.CommentsMoreParser;
import com.github.jreddit.parser.listing.MixedListingParser;
import com.github.jreddit.parser.listing.SubmissionsListingParser;
import com.github.jreddit.parser.single.FullSubmissionParser;
import com.github.jreddit.parser.util.CommentTreeUtils;
import com.github.jreddit.request.retrieval.comments.MoreCommentsRequest;
import com.github.jreddit.request.retrieval.mixed.FullSubmissionRequest;
import com.github.jreddit.request.retrieval.mixed.MixedOfUserRequest;
import com.github.jreddit.request.retrieval.param.SubmissionSort;
import com.github.jreddit.request.retrieval.param.TimeSpan;
import com.github.jreddit.request.retrieval.param.UserMixedCategory;
import com.github.jreddit.request.retrieval.param.UserOverviewSort;
import com.github.jreddit.request.retrieval.param.UserSubmissionsCategory;
import com.github.jreddit.request.retrieval.submissions.SubmissionsOfSubredditRequest;
import com.github.jreddit.request.retrieval.submissions.SubmissionsOfUserRequest;

public class ExampleRetrieveRequests {

    // Information about the app
    public static final String USER_AGENT = "jReddit: Reddit API Wrapper for Java";
    public static final String CLIENT_ID = "PfnhLt3VahLrbg";
    public static final String REDIRECT_URI = "https://github.com/snkas/jReddit";
    
    // Variables
    private RedditApp redditApp;
    private RedditOAuthAgent agent;
    private RedditClient client;
    
    public ExampleRetrieveRequests() throws RedditOAuthException {
        
        // Reddit application
        redditApp = new RedditInstalledApp(CLIENT_ID, REDIRECT_URI);
        
        // Create OAuth agent
        agent = new RedditOAuthAgent(USER_AGENT, redditApp);    
        
        // Create client
        client = new RedditPoliteClient(new RedditHttpClient(USER_AGENT, HttpClientBuilder.create().build()));

    }
    
    public static void main(String[] args) throws RedditOAuthException, RedditParseException {
        ExampleRetrieveRequests example = new ExampleRetrieveRequests();
        //example.exampleSubmissionsOfSubreddit();
        //example.exampleSubmissionsOfUser();
        //example.exampleMixedOfUser();
        example.exampleFullSubmission();
    }
    
    public void exampleSubmissionsOfSubreddit() throws RedditParseException, RedditOAuthException {
        
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
    
    public void exampleSubmissionsOfUser() throws RedditParseException, RedditOAuthException {
        
        // Create token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);
        
        // Create parser for request
        SubmissionsListingParser parser = new SubmissionsListingParser();

        // Create the request
        SubmissionsOfUserRequest request = new SubmissionsOfUserRequest("jRedditBot", UserSubmissionsCategory.SUBMITTED);

        // Perform and parse request, and store parsed result
        List<Submission> submissions = parser.parse(client.get(token, request));
        
        // Now print out the result (don't care about formatting)
        System.out.println(submissions);

    }
    
    public void exampleMixedOfUser() throws RedditParseException, RedditOAuthException {
        
        // Create token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);
        
        // Create parser for request
        MixedListingParser parser = new MixedListingParser();

        // Create the request
        MixedOfUserRequest request = new MixedOfUserRequest("jRedditBot", UserMixedCategory.OVERVIEW)
                                                        .setSort(UserOverviewSort.TOP)
                                                        .setTime(TimeSpan.ALL);

        // Perform and parse request, and store parsed result
        List<MixedListingElement> elements = parser.parse(client.get(token, request));
        
        // Now print out the result (don't care about formatting)
        System.out.println(elements);

    }
    
    public void exampleFullSubmission() throws RedditParseException, RedditOAuthException {
        
        // Create token (will be valid for 1 hour)
        RedditToken token = agent.tokenAppOnly(false);
        
        // Create parser for request
        FullSubmissionParser parser = new FullSubmissionParser();

        // Create the request
        FullSubmissionRequest request = new FullSubmissionRequest("2np694").setDepth(1);

        // Perform and parse request, and store parsed result
        FullSubmission fullSubmission = parser.parse(client.get(token, request));
        
        // Now print out the result of the submission (don't care about formatting)
        Submission s = fullSubmission.getSubmission();
        System.out.println(s);
        
        // Now print out the result of the comment tree (don't care about formatting)
        System.out.println(CommentTreeUtils.printCommentTree(fullSubmission.getCommentTree()));
        
        // Flatten the tree
        List<CommentTreeElement> flat = CommentTreeUtils.flattenCommentTree(fullSubmission.getCommentTree());
        
        // Retrieve ALL comments hiding behind MOREs
        for (CommentTreeElement e : flat) {
            if (e instanceof More) {
                
                // Create the request for more comments
                MoreCommentsRequest requestMore = new MoreCommentsRequest(s.getFullName(), ((More) e).getChildren());
                
                // Perform and parse request, and store parsed result
                CommentsMoreParser parserMore = new CommentsMoreParser();
                System.out.println(parserMore.parse(client.get(token, requestMore)));
                
            }
        }

    }
    
}
