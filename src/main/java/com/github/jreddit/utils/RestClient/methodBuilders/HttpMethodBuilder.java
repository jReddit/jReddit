package com.github.jreddit.utils.RestClient.methodBuilders;

import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class HttpMethodBuilder<B extends HttpMethodBuilder, O extends HttpRequestBase> {

    protected List<Header> headers = new ArrayList<Header>();
    protected URI uri;

    public B withUrl(String url) throws URISyntaxException {
        this.uri = new URI(url);
        return (B) this;
    }

    public B withCookie(String cookie) {
        if (cookie != null && !cookie.isEmpty()) {
            headers.add(new BasicHeader("cookie", "reddit_session=" + cookie));
        }
        return (B) this;
    }

    public B withUserAgent(String userAgent) {
        headers.add(new BasicHeader("User-Agent", userAgent));
        return (B) this;
    }

    public abstract O build();
}
