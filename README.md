#jReddit
##What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. It is a work in progress.
##What can it do?
So far, jReddit can login with a user, retrieve user information, and vote/comment on submissions.
##What's next for jReddit?
I plan to implement every feature documented [here](http://www.reddit.com/dev/api).
##Dependencies
[JSON-simple](http://code.google.com/p/json-simple/)
##Examples

Upvote a submission and comment on it

    import org.omer.api.submissions.Submission;
    import org.omer.api.user.User;

    public final class Example {
	    public static void main(String[] args) throws Exception {
		    User user = new User("username", "password");
		    user.connect();

		    Submission submission = new Submission(user, "tki9d");
		    submission.upVote();
		    submission.comment("This is a cool submission.");
	    }
    }

Upvote every submission on the frontpage of a subreddit

    package org.jreddit.api;

    import org.jreddit.api.submissions.Submission;
    import org.jreddit.api.submissions.Submissions;
    import org.jreddit.api.user.User;

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

Print some information about this user and a certain submission

	package org.jreddit.api;
	
	import org.jreddit.api.submissions.Submission;
	import org.jreddit.api.submissions.Submissions;
	import org.jreddit.api.user.User;
	
	public final class Test {
		public static void main(String[] args) throws Exception {
			User user = new User("username", "password");
			user.connect();
	
			System.out.println(user.commentKarma());
			System.out.println(user.linkKarma());
			System.out.println(user.hasMail());
			System.out.println(user.isGold());
			System.out.println(user.getModhash());
	
			Submission submission = Submissions.getSubmissions("programming",
					Submissions.HOT, Submissions.FRONTPAGE, user).get(0);
	
			System.out.println(submission.commentCount());
			System.out.println(submission.downVotes());
			System.out.println(submission.upVotes());
			System.out.println(submission.getAuthor());
			System.out.println(submission.getScore());
		}
	}
