package com.github.jreddit.action;

import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.mockito.Mockito;

import com.github.jreddit.entity.Submission;
import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.RestClient;

public class UserActionsTest {

    public static final String COOKIE = "cookie";
    public static final String REDDIT_OBJ_FULLNAME = "Type_Identifier";
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

    /*@Test
    public void markNSFW() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_FULLNAME, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.markNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_FULLNAME + "&uh=" + MOD_HASH, SUBMISSION_MARK_AS_NSFW, COOKIE);
    }

    @Test
    public void unmarkNSFW() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_FULLNAME, true));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unmarkNSFW();

        verify(restClient).post("id=" + REDDIT_OBJ_FULLNAME + "&uh=" + MOD_HASH, SUBMISSION_UNMARK_AS_NSFW, COOKIE);
    }

    @Test
    public void save() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_FULLNAME, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.save();

        verify(restClient).post("id=" + REDDIT_OBJ_FULLNAME + "&uh=" + MOD_HASH,
                SUBMISSION_SAVE, COOKIE);
    }

    @Test
    public void unsave() throws IOException, ParseException {
        underTest = new Submission(createSubmission(REDDIT_OBJ_FULLNAME, false));
        underTest.setUser(user);
        underTest.setRestClient(restClient);
        underTest.unsave();

        verify(restClient).post("id=" + REDDIT_OBJ_FULLNAME + "&uh=" + MOD_HASH,
                SUBMISSION_UNSAVE, COOKIE);
    }*/
    
}
