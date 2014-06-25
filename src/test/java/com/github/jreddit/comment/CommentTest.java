package com.github.jreddit.comment;

import com.github.jreddit.testsupport.JsonHelpers;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static com.github.jreddit.testsupport.JsonHelpers.createMockComment;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Class for testing Message-related methods
 *
 * @author Raul Rene Lepsa
 */
public class CommentTest {

    private RestClient restClient;
    private Comments comments;

    @Before
    public void setUp() {
        restClient = mock(RestClient.class);
        comments = new Comments(restClient);
    }

    @Test
    public void commentsFromArticle() {
        // Define variables
        String subreddit = "subreddit";
        String articleLink = "articleLink";
        String commentId = "comment";
        int parentsShown = 0;
        int depth = 8;
        int limit = 20;
        CommentSort commentSort = CommentSort.CONFIDENCE;

        // Construct extra parameters
        String parameters = "";
        parameters += "&comment=" + commentId;
        parameters += "&context=" + parentsShown;
        parameters += "&depth=" + depth;
        parameters += "&limit=" + limit;
        parameters += "&sort=" + commentSort.value();
        
        Response response = new UtilResponse("", commentsList(), 200);
        when(restClient.get(String.format(ApiEndpointUtils.SUBMISSION_COMMENTS, articleLink, parameters), null)).thenReturn(response);

        int size = comments.ofSubmission(null, articleLink, commentId, parentsShown, depth, limit, commentSort).size();
        assertEquals(size, 1);
    }

    private JSONArray commentsList() {
        // Create a mock comment
        JSONObject comment = createMockComment("id", "author", "full_name_of_comment", "parent_id");

        // Create a listing with the comments
        JSONObject list = JsonHelpers.redditListing(comment);

        // Create array from the listing
        return JsonHelpers.jsonArrayOf(list, list);
    }
}
