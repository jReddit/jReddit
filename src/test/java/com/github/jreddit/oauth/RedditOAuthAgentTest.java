package com.github.jreddit.oauth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.commons.validator.routines.UrlValidator;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.app.RedditInstalledApp;
import com.github.jreddit.oauth.app.RedditScriptApp;
import com.github.jreddit.oauth.app.RedditWebApp;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.oauth.param.RedditDuration;
import com.github.jreddit.oauth.param.RedditScope;
import com.github.jreddit.oauth.param.RedditScopeBuilder;

@RunWith(MockitoJUnitRunner.class)
public class RedditOAuthAgentTest {

    @Mock private OAuthClient mockOAuthClient;
    @Mock private OAuthJSONAccessTokenResponse jsonToken;
    @Mock private OAuthJSONAccessTokenResponse jsonTokenNonRefreshable;
    @Mock private RedditToken mockRedditToken;
    @Mock private RedditToken mockRedditTokenRefreshable;
    @Mock private OAuthResourceResponse mockSuccessOAuthResourceResponse;
    @Mock private OAuthResourceResponse mockFailureOAuthResourceResponse;
    
    private final String userAgent = "Test User Agent / 2.0";
    private final String clientID = "35_398598298592";
    private final String clientSecret = "dsuf9a8f92";
    private final String redirectURI = "http://www.example.com/";


    private String accessToken = "djsaf98s9fasjofjkasjdf9";
    private String refreshToken = "89sd89fafjdajiofafsfasf";
    private String tokenType = "bearer";
    private long expiresIn = 1;
    private String scope = "edit,flair";
    
    private String code = "dioua97f98f9a893oa98fa098093805aklajlaa_";
    
    private RedditOAuthAgent subject;
    
    @Before
    public void setup() {
        
        RedditScriptApp app = new RedditScriptApp(clientID, clientSecret, redirectURI);
        subject = new RedditOAuthAgent(userAgent, app, mockOAuthClient);
        
        when(jsonToken.getAccessToken()).thenReturn(accessToken);
        when(jsonToken.getRefreshToken()).thenReturn(refreshToken);
        when(jsonToken.getParam(RedditToken.PARAM_TOKEN_TYPE)).thenReturn(tokenType);
        when(jsonToken.getExpiresIn()).thenReturn(expiresIn);
        when(jsonToken.getScope()).thenReturn(scope);
        
        when(jsonTokenNonRefreshable.getAccessToken()).thenReturn(accessToken);
        when(jsonTokenNonRefreshable.getRefreshToken()).thenReturn(null);
        when(jsonTokenNonRefreshable.getParam(RedditToken.PARAM_TOKEN_TYPE)).thenReturn(tokenType);
        when(jsonTokenNonRefreshable.getExpiresIn()).thenReturn(expiresIn);
        when(jsonTokenNonRefreshable.getScope()).thenReturn(scope);
        
        when(mockRedditToken.isRefreshable()).thenReturn(false);
        when(mockRedditTokenRefreshable.isRefreshable()).thenReturn(true);
        
        when(mockSuccessOAuthResourceResponse.getResponseCode()).thenReturn(RedditOAuthAgent.RESPONSE_CODE_204);
        when(mockFailureOAuthResourceResponse.getResponseCode()).thenReturn(RedditOAuthAgent.RESPONSE_CODE_401);
        
    }
    
    @Test
    public void testDefaultConstructor() {
        RedditApp app = new RedditWebApp(clientID, clientSecret, redirectURI);
        new RedditOAuthAgent(userAgent, app);
        app = new RedditScriptApp(clientID, clientSecret, redirectURI);
        new RedditOAuthAgent(userAgent, app);
        app = new RedditInstalledApp(clientID, redirectURI);
        new RedditOAuthAgent(userAgent, app);
    }
    
    @Test
    public void testGenerateCodeFlowURI() {
        RedditScopeBuilder builder = new RedditScopeBuilder();
        builder.addScope(RedditScope.EDIT);
        String url = subject.generateCodeFlowURI(builder, RedditDuration.PERMANENT);
        UrlValidator urlValidator = new UrlValidator();
        assertTrue(urlValidator.isValid(url));
    }
    
    @Test
    public void testGenerateImplicitFlowURI() {
        RedditScopeBuilder builder = new RedditScopeBuilder();
        builder.addScope(RedditScope.FLAIR);
        String url = subject.generateImplicitFlowURI(builder);
        UrlValidator urlValidator = new UrlValidator();
        assertTrue(urlValidator.isValid(url));
    }
    
    @Test
    public void testTokenFromInfo() {
        RedditToken token = subject.tokenFromInfo(accessToken, tokenType, expiresIn, scope);
        assertEquals(accessToken, token.getAccessToken());
        assertEquals(tokenType, token.getTokenType());
        assertEquals(expiresIn, token.getExpirationSpan());
        assertTrue(token.hasScope(RedditScope.EDIT));
        assertTrue(token.hasScope(RedditScope.FLAIR));
        assertFalse(token.hasScope(RedditScope.PRIVATEMESSAGE));
    }

    @Test
    public void testToken() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
        
        // Captor for the request that is executed
        ArgumentCaptor<OAuthClientRequest> clientCaptor = ArgumentCaptor.forClass(OAuthClientRequest.class);
        
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenReturn(jsonToken);
        
        // Run subject
        RedditToken token = subject.token(code);
        
        // Verify and capture
        verify(mockOAuthClient).accessToken(clientCaptor.capture());
        
        OAuthClientRequest request = clientCaptor.getValue();
        
        assertNotNull(request.getHeader("Authorization")); // This is Base64 encoded
        assertEquals(request.getHeader("User-Agent"), userAgent);
        
        assertEquals(accessToken, token.getAccessToken());
        assertEquals(refreshToken, token.getRefreshToken());
        assertEquals(tokenType, token.getTokenType());
        assertEquals(expiresIn, token.getExpirationSpan());
        assertTrue(token.hasScope(RedditScope.EDIT));
        assertTrue(token.hasScope(RedditScope.FLAIR));
        assertFalse(token.hasScope(RedditScope.PRIVATEMESSAGE));

    }
    
    @Test(expected=RedditOAuthException.class)
    public void testTokenOAuthSystemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(new OAuthSystemException());
        subject.token(code);
    }
    
    @Test(expected=RedditOAuthException.class)
    public void testTokenOAuthProblemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(OAuthProblemException.error("Error"));
        subject.token(code);
    }
    
    @Test
    public void testRefreshTokenFailure() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
        assertFalse(subject.refreshToken(mockRedditToken));
    }
    
    @Test
    public void testRefreshTokenSuccess() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
        assertTrue(subject.refreshToken(mockRedditTokenRefreshable));
        verify(mockOAuthClient).accessToken(any(OAuthClientRequest.class));
        verify(mockRedditTokenRefreshable).refresh(null);
    }
    
    @Test(expected=RedditOAuthException.class)
    public void testRefreshTokenOAuthSystemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(new OAuthSystemException());
        subject.refreshToken(mockRedditTokenRefreshable);
    }
    
    @Test(expected=RedditOAuthException.class)
    public void testRefreshTokenOAuthProblemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(OAuthProblemException.error("Error"));
        subject.refreshToken(mockRedditTokenRefreshable);
    }
    
    @Test
    public void testTokenAppOnlyConfidential() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
        
        // Captor for the request that is executed
        ArgumentCaptor<OAuthClientRequest> clientCaptor = ArgumentCaptor.forClass(OAuthClientRequest.class);
        
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenReturn(jsonTokenNonRefreshable);
        
        // Run subject
        RedditToken token = subject.tokenAppOnly(true);
        
        // Verify and capture
        verify(mockOAuthClient).accessToken(clientCaptor.capture());
        
        OAuthClientRequest request = clientCaptor.getValue();
        
        assertNotNull(request.getHeader("Authorization")); // This is Base64 encoded
        assertEquals(request.getHeader("User-Agent"), userAgent);
        
        assertEquals(accessToken, token.getAccessToken());
        assertNull(token.getRefreshToken());
        assertEquals(tokenType, token.getTokenType());
        assertEquals(expiresIn, token.getExpirationSpan());
        assertTrue(token.hasScope(RedditScope.EDIT));
        assertTrue(token.hasScope(RedditScope.FLAIR));
        assertFalse(token.hasScope(RedditScope.PRIVATEMESSAGE));

    }
    
    @Test
    public void testTokenAppOnly() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
        
        // Captor for the request that is executed
        ArgumentCaptor<OAuthClientRequest> clientCaptor = ArgumentCaptor.forClass(OAuthClientRequest.class);
        
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenReturn(jsonTokenNonRefreshable);
        
        // Run subject
        RedditToken token = subject.tokenAppOnly(false);
        
        // Verify and capture
        verify(mockOAuthClient).accessToken(clientCaptor.capture());
        
        OAuthClientRequest request = clientCaptor.getValue();
        
        assertNotNull(request.getHeader("Authorization")); // This is Base64 encoded
        assertEquals(request.getHeader("User-Agent"), userAgent);
        
        assertEquals(accessToken, token.getAccessToken());
        assertNull(token.getRefreshToken());
        assertEquals(tokenType, token.getTokenType());
        assertEquals(expiresIn, token.getExpirationSpan());
        assertTrue(token.hasScope(RedditScope.EDIT));
        assertTrue(token.hasScope(RedditScope.FLAIR));
        assertFalse(token.hasScope(RedditScope.PRIVATEMESSAGE));

    }
    
    @Test(expected=RedditOAuthException.class)
    public void testTokenAppOnlyOAuthSystemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(new OAuthSystemException());
        subject.tokenAppOnly(false);
    }
    
    @Test(expected=RedditOAuthException.class)
    public void testTokenAppOnlyOAuthProblemException() throws OAuthSystemException, OAuthProblemException, RedditOAuthException {
        when(mockOAuthClient.accessToken(any(OAuthClientRequest.class))).thenThrow(OAuthProblemException.error("Error"));
        subject.tokenAppOnly(false);
    }
    
    @Test
    public void testSucessRevoke() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
    	when(mockOAuthClient.resource(any(OAuthClientRequest.class), any(String.class), Matchers.eq(OAuthResourceResponse.class))).thenReturn(mockSuccessOAuthResourceResponse);
    	assertTrue(subject.revoke(mockRedditToken, true));
    	assertTrue(subject.revoke(mockRedditTokenRefreshable, false));
    }
    
    @Test
    public void testFailedRevoke() throws RedditOAuthException, OAuthSystemException, OAuthProblemException {
    	when(mockOAuthClient.resource(any(OAuthClientRequest.class), any(String.class), Matchers.eq(OAuthResourceResponse.class))).thenReturn(mockFailureOAuthResourceResponse);
    	assertFalse(subject.revoke(mockRedditToken, true));
    	assertFalse(subject.revoke(mockRedditTokenRefreshable, false));
    }
    
}
