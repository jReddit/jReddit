package com.github.jreddit.utils.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.List;

public class SelfPostBuilder extends PostBuilder<SelfPostBuilder> {

    private String body;

    public static SelfPostBuilder selfPost() {
        return new SelfPostBuilder();
    }

    public SelfPostBuilder withBody(String body) {
        this.body = body;
        return this;
    }

    @Override
    public List<NameValuePair> build() {
        if (body == null || body.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new self post without a body specified");
        }
        List<NameValuePair> postParams = super.build();
        postParams.add(new BasicNameValuePair("kind", "self"));
        postParams.add(new BasicNameValuePair("text", body));

        return postParams;
    }
}
