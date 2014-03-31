package com.github.jreddit.utils.restclient;

import org.apache.http.HttpResponse;

public class RestResponse implements ResponseWithJsonSimple {
    private final String responseBody;
    private final Object responseObject;
    private final HttpResponse httpResponse;

    public RestResponse(String responseBody, Object responseObject, HttpResponse httpResponse) {
        this.responseBody = responseBody;
        this.responseObject = responseObject;
        this.httpResponse = httpResponse;
    }

    @Override
    public Object getResponseObject() {
        return responseObject;
    }

    @Override
    public String getResponseBody() {
        return responseBody;
    }

    @Override
    public int getStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    public HttpResponse getHttpResponse() {
        return httpResponse;
    }
}
