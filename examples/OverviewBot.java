
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.submissions.Submissions;
import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.SortOption;
import im.goel.jreddit.utils.TimeOption;
import im.goel.jreddit.utils.Utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Simple bot to print submissions from your profile...
 * 
 * @author Evin Ugur
 */
public final class OverviewBot {
	public static void main(String[] args) throws Exception {
		Utils.setUserAgent("Overview-Bot");
		User user = new User("user", "pass");
		user.connect();
		
		
		Subreddit sub = new Subreddit ("funny", user);
		//sub.print();
		LinkedList<Submission> items = Submissions.getSubmissions(sub, SortOption.TOP, TimeOption.WEEK, 10, user);
		
		for (Submission s : items){
			System.out.println(s);
		}
		//Submission Array from user profile
		List<Submission> submissions = user.getDislikedSubmissions();
									 //user.getLikedSubmissions();
									 //user.getHiddenSubmissions();
									 //user.getSavedSubmissions();
									 //user.getSubmissions();
		for (int i = 0; i < submissions.size(); i++) System.out.println(submissions.get(i).getTitle());
	}
}