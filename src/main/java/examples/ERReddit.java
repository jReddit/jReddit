package examples;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.parser.ParseException;

import com.github.jreddit.comment.Comment;
import com.github.jreddit.comment.Comments;
import com.github.jreddit.comment.ExtendedComments;
import com.github.jreddit.submissions.ExtendedSubmissions;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.subreddit.ExtendedSubreddits;
import com.github.jreddit.subreddit.Subreddits;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.UserOverviewSort;
import com.github.jreddit.utils.UserSubmissionsCategory;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class ERReddit {

	public static void main(String[] args) {
		
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("ER-bot/1.0 by sk-TUD");
	
		User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		try {
			user.connect();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	
		// Object that perform the functions
		Submissions subms = new Submissions(restClient);
		ExtendedSubmissions extSubms = new ExtendedSubmissions(subms);
		Subreddits subrs = new Subreddits(restClient);
		ExtendedSubreddits extSubrs = new ExtendedSubreddits(subrs);
		Comments comts = new Comments(restClient);
		ExtendedComments extComts = new ExtendedComments(comts);
		
		/* Retrieve top 100 subreddits of 'flowers' and their post amount
		List<Subreddit> subreddits = extSubrs.search(user, "flowers", 100);
		for (Subreddit s : subreddits) {
			List<Submission> submissions = extSubms.get(s.getDisplayName(), SubmissionsGetSort.TOP, 800, null);
			System.out.println(s.getDisplayName() + "\t" + (submissions != null ? submissions.size() : 0));
		}*/
		
		Set<String> usersfound_all = new HashSet<String>();
		
		/* Get the amount of c */
		String[] subreddits = { "flowers" };
		for (String s : subreddits) {
			
			Set<String> usersfound_local_submission = new HashSet<String>();
			Set<String> usersfound_local_comment = new HashSet<String>();
			int amount_of_comments = 0;
			
			// Iterate over all submissions in the subreddit
			List<Submission> submissions = extSubms.get(s, SubmissionsGetSort.TOP, 50, null);
			int i = 0;
			for (Submission submission : submissions) {
				
				// Add the author to users the contributed
				usersfound_local_submission.add(submission.getAuthor());
				List<Comment> comments = comts.ofSubmission(user, submission.getIdentifier(), null, 0, 8, 500, CommentSort.TOP);
				amount_of_comments += comments.size();
				
				// Iterate over all the comments in the submission
				for (Comment comment : comments) {
					usersfound_local_comment.add(comment.getAuthor());
				}
				System.out.println(i);
				i++;
			}
			
			Set<String> userfound_local = new HashSet<String>();
			userfound_local.addAll(usersfound_local_submission);
			userfound_local.addAll(usersfound_local_comment);
			/*
			System.out.println("A total of " + usersfound_local_submission.size() + " users submitted a post.");
			System.out.println("A total of " + usersfound_local_comment.size() + " users commented.");
			System.out.println("A total of " + amount_of_comments + " comments were made in " + submissions.size() + " submissions.");
			System.out.println("This is an average of " + (((double) amount_of_comments / (double) submissions.size())) + " comments per submission");
			System.out.println("Users found in total: " + userfound_local.size());
			*/
			usersfound_all.addAll(userfound_local);
			
		}
		
		for (String s : usersfound_all) {
			List<Submission> submissions = extSubms.ofUser(user, s, UserSubmissionsCategory.SUBMITTED, UserOverviewSort.TOP, 800);
			List<Comment> comments = extComts.ofUser(user, s, UserOverviewSort.TOP, SubmissionsSearchTime.ALL, 800, null);
			System.out.println(s + "\t" + submissions.size() + "\t" + comments.size());
		}

		

		
	}
	
}
