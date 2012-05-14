#jReddit
##What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. It is a work in progress.
##What can it do?
So far, jReddit can login with a user, retrieve user information, and vote/comment on submissions.
##What's next for jReddit?
I plan to implement every feature documented [here](http://www.reddit.com/dev/api).
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