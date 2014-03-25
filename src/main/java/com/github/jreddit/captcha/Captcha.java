package com.github.jreddit.captcha;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.RestClient.HttpRestClient;
import com.github.jreddit.utils.RestClient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class corresponds to the Reddit's captcha class.
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Andrei Sfat
 */
public class Captcha {

    private static final String IMAGE_FORMAT = "png";
    private static final String IMAGE_PATH = "captcha." + IMAGE_FORMAT;
    private RestClient restClient = new HttpRestClient();

    /**
     * Generates and saves a new reddit captcha in the working directory
     *
     * @param user user to get captcha for
     * @return the iden of the generated captcha as a String
     */
    public String newCaptcha(User user) {
        String iden = null;

        try {
            // Get the captcha iden
            JSONObject obj = (JSONObject) restClient.post(null, ApiEndpointUtils.CAPTCHA_NEW, user.getCookie()).getResponseObject();
            iden = (String) ((JSONArray) ((JSONArray) ((JSONArray) obj.get("jquery")).get(11)).get(3)).get(0);
            System.out.println("Received CAPTCHA iden: " + iden);

            // Get the corresponding captcha image
            URL url = new URL(ApiEndpointUtils.REDDIT_BASE_URL + "/captcha/" + iden + ".png");
            RenderedImage captcha = ImageIO.read(url);

            // Write the file to disk
            ImageIO.write(captcha, IMAGE_FORMAT, new File(IMAGE_PATH));

        } catch (MalformedURLException e) {
            System.out.println("Invalid URL for retrieving captcha");
        } catch (IOException e) {
            System.out.println("Error reading captcha file");
        }

        return iden;
    }

    /**
     * Check whether user needs CAPTCHAs for API methods that define the "captcha" and "iden" parameters.
     *
     * @param user user to do the check for
     * @return true if CAPTCHAs are needed, false otherwise
     */
    public boolean needsCaptcha(User user) {
        boolean needsCaptcha = false;

        try {
            needsCaptcha = (Boolean) restClient.get(ApiEndpointUtils.CAPTCHA_NEEDS, user.getCookie()).getResponseObject();
        } catch (Exception e) {
            System.err.println("Error verifying if the user needs a captcha");
        }

        return needsCaptcha;
    }

}
