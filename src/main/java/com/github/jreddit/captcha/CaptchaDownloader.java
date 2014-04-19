package com.github.jreddit.captcha;

import com.github.jreddit.utils.ApiEndpointUtils;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CaptchaDownloader {
    private static final String IMAGE_FORMAT = "png";
    private static final String IMAGE_PATH = "captcha." + IMAGE_FORMAT;

    public void getCaptchaImage(String iden) throws IOException {
        URL url = new URL(ApiEndpointUtils.REDDIT_BASE_URL + "/captcha/" + iden + ".png");
        RenderedImage captcha = ImageIO.read(url);
        ImageIO.write(captcha, IMAGE_FORMAT, new File(IMAGE_PATH));
    }
}
