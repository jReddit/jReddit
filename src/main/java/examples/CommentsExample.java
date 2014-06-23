package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.comment.Comment;
import com.github.jreddit.comment.Comments;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class CommentsExample {

	public static void main(String[] args) {
		
		// Initialize REST Client
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("bot/1.0 by name");

		// Connect the user
	    User user = new User(restClient, "ER-bot", "peanut34");
		try {
			user.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// Create handle to retrieve submissions
		Comments coms = new Comments(restClient);
		
		// Retrieve some submissions!
		
		//List<Comment> comments1 = coms.ofSubmission(user, "28obnl", null, 0, 1, 1, CommentSort.NEW);
		List<Comment> comments1 = coms.parseRecursive(user, "/comments/28svdn.json?comment=cieirih");//"/comments/28svdn.json?&context=0&depth=2&limit=500&sort=popular");
		//.ofUser(user, "Unidan", null, null, -1, 133, null, null, false);
		
		System.out.println("Size: " + comments1.size());
		System.out.println("Last: " + comments1.get(comments1.size() - 1));

		int i = 0;
		for (Comment c : comments1) {
			//if (i % 10 == 0 || i == comments1.size() - 1) {
			//System.out.println(c);
			//}
			i++;
		}

	}
	
}
