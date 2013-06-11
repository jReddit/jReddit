import com.omrlnr.jreddit.submissions.Comment;
import com.omrlnr.jreddit.submissions.Comments;
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
public class CommentLister {

    public static void main(String[] args) throws Exception {

        String username     = args[0];
        String password     = args[1];
        String article      = args[2];

        User user = new User(username, password);
        user.connect();
        
        List<Comment> comments = Comments.getComments(
                                                article,
                                                user );
        for(Comment comment: comments) {
            System.out.println(comment);
        }

    }

}
