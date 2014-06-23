import com.github.jreddit.submissions.Submission;
import com.github.jreddit.submissions.Submissions;
import com.github.jreddit.utils.PaginationResult;
import com.github.jreddit.utils.restclient.PoliteRestClient;
import com.github.jreddit.utils.restclient.RestClient;

public final class GetPaginatedTopics
{
    public static final int NUMBER_OF_PAGES = 3;
    public static final String SUBREDDIT = "science";

    public static void main(String[] args) throws Exception
    {
        RestClient restClient = PoliteRestClient.get();
        restClient.setUserAgent("Post-Reader-Bot");
        Submissions fetcher = new Submissions( restClient );

        String after = null;
        for (int page = 1; page <= NUMBER_OF_PAGES; page++)
        {
            PaginationResult<Submission> result = fetcher.getSubmissions(SUBREDDIT, after);
            after = result.getAfter();
            for ( Submission submission : result.getResults() )
            {
                System.out.println( submission.getTitle() + ", " + submission.getAuthor() );
            }
        }
    }
}