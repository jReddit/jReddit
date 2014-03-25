package com.github.jreddit.utils.RestClient.methodBuilders;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpGet;

public class HttpGetMethodBuilder extends HttpMethodBuilder<HttpGetMethodBuilder, HttpGet>{
    public static HttpGetMethodBuilder httpGetMethod() {
        return new HttpGetMethodBuilder();
    }

    @Override
    public HttpGet build() {
        HttpGet httpGet = new HttpGet(uri);
        for (Header header : headers) {
            httpGet.addHeader(header);
        }
        return httpGet;
    }
}
