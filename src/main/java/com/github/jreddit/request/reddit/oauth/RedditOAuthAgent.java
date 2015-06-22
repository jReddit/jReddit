package com.github.jreddit.request.reddit.oauth;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.github.jreddit.request.reddit.app.RedditApp;
import com.github.jreddit.request.reddit.oauth.param.RedditDuration;
import com.github.jreddit.request.reddit.oauth.param.RedditScopeBuilder;
import com.github.jreddit.request.reddit.request.KeyValueFormatter;

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
	public static final String REDDIT_AUTHORIZE = "https://www.reddit.com/api/v1/authorize?";
	
	/** Grant type for an installed client (weirdly enough a URI). */
    public static final String GRANT_TYPE_INSTALLED_CLIENT = "https://oauth.reddit.com/grants/installed_client";
    
    /** Grant type for client credentials (described in OAuth2 standard). */
    public static final String GRANT_TYPE_CLIENT_CREDENTIALS = "client_credentials";
    
	/** User agent. */
	private final String userAgent;
	
	/** OAuth2 client. */
	private OAuthClient oAuthClient;
	
	/** Reddit application. */
	private RedditApp redditApp;
    
    /**
     * Constructor for a Reddit OAuth agent.
     * 
     * @param userAgent User agent for your application (e.g. "jReddit: Reddit API Wrapper for Java")
     * @param redditApp Reddit application
     */
    public RedditOAuthAgent(String userAgent, RedditApp redditApp) {
    	this.userAgent = userAgent;
    	this.redditApp = redditApp;
    	
    	// Initialize OAuthClient with custom HTTPClient under the hood
    	this.oAuthClient = new OAuthClient(new URLConnectionClient());
    	
    }
    
    /**
     * Generate the <i>code flow</i> Uniform Resource Locator (URI) for a
     * reddit user to authorize your application.<br>
     * <br>
     * The user will, after authorization, receive a <i>code</i>. This can be turned into
     * a <i>RedditToken</i> using {@link #token(String)}.
     * 
     * @param scopeBuilder Authorization scope builder
     * @param duration Duration that the token can last
     * 
     * @return The URI users need to visit and retrieve the <i>code</i> from
     * 
     * @see {@link #token(String)} for converting the <i>code</i> into a usable <i>RedditToken</i>
     */
    public synchronized String generateCodeFlowURI(RedditScopeBuilder scopeBuilder, RedditDuration duration) {
    	
    	// Set parameters
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("client_id", redditApp.getClientID());
    	params.put("response_type", "code");
    	params.put("state", UUID.randomUUID().toString());
    	params.put("redirect_uri", redditApp.getRedirectURI());
    	params.put("duration", duration.value());
    	params.put("scope", scopeBuilder.build());
    	
    	// Create URI
    	return REDDIT_AUTHORIZE + KeyValueFormatter.format(params);
    	
    }
    
    /**
     * Generate the <i>implicit flow</i> Uniform Resource Locator (URI) for a
     * reddit user to authorize your application.<br>
     * <br>
     * The user will, after authorization, receive token information. This can be turned into
     * a <i>RedditToken</i> using {@link #tokenFromInfo(String)}.
     * 
     * @param scopeBuilder Authorization scope builder
     * 
     * @return The URI users need to visit and retrieve the <i>token information</i> from
     * 
     * @see {@link #tokenFromInfo(String)} for converting the <i>token information</i> into <i>RedditToken</i>
     */
    public synchronized String generateImplicitFlowURI(RedditScopeBuilder scopeBuilder) {
    	
    	// Set parameters
    	Map<String, String> params = new HashMap<String, String>();
    	params.put("client_id", redditApp.getClientID());
    	params.put("response_type", "token");
    	params.put("state", UUID.randomUUID().toString());
    	params.put("redirect_uri", redditApp.getRedirectURI());
    	params.put("scope", scopeBuilder.build());
    	
    	// Create URI
    	return REDDIT_AUTHORIZE + KeyValueFormatter.format(params);
    	
    }
    
    /**
     * Add a user agent to the OAuth request.
     * 
     * @param request OAuth request
     */
    private void addUserAgent(OAuthClientRequest request) {
    	request.addHeader("User-Agent", userAgent);
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
		byte[] authEncBytes = Base64.getEncoder().encode(authString.getBytes());
		String authStringEnc = new String(authEncBytes);
		request.addHeader("Authorization", "Basic " + authStringEnc);
    }
    
    /**
     * Token retrieval (<i>code</i> flow).<br>
     * <br>
     * Retrieve a token for a specific user, meaning that the token is <u>coupled to a user</u>. 
     * After it has expired, the token will no longer work. You must either request a new
     * token, or refresh it using {@link #refreshToken()}.
     * 
     * @param app Application
     * @param code One-time code received from the user, after manual authorization by that user
     * 
     * @return Token (associated with a user)
     * 
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
	public synchronized RedditToken token(String code) throws OAuthSystemException, OAuthProblemException {
		
		// Set general values of the request
		OAuthClientRequest request = OAuthClientRequest
            .tokenProvider(OAuthProviderType.REDDIT)
            .setGrantType(GrantType.AUTHORIZATION_CODE)
            .setClientId(redditApp.getClientID())
            .setClientSecret(redditApp.getClientSecret())
            .setRedirectURI(redditApp.getRedirectURI())
            .setParameter("code", code)
            .buildBodyMessage();
		
		// Add the user agent
		addUserAgent(request);
		
		// Add basic authentication
		addBasicAuthentication(request, redditApp);
        
        // Return a wrapper controlled by jReddit
        return new RedditToken(oAuthClient.accessToken(request));

	}
	
    /**
     * Refresh token.<br>
     * <br>
     * This is <u>only</u> possible for tokens retrieved through the <u>code flow</u>
     * authorization and had their duration set to permanent. Tokens that do not have
     * a refresh_token with them or are expired, will not be able to be refreshed. 
     * In that case, a new one must be acquired. 
     * 
     * @param app Application
     * @param rToken Reddit token (which needs to be refreshed)
     * 
     * @return Whether the token was successfully refreshed
     * 
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     * 
     * @see RedditToken#isRefreshable()
     */
	public synchronized boolean refreshToken(RedditToken rToken) throws OAuthSystemException, OAuthProblemException {
		
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
     * @param app Application
     * @param confidential <i>True</i>: confidential clients (web apps / scripts) not acting on behalf of one or more logged out users. <i>False</i>: installed app types, and other apps acting on behalf of one or more logged out users.
     * 
     * @return Token (not associated with a user)
     * 
     * @throws OAuthSystemException
     * @throws OAuthProblemException
     */
	public synchronized RedditToken tokenAppOnly(boolean confidential) throws OAuthSystemException, OAuthProblemException {
		
		// Set general values of the request
		TokenRequestBuilder builder = OAuthClientRequest
            .tokenProvider(OAuthProviderType.REDDIT)
            .setParameter("grant_type", confidential ? GRANT_TYPE_CLIENT_CREDENTIALS : GRANT_TYPE_INSTALLED_CLIENT)
            .setClientId(redditApp.getClientID())
            .setClientSecret(redditApp.getClientSecret());
		
		// If it is not acting on behalf of a unique client, a (unique as possible) device identifier must be generated:
		if (!confidential) {
			builder = builder.setParameter("device_id", UUID.randomUUID().toString());
		}
		
		// Build the request
		OAuthClientRequest request = builder.buildBodyMessage();
		
		// Add the user agent
		addUserAgent(request);
		
		// Add basic authentication
		addBasicAuthentication(request, redditApp);
        
        // Return a wrapper controlled by jReddit
        return new RedditToken(oAuthClient.accessToken(request));
        
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
	 * <i>Note: Per RFC 7009, this request will return a success (204) response even if the passed in token was never valid.</i>
	 * 
	 * @param token <i>RedditToken</i> to revoke
	 * @param revokeAccessTokenOnly Whether to only revoke the access token, or both
	 * 
	 * @return Whether the token is no longer valid
	 */
	public boolean revoke(RedditToken token, boolean revokeAccessTokenOnly) {
		// TODO: Implement
		// https://www.reddit.com/api/v1/revoke_token
		// In POST data: token=TOKEN&token_type_hint=TOKEN_TYPE
		// TOKEN_TYPE: refresh_token or access_token
		// 
		return true;
	}
	
}
