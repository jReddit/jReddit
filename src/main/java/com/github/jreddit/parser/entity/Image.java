package com.github.jreddit.parser.entity;

import org.json.simple.JSONObject;
import static com.github.jreddit.parser.util.JsonUtils.*;

/**
 * Represent images preview of a submission.
 */
public class Image {

    private String url;
    private Long width;
    private Long height;

    public Image(JSONObject data) {
        if (data != null) {
            this.url = safeJsonToString(data.get("url"));
            this.width = safeJsonToLong(data.get("width"));
            this.height = safeJsonToLong(data.get("height"));
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getWidth() {
        return width;
    }

    public void setWidth(Long width) {
        this.width = width;
    }

    public Long getHeight() {
        return height;
    }

    public void setHeight(Long height) {
        this.height = height;
    }
}
