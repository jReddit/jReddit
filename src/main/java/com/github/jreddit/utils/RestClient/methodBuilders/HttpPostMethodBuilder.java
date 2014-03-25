package com.github.jreddit.utils.RestClient.methodBuilders;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;

public class HttpPostMethodBuilder extends HttpMethodBuilder<HttpPostMethodBuilder, HttpPost>{
    public static HttpPostMethodBuilder httpPostMethod() {
        return new HttpPostMethodBuilder();
    }

    @Override
    public HttpPost build() {
        HttpPost httpGet = new HttpPost(uri);
        for (Header header : headers) {
            httpGet.addHeader(header);
        }
        return httpGet;
    }
}
