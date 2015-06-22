package com.github.jreddit.oauth.param;

/**
 * Enumerator for the possible authorization scopes for reddit requests.<br>
 * <br>
 * These scopes are used to specify what actions an application
 * can perform with the generated token. The scope can be combined
 * together, so that a single token can have access to multiple scopes.
 * 
 * @see RedditScopeBuilder
 * 
 * @author Simon Kassing
 */
public enum RedditScope {

	IDENTITY("identity"), 
	EDIT("edit"), 
	FLAIR("flair"), 
	HISTORY("history"),  
	MODCONFIG("modconfig"),  
	MODFLAIR("modflair"), 
	MODLOG("modlog"), 
	MODPOSTS("modposts"),  
	MODWIKI("modwiki"), 
	MYSUBREDDITS("mysubreddits"), 
	PRIVATEMESSAGE("privatemessages"),  
	READ("read"), 
	REPORT("report"),  
	SAVE("save"), 
	SUBMIT("submit"),  
	SUBSCRIBE("subscribe"),  
	VOTE("vote"), 
	WIKIEDIT("wikiedit"),  
	WIKIREAD("wikiread");
	
	protected static final String SEPARATOR = ",";
	
    private final String value;

    RedditScope(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }

}
