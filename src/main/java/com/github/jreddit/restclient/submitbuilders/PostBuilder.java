package com.github.jreddit.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public abstract class PostBuilder<T extends PostBuilder> {
    private String title;
    private String subreddit;
    private String modhash;

    public T withTitle(String title) {
        this.title = title;
        return (T) this;
    }

    public T withSubreddit(String subreddit) {
        this.subreddit = subreddit;
        return (T) this;
    }

    public T withModhash(String modhash) {
        this.modhash = modhash;
        return (T) this;
    }

    public List<NameValuePair> build() {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new post without a title specified");
        }

        if (subreddit == null || subreddit.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new post without a subreddit specified");
        }

        if (modhash == null || modhash.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new post without a modhash specified");
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("title", title));
        postParams.add(new BasicNameValuePair("sr", subreddit));
        postParams.add(new BasicNameValuePair("uh", modhash));
        return postParams;
    }
}
