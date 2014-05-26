
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.RestClient;
import com.github.jreddit.utils.restclient.HttpRestClient;

import java.util.List;

/**
 * Simple bot to print submissions from your profile...
 * 
 * @author Evin Ugur
 */
public final class OverviewBot {
	public static void main(String[] args) throws Exception {
        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("Overview-Bot");

        User user = new User(restClient, "user", "password");
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