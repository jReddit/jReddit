package com.github.jreddit.request.reddit.oauth;

import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;

public class RedditToken {

	/** Token type parameter. */
	public static final String PARAM_TOKEN_TYPE = "token_type";
	
	/** Access token. */
	private String accessToken;
	
	/** Refresh token. */
	private String refreshToken;
	
	/** 
	 * Comma-separated list of scopes. 
	 * 
	 * Possible scopes (15-06-2015): 
	 * identity, edit, flair, history, modconfig, 
	 * modflair, modlog, modposts, modwiki, mysubreddits, 
	 * privatemessages, read, report, save, submit, 
	 * subscribe, vote, wikiedit, wikiread 
	 * 
	*/
	private String scope;
	
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
		this.expiration = (System.currentTimeMillis() / 1000) + token.getExpiresIn();
		this.scope = token.getScope();
		this.tokenType = token.getParam(PARAM_TOKEN_TYPE);
	}
	
	/**
	 * Refresh this reddit token with data received from the new token.
	 * 
	 * @param token Token received from a refresh request to reddit
	 */
	public void refresh(OAuthJSONAccessTokenResponse token) {
		this.accessToken = token.getAccessToken();
		this.expiration = (System.currentTimeMillis() / 1000) + token.getExpiresIn();
		this.scope = token.getScope();
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
	 * Retrieve whether the token is still valid (before its expiration time).
	 * 
	 * @return Is the token still valid?
	 */
	public boolean isStillValid() {
		return expiration > System.currentTimeMillis();
	}
	
	/**
	 * Retrieve the scope.
	 * 
	 * @return Comma-separated list of scopes (e.g. "identify,flair,report")
	 */
	public String getScope() {
		return scope;
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
		return this.isStillValid() && this.getRefreshToken() != null;
	}
	
}
