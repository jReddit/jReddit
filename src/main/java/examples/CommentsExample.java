package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.comment.Comment;
import com.github.jreddit.comment.Comments;
import com.github.jreddit.comment.ExtendedComments;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.RedditConstants;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
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

		
		/***************************************************************************************************
		 * First: basic API functionality
		*/
		
		// Handle to Comments, which offers the basic API functionality
		Comments coms = new Comments(restClient);
		
		// Retrieve comments of a submission
		System.out.println("\n============== Basic submission comments ==============");
		List<Comment> commentsSubmission = coms.ofSubmission(user, "2911vg", null, 0, 8, 100, CommentSort.TOP);
		Comments.printCommentTree(commentsSubmission);
		
		
		// Retrieve comments of a user
		System.out.println("\n============== Basic user comments ==============");
		List<Comment> commentsUser = coms.ofUser(user, "Unidan", UserOverviewSort.NEW, SubmissionsSearchTime.ALL, -1, 80, null, null, true);
		Comments.printCommentTree(commentsUser); // Note: this tree is already flat, because listing is one level
		
		
		/***************************************************************************************************
		 * Second: extended API functionality
		*/
		
		// Handle to ExtendedComments, which offers functionality beyond the Reddit API
		ExtendedComments extendedComs = new ExtendedComments(new Comments(restClient));
		
		// Retrieve the top 50 first level comments, and all their (up to 500) subcomments each
		System.out.println("\n============== Extended submission comments retrieval ==============");
		List<Comment> commentsSubmissionExtra = extendedComs.ofSubmission(user, "2911vg", CommentSort.TOP, 50, null);
		Comments.printCommentTree(commentsSubmissionExtra);

		// Retrieve all comments that are possible to retrieve from a user (there is a limit in Reddit which only
		// allows you to retrieve about ~1000 posts in a listing, e.g. a subreddit or user posts listing)
		System.out.println("\n============== Extended user comments retrieval ==============");
		List<Comment> commentsUserExtra = extendedComs.ofUser(user, "Unidan", UserOverviewSort.HOT, SubmissionsSearchTime.ALL, RedditConstants.APPROXIMATE_MAX_LISTING_AMOUNT, null);
		Comments.printCommentTree(commentsUserExtra);

	}
	
}
