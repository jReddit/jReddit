package com.github.jreddit.request.reddit.oauth;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;

import com.github.jreddit.request.reddit.oauth.param.RedditScope;
import com.github.jreddit.request.reddit.oauth.param.RedditTokenScopes;

/**
 * OAuth2 token wrapper for reddit.<br>
 * <br>
 * This class wraps the information received from reddit following
 * the request for a token.<br>
 * A token has three dimensions:
 * <ul>
 * <li><b>scope:</b> what can it be used for?
 * <li><b>expiration:</b> how long can it be used?
 * <li><b>refreshable:</b> can its duration be prolonged?
 * </ul>
 * 
 * @author Simon Kassing
 *
 */
public class RedditToken {

	/** Token type parameter. */
	public static final String PARAM_TOKEN_TYPE = "token_type";
	
	/** Access token. */
	private String accessToken;
	
	/** Refresh token. */
	private String refreshToken;
	
	/** Manager of the scopes that this token applies to. */
	private RedditTokenScopes scopes;
	
	/** Token type. Only value possible (15-06-2015): bearer */
	private String tokenType;
	
	/** Time at which the token expires (seconds since UNIX epoch). */
	private long expiration;
	
	/**
	 * Constructor.
	 * 
	 * @param token Access token
	 * @param expiresIn Time before token expires
	 */
	public RedditToken(OAuthJSONAccessTokenResponse token) {
		this.accessToken = token.getAccessToken();
		this.refreshToken = token.getRefreshToken();
		this.expiration = currentTimeSeconds() + token.getExpiresIn();
		this.scopes = new RedditTokenScopes(token.getScope());
		this.tokenType = token.getParam(PARAM_TOKEN_TYPE);
	}
	
	/**
	 * Refresh this reddit token with data received from the new token.
	 * 
	 * @param token Token received from a refresh request to reddit
	 */
	public void refresh(OAuthJSONAccessTokenResponse token) {
		this.accessToken = token.getAccessToken();
		this.expiration = currentTimeSeconds() + token.getExpiresIn();
		this.scopes = new RedditTokenScopes(token.getScope());
		this.tokenType = token.getParam(PARAM_TOKEN_TYPE);
	}

	/**
	 * Retrieve the access token.
	 * 
	 * @return Access token (e.g. "jsdkjfskaj9f8-dnafk")
	 */
	public String getAccessToken() {
		return accessToken;
	}
	
	/**
	 * Retrieve the refresh token.
	 * 
	 * @return Refresh token (e.g. "nvkJu9kjdada2-d98afj")
	 */
	public String getRefreshToken() {
		return refreshToken;
	}

	/**
	 * Retrieve whether the token is expired.
	 * 
	 * @return Is the token expired?
	 */
	public boolean isExpired() {
		return expiration < currentTimeSeconds();
	}
	
	/**
	 * Check whether this token possess this particular authorization scope.
	 * If it does not support the scope, it means that the token does
	 * not have approval from the user to perform the actions belonging to 
	 * that scope.
	 * 
	 * @param scope Reddit scope
	 * 
	 * @return Does this token support this scope?
	 */
	public boolean hasScope(RedditScope scope) {
		return this.scopes.has(scope);
	}
	
	/**
	 * Retrieve the expiration.
	 * 
	 * @return Expiration time (seconds since UNIX epoch)
	 */
	public long getExpiration() {
		return expiration;
	}	
	/**
	 * Retrieve the token type.
	 * 
	 * @return Token type (e.g. "bearer")
	 */
	public String getTokenType() {
		return tokenType;
	}
	
	/**
	 * Check whether the token can be refreshed.
	 * This means that (a) the token has not yet expired, and (b) a refresh token exists.
	 * 
	 * @return Can the token be refreshed?
	 */
	public boolean isRefreshable() {
		return !this.isExpired() && this.getRefreshToken() != null;
	}
	
	/**
	 * Check whether this token will expire within the given time frame (given in seconds).
	 * 
	 * @param seconds Amount of seconds
	 * 
	 * @return Will the token expire within the given time frame?
	 */
	public boolean willExpireIn(long seconds) {
		return currentTimeSeconds() + seconds > expiration;
	}
	
	/**
	 * Retrieve the current time in seconds.
	 * 
	 * Uses <i>System.currentTimeMillis()</i>.
	 * 
	 * @return Current time in seconds.
	 */
	private static long currentTimeSeconds() {
		return System.currentTimeMillis() / (long) 1000;
	}
	
}
