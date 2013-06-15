<<<<<<< HEAD
Note: Not the latest README. -Karan

#jReddit
##What is jReddit?
jReddit is a wrapper for the Reddit API written in Java. It is a work in progress.
##What can it do?
So far, jReddit can login with a user, retrieve user information, submit new links, and vote/comment on submissions, among other things.
##What's next for jReddit?
I plan to implement every feature documented [here](http://www.reddit.com/dev/api).
##What licence is jReddit released under?
You are permitted to use, copy, modify, and distribute the Software and its documentation, with or without modification, for any purpose, provided that the following conditions are met:

A copy of this license agreement must be included with the distribution.
Redistributions in binary form must reproduce the above copyright notice in the documentation and/or other materials provided with the distribution.
Commercial products derived from the Software must include an acknowledgment of the author(s) of jReddit in their documentation and/or other materials provided with the distribution.
Products derived from the Software may not be called "jReddit", nor may "jReddit" appear in their name, without prior written permission from the author(s).

See LICENCE.txt for more info.

##Dependencies
=======
# jReddit (forked from hormigas/jReddit)

## What is jReddit?

    jReddit is a wrapper for the Reddit API written in Java. 
    It is a work in progress.

## What can it do?

    So far, jReddit can login with a user, retrieve user information, 
    submit new links, and vote/comment on submissions, among other things.

## What's next for jReddit?

    The original project (hormigas/jReddit) wants to implement every feature 
    documented [here](http://www.reddit.com/dev/api).
    
    This fork is to improve on the original object model
    and add functionality.

## Dependencies

>>>>>>> 655009ae842bf8c3ca033c6d25edb14f022bcc15
[JSON-simple](http://code.google.com/p/json-simple/)

## Building (everything)
    
    $ ant all

    See ant targets in build.xml for additional targets.

    E.g.
    $ ant examples

## Building and Running Examples

    Compile
    $ ant examples

    Running usage:
    $ examples/runExample.sh <classname> <user> <pass> <subreddit> 

    E.g.
    $ examples/runExample.sh TopCommentLister joesmith secret123 pics

## Examples

    import com.omrlnr.jreddit.submissions.Submission;
    import com.omrlnr.jreddit.submissions.Submissions;
    import com.omrlnr.jreddit.user.User;

    import com.omrlnr.jreddit.utils.Utils;

    import java.util.List;

    /**
     *
     * A simple example that lists submissions in a subreddit
     * 
     */
    public class SubmissionLister {

        public static void main(String[] args) throws Exception {

            String username     = args[0];
            String password     = args[1];
            String subreddit    = args[2];

            Utils.setUserAgent("Sample Java API user agent v0.01");

            User user = new User(username, password);
            user.connect();
        
            List<Submission> submissions = Submissions.getSubmissions(
                                                subreddit,
                                                Submissions.NEW,
                                                Submissions.FRONTPAGE,
                                                user);

            for(Submission submission: submissions) {
                System.out.println(submission);
            }
        }

    }

<<<<<<< HEAD
Print some information about this user and a certain submission
	
	import com.omrlnr.jreddit.submissions.Submission;
	import com.omrlnr.jreddit.submissions.Submissions;
	import com.omrlnr.jreddit.user.User;
	
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

Submit a link and self post

	import com.omrlnr.jreddit.user.User;
	
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
	
List the subreddits that make up the default front page of reddit

	import com.omrlnr.jreddit.submissions.Submission;
	import com.omrlnr.jreddit.user.User;
	import com.omrlnr.jreddit.subreddit.Subreddit;
	import com.omrlnr.jreddit.subreddit.Subreddits;
	
	/**
	 * @author Benjamin Jakobus
	 */
	public final class Test {
		public static void main(String[] args) throws Exception {
			User user = new User("username", "password");
			user.connect();
		
			List<Subreddit> subreddits = Subreddits.list(user, "popular");
			// Alternativly use: Subreddits.listDefault(user);
			
			for (Subreddit sr : subreddits) {
				System.out.println(sr.getUrl() + " " + sr.getTitle());
			}
		
		}
	}

List all comments made by user called USERNAME_OF_OTHER_USER

	import com.omrlnr.jreddit.user.Comment;
	import com.omrlnr.jreddit.user.User;
	
	/**
	 * @author Benjamin Jakobus
	 */
	public final class Test {
		public static void main(String[] args) throws Exception {
			User user = new User("username", "password");
        		user.connect();

        		List<Comment> comments = User.comments("USERNAME_OF_OTHER_USER");
		
			for (Comment c : comments) {
				System.out.println(c.getComment());
			}
		}
	}
	
List all submissions made by user called USERNAME_OF_OTHER_USER

	import com.omrlnr.jreddit.submissions.Submission;
	import com.omrlnr.jreddit.user.User;
	
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
=======
>>>>>>> 655009ae842bf8c3ca033c6d25edb14f022bcc15

