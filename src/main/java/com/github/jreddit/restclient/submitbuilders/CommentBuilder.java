package com.github.jreddit.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class CommentBuilder {

    private String modhash;
    private String thingId;
    private String commentText;

    public static CommentBuilder comment() {
        return new CommentBuilder();
    }

    public CommentBuilder withModhash(String modhash) {
        this.modhash = modhash;
        return this;
    }

    public CommentBuilder withThingId(String thingId) {
        this.thingId = thingId;
        return this;
    }

    public CommentBuilder withCommentText(String commentText) {
        this.commentText = commentText;
        return this;
    }

    public List<NameValuePair> build() {
        if (thingId == null || thingId.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new comment without a thingId to comment on specified");
        }

        if (commentText == null || commentText.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new comment without a commentText specified");
        }

        if (modhash == null || modhash.isEmpty()) {
            throw new IllegalArgumentException("You cannot make a new comment without a modhash specified");
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("thing_id", thingId));
        postParams.add(new BasicNameValuePair("text", commentText));
        postParams.add(new BasicNameValuePair("uh", modhash));
        return postParams;
    }
}
