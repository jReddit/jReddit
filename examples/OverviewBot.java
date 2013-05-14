import com.omrlnr.jreddit.submissions.Submission;
import com.omrlnr.jreddit.user.User;
import com.omrlnr.jreddit.utils.Utils;
import java.util.List;

/**
 * Simple bot to print submissions from your profile...
 * 
 * @author Evin Ugur
 */
public final class OverviewBot {
	public static void main(String[] args) throws Exception {
		Utils.setUserAgent("Overview-Bot");
		User user = new User("user", "password");
		user.connect();
		
		//Submission Array from user profile
		List<Submission> submissions = user.getDislikedSubmissions();
									 //user.getLikedSubmissions();
									 //user.getHiddenSubmissions();
									 //user.getSavedSubmissions();
									 //user.getSubmissions();
		for (int i = 0; i < submissions.size(); i++) System.out.println(submissions.get(i).getTitle());
	}
}