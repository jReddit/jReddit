
import im.goel.jreddit.submissions.Submission;
import im.goel.jreddit.subreddit.Subreddit;
import im.goel.jreddit.user.User;
import im.goel.jreddit.utils.Utils;

import java.util.List;

/**
 * Simple bot to print submissions from your profile...
 * 
 * @author Evin Ugur
 */
public final class OverviewBot {
	public static void main(String[] args) throws Exception {
		Utils.setUserAgent("Overview-Bot");
		User user = new User("Arcas_Turing", "lag25491");
		user.connect();
		
		
		Subreddit sub = new Subreddit ("funny", user);
		sub.print();
		//Submission Array from user profile
		//List<Submission> submissions = //user.getDislikedSubmissions();
									 //user.getLikedSubmissions();
									 //user.getHiddenSubmissions();
									 //user.getSavedSubmissions();
									 //user.getSubmissions();
		//for (int i = 0; i < submissions.size(); i++) System.out.println(submissions.get(i).getTitle());
	}
}