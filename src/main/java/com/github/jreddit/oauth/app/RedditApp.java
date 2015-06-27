package com.github.jreddit.oauth.app;

public abstract class RedditApp {
    
    private final String clientID;
    private final String clientSecret;
    private final String redirectURI;
    
    /**
     * Reddit Application.<br>
     * <br>
     * <i>All</i> information given in this constructor <i>must</i>
     * match the information stated on reddit.
     * 
     * @param clientID Client identifier (e.g. "p_jcolKysdMFud")
     * @param clientSecret Client secret (e.g. "gko_LXEJKF89djs98fhFJkj9s")
     * @param redirectURI Redirect URI (e.g. "http://www.example.com/auth")
     */
    public RedditApp(String clientID, String clientSecret, String redirectURI) {
        this.clientID = clientID;
        this.clientSecret = clientSecret;
        this.redirectURI = redirectURI;
    }
    
    /**
     * Retrieve client identifier.
     * 
     * @return Client identifier (e.g. "p_jcolKysdMFud")
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * Retrieve client secret.
     * 
     * @return Client secret (e.g. "gko_LXEJKF89djs98fhFJkj9s")
     */
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Retrieve redirect Uniform Resource Identifier.
     * 
     * @return Redirect URI (e.g. "http://www.example.com/auth")
     */
    public String getRedirectURI() {
        return redirectURI;
    }
    
}
