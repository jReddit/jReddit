package com.github.jreddit.retrieval.params;

/**
 * Enumeration to represent the different subreddit categories.
 * @author Simon Kassing
 */
public enum SubredditsView {

    NEW("new"), 
    POPULAR("popular"), 
    MINE_SUBSCRIBER("mine/subscriber"), 
    MINE_CONTRIBUTOR("mine/contributor"), 
    MINE_MODERATOR("mine/moderator");

    private final String value;

    SubredditsView(String value) {
        this.value = value;
    }

    public String value() {
        return this.value;
    }
    
}
