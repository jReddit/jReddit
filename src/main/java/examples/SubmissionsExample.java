package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.submissions.ExtendedSubmissions;
import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.SubmissionsGetSort;
import com.github.jreddit.utils.SubmissionsSearchSort;
import com.github.jreddit.utils.SubmissionsSearchTime;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmissionsExample {

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
		ExtendedSubmissions subms = new ExtendedSubmissions(new Submissions(restClient));
		
		// Retrieve some submissions!
		try {
			int desired1 = 322;
			List<Submission> submissions1 = subms.get("askreddit", SubmissionsGetSort.RISING, desired1);
			System.out.println("API Caller (get): received " + submissions1.size() + " of the desired " + desired1 + " submissions.");
			
			int desired2 = 142;
			//List<Submission> submissions2 = subms.search("subreddit:askreddit AND title:valentine", SubmissionsSearchSort.NEW, SubmissionsSearchTime.YEAR, desired2);
			//System.out.println("API Caller (search): received " + submissions2.size() + " of the desired " + desired2 + " submissions.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
}
