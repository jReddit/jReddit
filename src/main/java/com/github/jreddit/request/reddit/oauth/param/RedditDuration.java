package com.github.jreddit.request.reddit.oauth.param;

/**
 * Enumerator for the duration of tokens.<br>
 * <br>
 * There are two possible values:
 * <ul>
 *   <li><b>permanent:</b> The token can be refreshed as many times as the application desires.</li>
 *   <li><b>temporary:</b> The token cannot be refreshed, and will last for <i>one hour</i>.</li>
 * </ul>
 */
public enum RedditDuration {

	PERMANENT("permanent"),
	TEMPORARY("temporary");
	
    private final String value;

    RedditDuration(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
	
}
