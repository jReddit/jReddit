package im.goel.jreddit.subreddit;

public enum SubredditType {

    NEW("new"), BANNED("banned"), POPULAR("popular");

    private String value;

    SubredditType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
