package examples;

import java.io.IOException;
import java.util.LinkedList;

import org.json.simple.parser.ParseException;

import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.submissions.Submissions.Sort;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class SubmissionsExample {

	public static void main(String[] args) {
		
		// Initialize REST Client
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("bot/1.0 by name");

		// Connect the user
	    User user = new User(restClient, "username", "password");
		try {
			user.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// Create handle to retrieve submissions
		Submissions subms = new Submissions(restClient);
		
		// Retrieve some submissions!
		try {
			int desired = 50;
			LinkedList<Submission> submissions = subms.getSubmissions("askreddit", null, Sort.TOP, desired, null);
			System.out.println("API Caller: received " + submissions.size() + " of the desired " + desired + " submissions.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
}
