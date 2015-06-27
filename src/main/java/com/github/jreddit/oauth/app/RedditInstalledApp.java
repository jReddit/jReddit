package com.github.jreddit.oauth.app;

public class RedditInstalledApp extends RedditApp {

    /**
     * Reddit Installed Application.<br>
     * <br>
     * <i>All</i> information given in this constructor <i>must</i>
     * match the information stated on reddit. The secret is defaulted
     * to an empty string.
     * 
     * @param clientID Client identifier (e.g. "p_jcolKysdMFud")
     * @param redirectURI Redirect URI (e.g. "http://www.example.com/auth")
     */
    public RedditInstalledApp(String clientID, String redirectURI) {
        super(clientID, "", redirectURI); // Empty string is the secret for an installed app
    }
    
}
