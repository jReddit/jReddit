package com.github.jreddit.oauth.app;

public class RedditWebApp extends RedditApp {

    /**
     * Reddit Web Application.<br>
     * <br>
     * <i>All</i> information given in this constructor <i>must</i>
     * match the information stated on reddit.
     * 
     * @param clientID Client identifier (e.g. "p_jcolKysdMFud")
     * @param clientSecret Client secret (e.g. "gko_LXEJKF89djs98fhFJkj9s")
     * @param redirectURI Redirect URI (e.g. "http://www.example.com/auth")
     */
    public RedditWebApp(String clientID, String clientSecret, String redirectURI) {
        super(clientID, clientSecret, redirectURI);
    }
    
}
