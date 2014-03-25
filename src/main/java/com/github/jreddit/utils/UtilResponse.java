package com.github.jreddit.utils;

import com.github.jreddit.utils.RestClient.Response;

public class UtilResponse implements Response {

    private String responseText;
    private Object responseObject;
    private int statusCode;

    public UtilResponse(String responseText, Object responseObject, int statusCode) {
        this.responseText = responseText;
        this.responseObject = responseObject;
        this.statusCode = statusCode;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public Object getResponseObject() {
        return responseObject;
    }

    @Override
    public String getResponseText() {
        return responseText;
    }
}
