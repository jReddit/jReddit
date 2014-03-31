package com.github.jreddit.utils;

import com.github.jreddit.model.domain.ApiEndpoints;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class CaptchaDownloader {
    private static final String IMAGE_FORMAT = "png";
    private static final String IMAGE_PATH = "captcha." + IMAGE_FORMAT;

    public void getCaptchaImage(String iden) throws IOException {
        URL url = new URL(ApiEndpoints.REDDIT_BASE_URL + "/captcha/" + iden + ".png");
        RenderedImage captcha = ImageIO.read(url);
        ImageIO.write(captcha, IMAGE_FORMAT, new File(IMAGE_PATH));
    }
}
