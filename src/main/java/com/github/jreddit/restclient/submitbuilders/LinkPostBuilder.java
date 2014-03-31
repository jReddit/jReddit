package com.github.jreddit.restclient.submitbuilders;

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
        List<NameValuePair> postParams = super.build();
        postParams.add(new BasicNameValuePair("kind", "link"));
        postParams.add(new BasicNameValuePair("url", url));

        return postParams;
    }
}
