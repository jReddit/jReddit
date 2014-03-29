package com.github.jreddit.testsupport;

import com.github.jreddit.utils.restclient.Response;

public class UtilResponse implements Response {

    private final String responseText;
    private final Object responseObject;
    private final int statusCode;

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
