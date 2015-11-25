package com.github.jreddit.oauth;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.client.response.OAuthClientResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.github.jreddit.oauth.RedditToken;
import com.github.jreddit.oauth.app.RedditApp;
import com.github.jreddit.oauth.exception.RedditOAuthException;
import com.github.jreddit.oauth.param.RedditDuration;
import com.github.jreddit.oauth.param.RedditScopeBuilder;
import com.github.jreddit.request.util.KeyValueFormatter;

/**
 * Thread-safe reddit OAuth agent.<br>
 * <br>
 * Communicates with reddit to retrieve tokens, and converts them
 * into {@link RedditToken}s, which are used internally by jReddit. This class
 * supports both the <i>code grant flow</i> and <i>implicit grant flow</i>.
 * 
 * @author Simon Kassing
 */
public class RedditOAuthAgent {
    
    /** Reddit authorization endpoint. */
    private static final String REDDIT_AUTHORIZE = "https://www.reddit.com/api/v1/authorize?";
    
    /** Grant type for an installed client (weirdly enough a URI). */
    private static final String GRANT_TYPE_INSTALLED_CLIENT = "https://oauth.reddit.com/grants/installed_client";
    
    /** Grant type for client credentials (described in OAuth2 standard). */
    private static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    
    /* Parameter keys */
    private static final String PARAM_CLIENT_ID = "client_id";
    private static final String PARAM_RESPONSE_TYPE = "response_type";
    private static final String PARAM_STATE = "state";
    private static final String PARAM_REDIRECT_URI = "redirect_uri";
    private static final String PARAM_DURATION = "duration";
    private static final String PARAM_SCOPE = "scope";
    private static final String PARAM_GRANT_TYPE = "grant_type";
    private static final String PARAM_CODE = "code";
    private static final String PARAM_DEVICE_ID = "device_id";
    private static final String PARAM_TOKEN = "token";
    private static final String PARAM_TOKEN_TYPE_HINT = "token_type_hint";
    
    /* Header keys */
    private static final String HEADER_USER_AGENT = "User-Agent";
    private static final String HEADER_AUTHORIZATION = "Authorization";
    
    /** User agent. */
    private final String userAgent;
    
    /** OAuth2 client for OAuth related requests. */
    private OAuthClient oAuthClient;
    
    /** Reddit application. */
    private RedditApp redditApp;
    
    /**
     * Constructor for a Reddit OAuth agent.<br>
     * <br>
     * A default Apache OAuthClient will be made to perform the OAuth communication.
     * 
     * @param userAgent User agent for your application (e.g. "jReddit: Reddit API Wrapper for Java")
     * @param redditApp Reddit application
     */
    public RedditOAuthAgent(String userAgent, RedditApp redditApp) {
        this(userAgent, redditApp, new OAuthClient(new URLConnectionClient()));
    }
    
    /**
     * Constructor for a Reddit OAuth agent.
     * 
     * @param userAgent User agent for your application (e.g. "jReddit: Reddit API Wrapper for Java")
     * @param redditApp Reddit application
     * @param oAuthClient Apache OAuth2 client
     */
    public RedditOAuthAgent(String userAgent, RedditApp redditApp, OAuthClient oAuthClient) {
        this.userAgent = userAgent;
        this.redditApp = redditApp;
        this.oAuthClient = oAuthClient;
    }
    
    /**
     * Generate the <i>code flow</i> Uniform Resource Locator (URI) for a
     * reddit user to authorize your application.<br>
     * <br>
     * The user will, after authorization, receive a <i>code</i>. This can be turned into
     * a <i>RedditToken</i> using {@link #token(String)}.
     * 
     * @param scopeBuilder Authorization scope builder (must not be <i>null</i>)
     * @param duration Duration that the token can last
     * 
     * @return The URI users need to visit and retrieve the <i>code</i> from
     * 
     * @see {@link #token(String)} for converting the <i>code</i> into a usable <i>RedditToken</i>
     */
    public synchronized String generateCodeFlowURI(RedditScopeBuilder scopeBuilder, RedditDuration duration) {
        
        // Set parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_CLIENT_ID, redditApp.getClientID());
        params.put(PARAM_RESPONSE_TYPE, "code");
        params.put(PARAM_STATE, UUID.randomUUID().toString());
        params.put(PARAM_REDIRECT_URI, redditApp.getRedirectURI());
        params.put(PARAM_DURATION, duration.value());
        params.put(PARAM_SCOPE, scopeBuilder.build());
        
        // Create URI
        return REDDIT_AUTHORIZE + KeyValueFormatter.format(params, true);
        
    }
    
    /**
     * Generate the <i>implicit flow</i> Uniform Resource Locator (URI) for a
     * reddit user to authorize your application.<br>
     * <br>
     * The user will, after authorization, receive token information. This can be turned into
     * a <i>RedditToken</i> using {@link #tokenFromInfo(String, String, long, String)}.
     * 
     * @param scopeBuilder Authorization scope builder (must not be <i>null</i>)
     * 
     * @return The URI users need to visit and retrieve the <i>token information</i> from
     * 
     * @see {@link #tokenFromInfo(String, String, long, String)} for converting the
     * <i>token information</i> into <i>RedditToken</i>
     */
    public synchronized String generateImplicitFlowURI(RedditScopeBuilder scopeBuilder) {
        
        // Set parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put(PARAM_CLIENT_ID, redditApp.getClientID());
        params.put(PARAM_RESPONSE_TYPE, "token");
        params.put(PARAM_STATE, UUID.randomUUID().toString());
        params.put(PARAM_REDIRECT_URI, redditApp.getRedirectURI());
        params.put(PARAM_SCOPE, scopeBuilder.build());
        
        // Create URI
        return REDDIT_AUTHORIZE + KeyValueFormatter.format(params, true);
        
    }
    
    /**
     * Add a user agent to the OAuth request.
     * 
     * @param request OAuth request
     */
    private void addUserAgent(OAuthClientRequest request) {
        request.addHeader(HEADER_USER_AGENT, userAgent);
    }
    
    /**
     * Add the basic authentication protocol to the OAuth request using
     * the credentials of the Reddit application provided.
     * 
     * @param request OAuth request
     * @param app Reddit application
     */
    private void addBasicAuthentication(OAuthClientRequest request, RedditApp app) {
        String authString = app.getClientID() + ":" + app.getClientSecret();
        String authStringEnc = DatatypeConverter.printBase64Binary(authString.getBytes());
        request.addHeader(HEADER_AUTHORIZATION, "Basic " + authStringEnc);
    }
    
    /**
     * Token retrieval (<i>code</i> flow).<br>
     * <br>
     * Retrieve a token for a specific user, meaning that the token is <u>coupled to a user</u>. 
     * After it has expired, the token will no longer work. You must either request a new
     * token, or refresh it using {@link #refreshToken(RedditToken)}.
     *
     * @param code One-time code received from the user, after manual authorization by that user
     * 
     * @return Token (associated with a user)
     * 
     * @throws RedditOAuthException
     */
    public synchronized RedditToken token(String code) throws RedditOAuthException {
        
        try {
        
            // Set general values of the request
            OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.REDDIT)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId(redditApp.getClientID())
                .setClientSecret(redditApp.getClientSecret())
                .setRedirectURI(redditApp.getRedirectURI())
                .setParameter(PARAM_CODE, code)
                .buildBodyMessage();
            
            // Add the user agent
            addUserAgent(request);
            
            // Add basic authentication
            addBasicAuthentication(request, redditApp);
            
            // Return a wrapper controlled by jReddit
            return new RedditToken(oAuthClient.accessToken(request));
        
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }

    }
    
    /**
     * Refresh token.<br>
     * <br>
     * This is <u>only</u> possible for tokens retrieved through the <u>code flow</u>
     * authorization and had their duration set to permanent. Tokens that do not have
     * a refresh_token with them or are expired, will not be able to be refreshed. 
     * In that case, a new one must be acquired. 
     *
     * @param rToken Reddit token (which needs to be refreshed)
     *
     * @return Whether the token was successfully refreshed
     * 
     * @throws RedditOAuthException
     * 
     * @see RedditToken#isRefreshable()
     */
    public synchronized boolean refreshToken(RedditToken rToken) throws RedditOAuthException {
        
        try {
            
            // Check whether the token can be refreshed
            if (rToken.isRefreshable()) {
            
                // Set general values of the request
                OAuthClientRequest request = OAuthClientRequest
                    .tokenProvider(OAuthProviderType.REDDIT)
                    .setGrantType(GrantType.REFRESH_TOKEN)
                    .setRefreshToken(rToken.getRefreshToken())
                    .buildBodyMessage();
                
                // Add the user agent
                addUserAgent(request);
                
                // Add basic authentication
                addBasicAuthentication(request, redditApp);
                
                // Return a wrapper controlled by jReddit
                rToken.refresh(oAuthClient.accessToken(request));
                
                return true;
            
            } else {
                
                // The token cannot be refreshed
                return false;
                
            }
        
        
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }
        
    }
    
    /**
     * Token retrieval (<i>app-only</i> flow).<br>
     * <br>
     * Retrieve a token for the <u>application-only</u>, meaning that the
     * token is <u>not coupled to any user</u>. The token is typically only
     * <u>valid for a short period of time</u> (at the moment of writing: 1 hour).
     * After it has expired, the token will no longer work. You must request a <u>new</u>
     * token in that case. Refreshing an application-only token is not possible.
     *
     * @param confidential <i>True</i>: confidential clients (web apps / scripts) not acting on
     *                     behalf of one or more logged out users. <i>False</i>: installed app types,
     *                     and other apps acting on behalf of one or more logged out users.
     * 
     * @return Token (not associated with a user)
     * 
     * @throws RedditOAuthException
     */
    public synchronized RedditToken tokenAppOnly(boolean confidential) throws RedditOAuthException {
        
        try {
        
            // Set general values of the request
            TokenRequestBuilder builder = OAuthClientRequest
                .tokenProvider(OAuthProviderType.REDDIT)
                .setParameter(PARAM_GRANT_TYPE, confidential ? GRANT_TYPE_CLIENT_CREDENTIALS : GRANT_TYPE_INSTALLED_CLIENT)
                .setClientId(redditApp.getClientID())
                .setClientSecret(redditApp.getClientSecret());
            
            // If it is not acting on behalf of a unique client, a unique device identifier must be generated:
            if (!confidential) {
                builder = builder.setParameter(PARAM_DEVICE_ID, UUID.randomUUID().toString());
            }
            
            // Build the request
            OAuthClientRequest request = builder.buildBodyMessage();
            
            // Add the user agent
            addUserAgent(request);
            
            // Add basic authentication
            addBasicAuthentication(request, redditApp);
            
            // Return a wrapper controlled by jReddit
            return new RedditToken(oAuthClient.accessToken(request));
            
        } catch (OAuthSystemException oase) {
            throw new RedditOAuthException(oase);
        } catch (OAuthProblemException oape) {
            throw new RedditOAuthException(oape);
        }
        
    }
    
    /**
     * Generate a token from information received using the <i>implicit grant flow</i>.<br>
     * <br>
     * <b>WARNING:</b> The expiration of the token is no longer very accurate. There is a delay
     * between the user receiving the token, and inputting it into this function. Beware that the
     * token might expire earlier than that the token reports it to.
     * 
     * @param accessToken Access token
     * @param tokenType Token type (commonly "bearer")
     * @param expiresIn Expires in (seconds)
     * @param scope Scope
     * 
     * @return <i>RedditToken</i> generated using the given information.
     */
    public synchronized RedditToken tokenFromInfo(String accessToken, String tokenType, long expiresIn, String scope) {
        return new RedditToken(accessToken, tokenType, expiresIn, scope);
    }
    
    /**
     * Revocation of a <i>RedditToken</i>.<br>
     * <br>
     * Be sure to not use the token after
     * calling this function, as its state pertaining its validity (e.g. scope, 
     * expiration, refreshability) is no longer valid when it is revoked.<br>
     * <br>
     * <i>Note: Per RFC 7009, this request will return a success (204) response
     * even if the passed in token was never valid.</i>
     * 
     * @param token <i>RedditToken</i> to revoke
     * @param revokeAccessTokenOnly Whether to only revoke the access token, or both
     * 
     * @return Whether the token is no longer valid
     * @throws RedditOAuthException 
     */
    public boolean revoke(RedditToken token, boolean revokeAccessTokenOnly) throws RedditOAuthException {
    	try{
        // TODO: Implement
        // https://www.reddit.com/api/v1/revoke_token
        // In POST data: token=TOKEN&token_type_hint=TOKEN_TYPE
        // TOKEN_TYPE: refresh_token or access_token
        // 
    	TokenRequestBuilder builder = OAuthClientRequest
                 .tokenLocation("https://www.reddit.com/api/v1/revoke_token")
                 .setClientId(redditApp.getClientID())
                 .setClientSecret(redditApp.getClientSecret())
                 .setRedirectURI(redditApp.getRedirectURI());
    	
    	if (revokeAccessTokenOnly) {
    		builder = builder.setParameter(PARAM_TOKEN, token.getAccessToken());
    		builder = builder.setParameter(PARAM_TOKEN_TYPE_HINT, "access_token");
    	}else {
    		builder = builder.setParameter(PARAM_TOKEN, token.getRefreshToken());
    		builder = builder.setParameter(PARAM_TOKEN_TYPE_HINT, "refresh_token");
    	}
    	
    	OAuthClientRequest request = builder.buildBodyMessage();
    	
    	addUserAgent(request);
    	
    	addBasicAuthentication(request, redditApp);
    	
    	OAuthResourceResponse resp = oAuthClient.resource(request, OAuth.HttpMethod.POST, OAuthResourceResponse.class);
    	System.out.println("Response code is" + resp.getResponseCode());
    	if(resp.getResponseCode() == 204){
    		return true;
    	}else{
    		return false;
    	}
    	
    } catch (OAuthSystemException oase) {
        throw new RedditOAuthException(oase);
    } catch (OAuthProblemException oape) {
        throw new RedditOAuthException(oape);
    }

 }
    
}
