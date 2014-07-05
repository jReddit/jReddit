package examples;

import java.io.IOException;
import java.util.List;

import org.json.simple.parser.ParseException;

import com.github.jreddit.entity.Subreddit;
import com.github.jreddit.entity.User;
import com.github.jreddit.exception.RedditError;
import com.github.jreddit.exception.RetrievalFailedException;
import com.github.jreddit.retrieval.ExtendedSubreddits;
import com.github.jreddit.retrieval.Subreddits;
import com.github.jreddit.retrieval.params.SubredditsView;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public class SubredditsExample {

	public static void main(String[] args) {
		
		// Initialize REST Client
	    RestClient restClient = new HttpRestClient();
	    restClient.setUserAgent("bot/1.0 by name");

		// Connect the user
	    User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
		try {
			user.connect();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		// Create handle to retrieve submissions
		Subreddits subrs = new Subreddits(restClient, user);
		ExtendedSubreddits extSubrs = new ExtendedSubreddits(subrs);
		
		try {
		
		// Retrieve some submissions!

				//List<Subreddit> getsubreddits = subrs.get(SubredditsView.NEW, 36);
				//System.out.println("Get subreddits, size received: " + getsubreddits.size());
				
				// Other possibilities:
				// subrs.get(SubredditType.NEW);
				// subrs.get(SubredditType.POPULAR, 64);
				// subrs.get(SubredditType.NEW, 22, "t1_29429");
				// subrs.get(user, SubredditType.NEW);
				// subrs.get(user, SubredditType.POPULAR, 33);
				// subrs.get(user, SubredditType.MINE, 16);
				// subrs.get(user, SubredditType.NEW, 22, "t1_29429");
				
				List<Subreddit> searchsubreddits = extSubrs.search("abbot", 10);
				System.out.println("Search subreddits, size received: " + searchsubreddits.size());
				
				// Other possibilities:
				// subrs.search("query");
				// subrs.search("query", 64);
				// subrs.search("query", 22, "t1_29429");
				// subrs.search(user, "query");
				// subrs.search(user, "query", 33);
				// subrs.search(user, "query", 22, "t1_29429");
				
				/* Enable to just iterate over all the subreddits:
				  for (Subreddit s : subreddits) {
					System.out.println(s.getDisplayName());
				}
				*/
				
		} catch (RetrievalFailedException e) {
			e.printStackTrace();
		} catch (RedditError e) {
			e.printStackTrace();
		}

	
	}
	
}
