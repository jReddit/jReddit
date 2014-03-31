package com.github.jreddit.model.json.response;

public class RedditListing <T extends RedditListingItem> {
    private Data<T> data;
    private String kind;

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static class Data<T> {
        private String after;
        private String before;
        private T[] children;
        private String modhash;

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getBefore() {
            return before;
        }

        public void setBefore(String before) {
            this.before = before;
        }

        public T[] getChildren() {
            return children;
        }

        public void setChildren(T[] children) {
            this.children = children;
        }

        public String getModhash() {
            return modhash;
        }

        public void setModhash(String modhash) {
            this.modhash = modhash;
        }
    }
}
