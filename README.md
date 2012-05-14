#jReddit
##What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. It is a work in progress.
##What can it do?
So far, jReddit can login with a user, retrieve user information, and vote on submissions.
##What's next for jReddit?
I plan to implement every feature documented [here](http://www.reddit.com/dev/api).
##Examples

Upvote a submission and comment on it

    import org.omer.api.links.Link;
    import org.omer.api.user.User;

    public final class Example {
	    public static void main(String[] args) throws Exception {
		    User user = new User("username", "password");
		    user.connect();

		    Link link = new Link(user, "tki9d");
		    link.upVote();
		    link.comment("This is a cool submission.");
	    }
    }