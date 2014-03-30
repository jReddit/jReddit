package com.github.jreddit.utils.restclient.methodbuilders;

import com.github.jreddit.utils.restclient.RestClientHeader;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public abstract class HttpMethodBuilder<T extends HttpMethodBuilder, O extends HttpRequestBase> {

    protected final List<Header> headers = new ArrayList<Header>();
    protected URI uri;

    public T withUrl(String url) throws URISyntaxException {
        this.uri = new URI(url);
        return (T) this;
    }

    public T withCookie(String cookie) {
        if (cookie != null && !cookie.isEmpty()) {
            headers.add(new RestClientHeader("cookie", "reddit_session=" + cookie));
        }
        return (T) this;
    }

    public T withUserAgent(String userAgent) {
        headers.add(new RestClientHeader("User-Agent", userAgent));
        return (T) this;
    }

    public T withHeaders(List<RestClientHeader> headers) {
        for (RestClientHeader header : headers) {
            this.headers.add(header);
        }
        return (T) this;
    }

    public abstract O build();

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(uri)
                .append(headers)
                .build();
    }

    @Override
    public boolean equals(final Object obj) {
        if(obj instanceof HttpMethodBuilder){
            final HttpMethodBuilder other = (HttpMethodBuilder) obj;
            return new EqualsBuilder()
                    .append(uri, other.uri)
                    .append(headers, other.headers)
                    .isEquals();
        } else{
            return false;
        }
    }
}
