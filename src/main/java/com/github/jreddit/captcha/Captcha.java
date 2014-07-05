package com.github.jreddit.captcha;

import com.github.jreddit.entity.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.restclient.RestClient;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * This class corresponds to the Reddit's captcha class.
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 * @author Simon Kassing
 */
public class Captcha {

    private final RestClient restClient;

    /**
     * Constructor.
     * @param restClient
     * @param captchaDownloader
     */
    public Captcha(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Generates and saves a new reddit captcha in the working directory
     *
     * @param user user to get captcha for
     * @return the iden of the generated captcha as a String
     */
    public String newCaptcha(User user) {
    	JSONObject obj = (JSONObject) restClient.post(null, ApiEndpointUtils.CAPTCHA_NEW, user.getCookie()).getResponseObject();
       	String iden = (String) ((JSONArray) ((JSONArray) ((JSONArray) obj.get("jquery")).get(11)).get(3)).get(0);
        return iden;
    }

    /**
     * Check whether user needs CAPTCHAs for API methods that define the "captcha" and "iden" parameters.
     *
     * @param user user to do the check for
     * @return true if CAPTCHAs are needed, false otherwise
     */
    public boolean needsCaptcha(User user) {
    	// TODO: Object comparison or responsetext comparison?
        return restClient.get(ApiEndpointUtils.CAPTCHA_NEEDS, user.getCookie()).getResponseText().equals("true");
    }

}
