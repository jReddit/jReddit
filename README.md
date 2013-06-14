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


