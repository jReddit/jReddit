package com.github.jreddit.submissions;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.github.jreddit.testsupport.JsonHelpers.createSubmission;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW;
import static com.github.jreddit.utils.ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * Class for testing Submission-related methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class SubmissionTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_OBJ_ID = "redditObjName";
    public static final String MOD_HASH = "modHash";
    private Submission underTest;
    private User user;
    private RestClient restClient;

    @Before
    public void setUp() {
        user = mock(User.class);
        restClient = mock(RestClient.class);

        Mockito.when(user.getCookie()).thenReturn(COOKIE);
        Mockito.when(user.getModhash()).thenReturn(MOD_HASH);
    }

    @Test
    public void markNSFW() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.markNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH, SUBMISSION_MARK_AS_NSFW, COOKIE);
    }

    @Test
    public void unmarkNSFW() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, true));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unmarkNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH, SUBMISSION_UNMARK_AS_NSFW, COOKIE);
    }
}