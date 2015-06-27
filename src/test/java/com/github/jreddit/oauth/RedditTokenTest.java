package com.github.jreddit.oauth;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.jreddit.oauth.param.RedditScope;

@RunWith(MockitoJUnitRunner.class)
public class RedditTokenTest {

    @Mock private OAuthJSONAccessTokenResponse jsonToken;
    @Mock private OAuthJSONAccessTokenResponse refreshJsonToken;
    
    private String accessToken = "djsaf98s9fasjofjkasjdf9";
    private String refreshToken = "89sd89fafjdajiofafsfasf";
    private String tokenType = "bearer";
    private long expiresIn = 1;
    private String scope = "edit,flair";
    
    private String accessToken2 = "9sd89s8fahhsf9898saf";
    private String tokenType2 = "bearer";
    private long expiresIn2 = 3600;
    private String scope2 = "edit";
    
    @Before
    public void setup() {
        
        when(jsonToken.getAccessToken()).thenReturn(accessToken);
        when(jsonToken.getRefreshToken()).thenReturn(refreshToken);
        when(jsonToken.getParam(RedditToken.PARAM_TOKEN_TYPE)).thenReturn(tokenType);
        when(jsonToken.getExpiresIn()).thenReturn(expiresIn);
        when(jsonToken.getScope()).thenReturn(scope);
        when(refreshJsonToken.getAccessToken()).thenReturn(accessToken2);
        when(refreshJsonToken.getParam(RedditToken.PARAM_TOKEN_TYPE)).thenReturn(tokenType2);
        when(refreshJsonToken.getExpiresIn()).thenReturn(expiresIn2);
        when(refreshJsonToken.getScope()).thenReturn(scope2);
        
    }
    
    @Test
    public void testGetters() {
        
        RedditToken subject = new RedditToken(jsonToken);
        RedditToken subjectUserProvided = new RedditToken(accessToken, tokenType, expiresIn, scope);
        assertEquals(accessToken, subject.getAccessToken());
        assertEquals(refreshToken, subject.getRefreshToken());
        assertEquals(tokenType, subject.getTokenType());
        assertEquals(expiresIn, subject.getExpirationSpan());
        assertTrue(subject.hasScope(RedditScope.EDIT));
        assertTrue(subject.hasScope(RedditScope.FLAIR));
        assertFalse(subject.hasScope(RedditScope.PRIVATEMESSAGE));
        assertTrue(subject.isRefreshable());
        assertFalse(subjectUserProvided.isRefreshable());
        
    }
    
    @Test
    public void testRefresh() {
        
        RedditToken subject = new RedditToken(jsonToken);
        assertEquals(accessToken, subject.getAccessToken());
        assertEquals(refreshToken, subject.getRefreshToken());
        assertEquals(tokenType, subject.getTokenType());
        assertEquals(expiresIn, subject.getExpirationSpan());
        assertTrue(subject.hasScope(RedditScope.EDIT));
        assertTrue(subject.hasScope(RedditScope.FLAIR));
        
        subject.refresh(refreshJsonToken);
        
        assertEquals(accessToken2, subject.getAccessToken());
        assertEquals(refreshToken, subject.getRefreshToken());
        assertEquals(tokenType2, subject.getTokenType());
        assertEquals(expiresIn2, subject.getExpirationSpan());
        assertTrue(subject.hasScope(RedditScope.EDIT));
        assertFalse(subject.hasScope(RedditScope.FLAIR));
        
    }
    
    @Test
    public void testTimeSensitiveExpiration() {
        
        RedditToken subject = new RedditToken(jsonToken);
        RedditToken subjectUserProvided = new RedditToken(accessToken, tokenType, expiresIn2, scope);
        
        assertFalse(subjectUserProvided.willExpireIn(expiresIn2 - 60));
        assertTrue(subjectUserProvided.willExpireIn(expiresIn2 + 60));
        assertFalse(subjectUserProvided.isExpired());
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        assertTrue(subject.isExpired());
        assertTrue(subject.getExpiration() < (System.currentTimeMillis() / 1000));
        
    }
    
}
