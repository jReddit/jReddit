package com.github.jreddit.utils.restclient.submitbuilders;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class VoteBuilder {

    private String modhash;
    private String thingId;
    private VOTE_DIRECTION vote;

    public enum VOTE_DIRECTION {
        UP("1"),
        DOWN("-1"),
        NEUTRAL("0");

        private String weight;

        private VOTE_DIRECTION(String weight) {
            this.weight = weight;
        }

        public String getWeight() {
            return weight;
        }
    }

    public static VoteBuilder vote() {
        return new VoteBuilder();
    }

    public VoteBuilder withModhash(String modhash) {
        this.modhash = modhash;
        return this;
    }

    public VoteBuilder withThingId(String thingId) {
        this.thingId = thingId;
        return this;
    }

    public VoteBuilder withVote(VOTE_DIRECTION vote) {
        this.vote = vote;
        return this;
    }

    public List<NameValuePair> build() {
        if (thingId == null || thingId.isEmpty()) {
            throw new IllegalArgumentException("You cannot vote without a thingId to vote on specified");
        }

        if (modhash == null || modhash.isEmpty()) {
            throw new IllegalArgumentException("You cannot vote without a modhash specified");
        }

        if (vote == null) {
            throw new IllegalArgumentException("You cannot vote without specifying how you wish to vote");
        }

        List<NameValuePair> postParams = new ArrayList<NameValuePair>();
        postParams.add(new BasicNameValuePair("thing_id", thingId));
        postParams.add(new BasicNameValuePair("dir", vote.getWeight()));
        postParams.add(new BasicNameValuePair("uh", modhash));
        return postParams;
    }
}
