package com.github.jreddit.action;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.github.jreddit.entity.User;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

public class MarkActionsTest {

    public static final String COOKIE = "cookie";
    public static final String FN_SUBM = "t1_SubmID"; // Full name of the submission
    public static final String MOD_HASH = "modHash";

    private MarkActions subject;
    private User user;
    private RestClient restClient;
    private UtilResponse normalResponse;
    
    @Before
    public void setup() {
        user = mock(User.class);
        restClient = mock(RestClient.class);
        subject = new MarkActions(restClient, user);

        Mockito.when(user.getCookie()).thenReturn(COOKIE);
        Mockito.when(user.getModhash()).thenReturn(MOD_HASH);
        normalResponse = new UtilResponse(null, new JSONObject(), 200);
        
    }

    @Test
    public void testMarkNSFWSuccess() {
        when(restClient.post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, COOKIE)).thenReturn(normalResponse);
        subject.markNSFW(FN_SUBM);
        verify(restClient).post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SUBMISSION_MARK_AS_NSFW, COOKIE);
    }

    @Test
    public void testUnmarkNSFWSuccess() {
        when(restClient.post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, COOKIE)).thenReturn(normalResponse);
    	subject.unmarkNSFW(FN_SUBM);
        verify(restClient).post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SUBMISSION_UNMARK_AS_NSFW, COOKIE);
    }

    @Test
    public void testSaveSuccess() {
        when(restClient.post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SAVE, COOKIE)).thenReturn(normalResponse);
    	subject.save(FN_SUBM);
        verify(restClient).post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.SAVE, COOKIE);
    }

    @Test
    public void testUnsaveSuccess() {
        when(restClient.post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.UNSAVE, COOKIE)).thenReturn(normalResponse);
    	subject.unsave(FN_SUBM);
        verify(restClient).post("id=" + FN_SUBM + "&uh=" + MOD_HASH, ApiEndpointUtils.UNSAVE, COOKIE);
    }
    
    // TODO: Add test cases that handle when stuff fails
    
}
