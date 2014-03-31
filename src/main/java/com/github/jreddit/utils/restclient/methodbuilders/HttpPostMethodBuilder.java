package com.github.jreddit.utils.restclient.methodbuilders;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpPost;

public class HttpPostMethodBuilder extends HttpMethodBuilder<HttpPostMethodBuilder, HttpPost>{
    public static HttpPostMethodBuilder httpPostMethod() {
        return new HttpPostMethodBuilder();
    }

    @Override
    public HttpPost build() {
        HttpPost httpPost = new HttpPost(uri);
        for (Header header : headers) {
            httpPost.addHeader(header);
        }
        return httpPost;
    }
}
