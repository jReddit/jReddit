package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.Comments;
import com.github.jreddit.retrieval.ExtendedComments;
import com.github.jreddit.retrieval.params.CommentSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class CommentsExample {

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
			
			// Handle to Comments, which offers the basic API functionality
			Comments coms = new Comments(restClient, user);
			
			// Retrieve comments of a submission
			System.out.println("\n============== Basic submission comments ==============");
			List<Comment> commentsSubmission = coms.ofSubmission("29p8fe", null, 0, 8, 100, CommentSort.TOP);
			Comments.printCommentTree(commentsSubmission);
			
			
			// Retrieve comments of a user
			System.out.println("\n============== Basic user comments ==============");
			List<Comment> commentsUser = coms.ofUser("Unidan", UserOverviewSort.NEW, TimeSpan.ALL, -1, 80, null, null, true);
			Comments.printCommentTree(commentsUser); // Note: this tree is already flat, because listing is one level
			
			
			/***************************************************************************************************
			 * Second: extended API functionality
			*/
			
			// Handle to ExtendedComments, which offers functionality beyond the Reddit API
			ExtendedComments extendedComs = new ExtendedComments(coms);
			
			// Retrieve the top 50 first level comments, and all their (up to 500) subcomments each
			System.out.println("\n============== Extended submission comments retrieval ==============");
			List<Comment> commentsSubmissionExtra = extendedComs.ofSubmission("2911vg", CommentSort.TOP, 50, null);
			Comments.printCommentTree(commentsSubmissionExtra);
	
			// Retrieve all comments that are possible to retrieve from a user (there is a limit in Reddit which only
			// allows you to retrieve about ~1000 posts in a listing, e.g. a subreddit or user posts listing)
			System.out.println("\n============== Extended user comments retrieval ==============");
			List<Comment> commentsUserExtra = extendedComs.ofUser("Unidan", UserOverviewSort.HOT, TimeSpan.ALL, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT, null);
			Comments.printCommentTree(commentsUserExtra);
			
		} catch (RetrievalFailedException e) {
			e.printStackTrace();
		} catch (RedditError e) {
			e.printStackTrace();
		}

	}
	
}
