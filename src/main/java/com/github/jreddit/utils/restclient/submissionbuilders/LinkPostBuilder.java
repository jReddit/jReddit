package com.github.jreddit.utils.restclient.submissionbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class LinkPostBuilder extends PostBuilder<LinkPostBuilder> {

    private String url;

    public static LinkPostBuilder linkPost() {
        return new LinkPostBuilder();
    }

    public LinkPostBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    @Override
    public List<NameValuePair> build() {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new link post without a url specified");
        }
        List<NameValuePair> headers = super.build();
        headers.add(new BasicNameValuePair("kind", "link"));
        headers.add(new BasicNameValuePair("url", url));

        return headers;
    }
}
