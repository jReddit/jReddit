package com.github.jreddit.submissions;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.github.jreddit.testsupport.JsonHelpers.createSubmission;
import static com.github.jreddit.utils.ApiEndpointUtils.*;
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
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false, false, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.markNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH, SUBMISSION_MARK_AS_NSFW, COOKIE);
    }

    @Test
    public void unmarkNSFW() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, true, false, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unmarkNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH, SUBMISSION_UNMARK_AS_NSFW, COOKIE);
    }

    @Test
    public void save() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false, false, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.save();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH,
                SUBMISSION_SAVE, COOKIE);
    }

    @Test
    public void unsave() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false, true, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unsave();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH,
                SUBMISSION_UNSAVE, COOKIE);
    }

    @Test
    public void hide() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false, false, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.hide();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH,
                SUBMISSION_HIDE, COOKIE);
    }

    @Test
    public void unhide() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_ID, false, false, true));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unhide();

        verify(restClient).post("id=" + REDDIT_OBJ_ID + "&uh=" + MOD_HASH,
                SUBMISSION_UNHIDE, COOKIE);
    }
}