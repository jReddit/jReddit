package com.github.jreddit.oauth.param;

import java.util.HashSet;
import java.util.Set;

/**
 * Builder for the scopes of a request.
 * 
 * @author Simon Kassing
 *
 */
public class RedditScopeBuilder {

	/** Set of reddit scopes. */
	Set<RedditScope> scopes;
	
	/**
	 * Constructor.
	 */
	public RedditScopeBuilder() {
		scopes = new HashSet<RedditScope>();
	}
	
	/**
	 * Build a string for the request. Called upon by
	 * <i>RedditToken</i> when requesting a token that
	 * allows scope definition.
	 * 
	 * @return String list of the scopes
	 */
	public String build() {
		
		// Add each scope to the scope list
		String s = "";
		for (RedditScope scope : scopes) {
			s += scope.value() + RedditScope.SEPARATOR;
		}
		
		// Remove final separator
		if (s.length() > 0) {
			s = s.substring(0, s.length() - RedditScope.SEPARATOR.length());
		}
		
		// Return scope list
		return s;
		
	}
	
	/**
	 * Add a scope to the builder. If the scope has already 
	 * been added, it will not be added again.
	 * 
	 * @param scope Reddit scope
	 * 
	 * @return This builder
	 */
	public RedditScopeBuilder addScope(RedditScope scope) {
		scopes.add(scope);
		return this;
	}
	
	/**
	 * Remove a scope from the builder.
	 * 
	 * @param scope Reddit scope
	 * 
	 * @return This builder
	 */
	public RedditScopeBuilder removeScope(RedditScope scope) {
		scopes.remove(scope);
		return this;
	}
	
}
