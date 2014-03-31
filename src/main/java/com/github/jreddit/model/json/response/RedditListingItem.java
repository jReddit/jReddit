package com.github.jreddit.model.json.response;

public abstract class RedditListingItem {

    private String kind;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    //TODO work out how to generify the data stuff
}
