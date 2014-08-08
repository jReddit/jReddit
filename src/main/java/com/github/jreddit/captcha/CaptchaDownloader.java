package com.github.jreddit.captcha;

import com.github.jreddit.utils.ApiEndpointUtils;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Used to download Captcha's.
 * TODO: support different image formats, maybe a writeCaptchaImageToFile() function?
 * 
 * @author Simon Kassing
 */
public class CaptchaDownloader {
	
    /**
     * Fetch a rendered image of the captcha that is identified by the iden given along.
     * @param iden Captcha identifier
     * @return Captcha rendered image
     * @throws IOException 	Thrown if the image retrieval failed
     */
    public RenderedImage getCaptchaImage(String iden) throws IOException {
        URL url = new URL(ApiEndpointUtils.REDDIT_BASE_URL + "/captcha/" + iden + ".png");
        RenderedImage captcha = ImageIO.read(url);
        return captcha;
    }
    
}
