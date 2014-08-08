package com.github.jreddit.retrieval;

import static com.github.jreddit.testsupport.JsonHelpers.createMediaEmbedObject;
import static com.github.jreddit.testsupport.JsonHelpers.createMediaObject;
import static com.github.jreddit.testsupport.JsonHelpers.redditListing;
import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.jreddit.entity.Comment;
import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.retrieval.params.CommentSort;
import com.github.jreddit.retrieval.params.TimeSpan;
import com.github.jreddit.retrieval.params.UserOverviewSort;
import com.github.jreddit.testsupport.JsonHelpers;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

/**
 * Class for testing comment retrieval methods.
 *
 * @author Simon Kassing
 */
public class CommentsRetrievalTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_NAME = "all";
    public static final String USERNAME = "TestUser";
    private Comments subject;
    private RestClient restClient;
    private User user;
    private UtilResponse normalFlatResponse;
    private UtilResponse normalLayeredResponse;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    
    /**
     * Setup main mocks and stubs.
     */
    @Before
    public void setup() {
        user = mock(User.class);
        when(user.getCookie()).thenReturn(COOKIE);
        restClient = mock(RestClient.class);
        subject = new Comments(restClient);
        normalFlatResponse = new UtilResponse(null, commentFlatListings(), 200);
        normalLayeredResponse = new UtilResponse(null, commentLayeredListings(), 200);
    }

    /**
     * Test comment retrieval for a submission.
     */
    @Test
    public void testCommentsFromSubmission() {
    	
        // Define variables
        String articleLink = "articleLink";
        String commentId = "commentId";
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
        
        // Stub response
        when(restClient.get(String.format(ApiEndpointUtils.SUBMISSION_COMMENTS, articleLink, parameters), null)).thenReturn(normalLayeredResponse);

        // Execute and verify response
        List<Comment> comments = subject.ofSubmission(articleLink, commentId, parentsShown, depth, limit, commentSort);
        verifyNormalLayeredResult(comments);
        
    }
    
    
    /**
     * Test comments from submission when illegal submission identifier argument.
     */
    @Test
    public void testSubmissionCommentsIllegalArgumentIdentifier() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofSubmission((String) null, "commentId", 0, 8, 20, CommentSort.CONFIDENCE);
    }
    
    /**
     * Test comments from submission when illegal submission argument.
     */
    @Test
    public void testSubmissionCommentsIllegalArgumentSubmission() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofSubmission((Submission) null, "commentId", 0, 8, 20, CommentSort.CONFIDENCE);
    }
    
    /**
     * Test comments from submission when illegal depth argument.
     */
    @Test
    public void testSubmissionCommentsIllegalArgumentDepth() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofSubmission("submId", "commentId", 0, -8, 20, CommentSort.CONFIDENCE);
    }
    
    /**
     * Test comment retrieval for a user.
     */
    @Test
    public void testCommentsFromUser() {

        // Stub response
        String url = String.format(ApiEndpointUtils.USER_COMMENTS, USERNAME, "&sort=" + UserOverviewSort.HOT.value() + "&time=" + TimeSpan.ALL.value());
        when(restClient.get(url, null)).thenReturn(normalFlatResponse);

        // Execute and verify response
        List<Comment> comments = subject.ofUser(USERNAME, UserOverviewSort.HOT, TimeSpan.ALL, -1, -1, null, null, false);
        verifyNormalFlatResult(comments);
        
    }
    
    /**
     * Test comments from user when illegal username argument.
     */
    @Test
    public void testUserCommentsIllegalArgumentUsername() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofUser((String) null, UserOverviewSort.HOT, TimeSpan.ALL, -1, -1, null, null, false);
    }
    
    
    /**
     * Test comments from user when illegal user argument.
     */
    @Test
    public void testUserCommentsIllegalArgumentUser() {
    	exception.expect(IllegalArgumentException.class);
    	subject.ofUser((User) null, UserOverviewSort.HOT, TimeSpan.ALL, -1, -1, null, null, false);
    }
    
    /**
     * Test comment retrieval for a user when fails when undefined username.
     */
    @Test
    public void testCommentsFromUserFailUndefUsername() {

        // Stub response
        String url = String.format(ApiEndpointUtils.USER_COMMENTS, USERNAME, "&sort=" + UserOverviewSort.HOT.value() + "&time=" + TimeSpan.ALL.value());
        when(restClient.get(url, null)).thenReturn(normalFlatResponse);

        // Execute and verify response
        exception.expect(IllegalArgumentException.class);
        subject.ofUser((User) null, UserOverviewSort.HOT, TimeSpan.ALL, -1, -1, null, null, false);
        
    }
    
    /**
     * Verify that the normal flat result is correct.
     * @param result The list of comments returned.
     */
    private void verifyNormalFlatResult(List<Comment> result) {
        assertEquals(2, result.size());
        assertEquals(result.get(0).getFullName(), "t1_redditObjId");
        assertEquals(result.get(1).getFullName(), "t1_anotherRedditObjId");
    }
    
    /**
     * Generate a flat comment listing.
     * 
     * @return Flat comment listing
     */
    private JSONObject commentFlatListings() {
        JSONObject comment1 = JsonHelpers.createComment("t1_redditObjId", "redditObjId", "t3_objIdThread", "author_1", "body_1", null);
        JSONObject comment2 = JsonHelpers.createComment("t1_anotherRedditObjId", "anotherRedditObjId", "t3_objIdThread", "author_2", "body_2", null);
        return redditListing(comment1, comment2);
    }
    
    /**
     * Verify that the normal layered result is correct.
     * @param result The list of comments returned.
     */
    private void verifyNormalLayeredResult(List<Comment> result) {
    	
    	// First layer (comment1, comment2, comment3)
        assertEquals(3, result.size());
        assertEquals(result.get(0).getFullName(), "t1_redditObjId1");
        assertEquals(result.get(1).getFullName(), "t1_redditObjId2");
        assertEquals(result.get(2).getFullName(), "t1_redditObjId3");
        
        // Comment1
        Comment comment1 = result.get(0);
        assertEquals(3, comment1.getReplies().size());
        assertEquals(comment1.getReplies().get(0).getFullName(), "t1_redditObjId11");
        assertEquals(comment1.getReplies().get(1).getFullName(), "t1_redditObjId12");
        assertEquals(comment1.getReplies().get(2).getFullName(), "t1_redditObjId13");
        
        // Comment11
        Comment comment11 = comment1.getReplies().get(0);
        assertEquals(2, comment11.getReplies().size());
        assertEquals(comment11.getReplies().get(0).getFullName(), "t1_redditObjId111");
        assertEquals(comment11.getReplies().get(1).getFullName(), "t1_redditObjId112");
        
        // Comment2
        Comment comment2 = result.get(1);
        assertEquals(2, comment2.getReplies().size());
        assertEquals(comment2.getReplies().get(0).getFullName(), "t1_redditObjId21");
        assertEquals(comment2.getReplies().get(1).getFullName(), "t1_redditObjId22");
        
    }
    
    /**
     * Generate a layered comment listing.
     * 
     * Structure:
     * 	comment1
     * 		comment11
     * 			comment111
     * 			comment112
     * 		comment12
     * 		comment13
     * 	comment2
     * 		comment21
     * 		comment22
     * 	comment3
     * 
     * @return Layered comment listing
     */
    private JSONArray commentLayeredListings() {
    	
    	// Submission information
        JSONObject media = createMediaObject();
        JSONObject mediaEmbed = createMediaEmbedObject();
    	JSONObject parent_submission = JsonHelpers.createSubmission("t3_objIdThread", false, media, mediaEmbed);
    	
    	// Comment1 structure
        JSONObject comment111 = JsonHelpers.createComment("t1_redditObjId111", "redditObjId111", "t3_objIdThread", "author_111", "body_111", null);
        JSONObject comment112 = JsonHelpers.createComment("t1_redditObjId112", "redditObjId112", "t3_objIdThread", "author_112", "body_112", null);
        JSONObject replies_comment11 = redditListing(comment111, comment112);
        JSONObject comment11 = JsonHelpers.createComment("t1_redditObjId11", "redditObjId11", "t3_objIdThread", "author_11", "body_11", replies_comment11);
        JSONObject comment12 = JsonHelpers.createComment("t1_redditObjId12", "redditObjId12", "t3_objIdThread", "author_12", "body_12", null);
        JSONObject comment13 = JsonHelpers.createComment("t1_redditObjId13", "redditObjId13", "t3_objIdThread", "author_13", "body_13", null);
        JSONObject replies_comment1 = redditListing(comment11, comment12, comment13);
        JSONObject comment1 = JsonHelpers.createComment("t1_redditObjId1", "redditObjId1", "t3_objIdThread", "author_1", "body_1", replies_comment1);
        
    	// Comment2 structure
        JSONObject comment21 = JsonHelpers.createComment("t1_redditObjId21", "redditObjId21", "t3_objIdThread", "author_21", "body_21", null);
        JSONObject comment22 = JsonHelpers.createComment("t1_redditObjId22", "redditObjId22", "t3_objIdThread", "author_22", "body_22", null);
        JSONObject replies_comment2 = redditListing(comment21, comment22);
        JSONObject comment2 = JsonHelpers.createComment("t1_redditObjId2", "redditObjId2", "t3_objIdThread", "author_2", "body_2", replies_comment2);
        
    	// Comment3 structure
        JSONObject comment3 = JsonHelpers.createComment("t1_redditObjId3", "redditObjId3", "t3_objIdThread", "author_3", "body_3", null);
        
        return JsonHelpers.jsonArrayOf(redditListing(parent_submission), redditListing(comment1, comment2, comment3));
        
    }
    
}
