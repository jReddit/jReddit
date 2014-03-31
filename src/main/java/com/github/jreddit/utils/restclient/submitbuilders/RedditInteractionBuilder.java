package com.github.jreddit.utils.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class RedditInteractionBuilder {

    private String modhash;
    private String thingId;

    public static RedditInteractionBuilder redditInteraction() {
        return new RedditInteractionBuilder();
    }

    public RedditInteractionBuilder withModhash(String modhash) {
        this.modhash = modhash;
        return this;
    }

    public RedditInteractionBuilder withThingId(String thingId) {
        this.thingId = thingId;
        return this;
    }

    public List<NameValuePair> build() {
        if (thingId == null || thingId.isEmpty()) {
            throw new IllegalArgumentException("You cannot interact with something without a thingId specified");
        }

        if (modhash == null || modhash.isEmpty()) {
            throw new IllegalArgumentException("You cannot interact with something without a modhash specified");
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("id", thingId));
        postParams.add(new BasicNameValuePair("uh", modhash));
        return postParams;
    }
}
