# jReddit

### What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. Project started by Omer Elnour.

### What can it do?
jReddit can login with a user, retrieve user information, submit new links, and vote/comment on submissions, send and receive messages and notifications among other things.

### What's next for jReddit?
The plan is to implement every feature documented [here](http://www.reddit.com/dev/api). To see which methods have been implemented, and which have not, see [this file](https://github.com/thekarangoel/jReddit/blob/karan/implemented_methods.md).

### How to contribute?
Personally, I would suggest reading through the source code to understand the general structure and standards used. Then check the [implemented_methods.md](https://github.com/thekarangoel/jReddit/blob/karan/implemented_methods.md) file to see which methods have not yet been implemented. Choose the ones you'd like to contribute to. After you write the method (and maybe commit it?), write a test to see if it works fine and as expected. Then make sure other tests are working too and your code does not break anty other method.

Send in a pull request with the test and I'll be happy to merge! :-)

### Dependencies
[JSON-simple](http://code.google.com/p/json-simple/)

### Examples

Upvote every submission on the frontpage of a subreddit

    import com.github.jreddit.submissions.Submission;
    import com.github.jreddit.submissions.Submissions;
    import com.github.jreddit.user.User;

    public final class Test {
	    public static void main(String[] args) throws Exception {
		    User user = new User("username", "password");
		    user.connect();

		    for (Submission submission : Submissions.getSubmissions("programming",
				    Submissions.HOT, Submissions.FRONTPAGE, user)) {
			    submission.upVote();
		    }
	    }
    }

Submit a link and self post

	import com.github.jreddit.user.User;
	
	public final class Test {
		public static void main(String[] args) throws Exception {
			User user = new User("username", "password");
			user.connect();
	
			user.submitLink(
					"Oracle V Google judge is a programmer!",
					"http://www.i-programmer.info/news/193-android/4224-oracle-v-google-judge-is-a-programmer.html",
					"programming");
			user.submitSelfPost("What's the difference between a duck?",
					"One of its legs are both the same!", "funny");
		}
	}
	
List all submissions made by user called USERNAME_OF_OTHER_USER

	import com.github.jreddit.submissions.Submission;
	import com.github.jreddit.user.User;
	
	/**
	 * @author Benjamin Jakobus
	 */
	public final class Test {
		public static void main(String[] args) throws Exception {
			User user = new User("username", "password");
        		user.connect();

        		List<Submission> submissions = User.submissions("USERNAME_OF_OTHER_USER");
        		// To list hidden submissions, user User.hidden("...");
		
			for (Submission s : submissions) {
				// Print info here
			}
		}
	}

Send a message to another user

	import com.github.jreddit.message.Messages;
	import com.github.jreddit.user.User;


	public class ComposeTest {

		/**
		 * @author Karan Goel
		 */
		public static void main(String[] args) {
			User user = null;
			username_of_recipient = "other_user";
			try {
				user = new User("username", "password"); // Add your username and password
				user.connect();
			} catch (Exception exception) {
				exception.printStackTrace();
			}
			
			new Messages().compose(user, username_of_recipient, "this is the title", "the message", "", "");
		}

	}
