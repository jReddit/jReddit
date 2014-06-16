package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.SubmissionParams.SearchSort;
import com.github.jreddit.submissions.SubmissionParams.SearchTime;
import com.github.jreddit.submissions.SubmissionParams.SubredditSort;
import com.github.jreddit.submissions.Submissions;
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
			int desired1 = 322;
			List<Submission> submissions1 = subms.get("askreddit", SubredditSort.RISING, desired1);
			System.out.println("API Caller (get): received " + submissions1.size() + " of the desired " + desired1 + " submissions.");
			
			int desired2 = 142;
			List<Submission> submissions2 = subms.search("subreddit:askreddit AND title:valentine", SearchSort.NEW, SearchTime.YEAR, desired2);
			System.out.println("API Caller (search): received " + submissions2.size() + " of the desired " + desired2 + " submissions.");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}
	
}
