package com.github.jreddit.testsupport;

import com.github.jreddit.utils.restclient.ResponseWithJsonSimple;

public class UtilResponse implements ResponseWithJsonSimple {

    private final String responseBody;
    private final Object responseObject;
    private final int statusCode;

    public UtilResponse(String responseBody, Object responseObject, int statusCode) {
        this.responseBody = responseBody;
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
    public String getResponseBody() {
        return responseBody;
    }
}
