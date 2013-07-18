#jReddit

##What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. Project started by Omer Elnour.

##What can it do?
jReddit can login with a user, retrieve user information, submit new links, and vote/comment on submissions, send and receive messages and notifications among other things.

##What's next for jReddit?
I plan to implement every feature documented [here](http://www.reddit.com/dev/api). To see which methods have been implemented, and which have not, see [this file](https://github.com/thekarangoel/jReddit/blob/karan/implemented_methods.md).

##What licence is jReddit released under?
You are permitted to use, copy, modify, and distribute the Software and its documentation, with or without modification, for any purpose, provided that the following conditions are met:

A copy of this license agreement must be included with the distribution.
Redistributions in binary form must reproduce the above copyright notice in the documentation and/or other materials provided with the distribution.
Commercial products derived from the Software must include an acknowledgment of the author(s) of jReddit in their documentation and/or other materials provided with the distribution.
Products derived from the Software may not be called "jReddit", nor may "jReddit" appear in their name, without prior written permission from the author(s).

See LICENCE.txt for more info.

##Dependencies
[JSON-simple](http://code.google.com/p/json-simple/)
##Examples

Upvote every submission on the frontpage of a subreddit

    import im.goel.jreddit.submissions.Submission;
    import im.goel.jreddit.submissions.Submissions;
    import im.goel.jreddit.user.User;

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

	import im.goel.jreddit.user.User;
	
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

	import im.goel.jreddit.submissions.Submission;
	import im.goel.jreddit.user.User;
	
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

	import im.goel.jreddit.message.Messages;
	import im.goel.jreddit.user.User;


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
