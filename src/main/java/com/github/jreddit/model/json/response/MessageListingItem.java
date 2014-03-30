package com.github.jreddit.model.json.response;

public class MessageListingItem extends RedditListingItem {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String author;
        private String body;
        private String bodyHtml;
        private String context;
        private double created;
        private double createdUtc;
        private String dest;
        private String firstMessage;
        private String firstMessageName;
        private String id;
        private String name;
        private boolean isNew;
        private String parentId;
        private String replies;
        private String subject;
        private String subreddit;
        private boolean wasComment;

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String Body) {
            this.body = Body;
        }

        public String getBodyHtml() {
            return bodyHtml;
        }

        public void setBody_html(String bodyHtml) {
            this.bodyHtml = bodyHtml;
        }

        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public double getCreated() {
            return created;
        }

        public void setCreated(double created) {
            this.created = created;
        }

        public double getCreatedUtc() {
            return createdUtc;
        }

        public void setCreated_utc(double createdUtc) {
            this.createdUtc = createdUtc;
        }

        public String getDest() {
            return dest;
        }

        public void setDest(String dest) {
            this.dest = dest;
        }

        public String getFirstMessage() {
            return firstMessage;
        }

        public void setFirst_message(String firstMessage) {
            this.firstMessage = firstMessage;
        }

        public String getFirstMessageName() {
            return firstMessageName;
        }

        public void setFirst_message_name(String firstMessageName) {
            this.firstMessageName = firstMessageName;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isNew() {
            return isNew;
        }

        public void setNew(boolean isNew) {
            this.isNew = isNew;
        }

        public String getParentId() {
            return parentId;
        }

        public void setParent_id(String parentId) {
            this.parentId = parentId;
        }

        public String getReplies() {
            return replies;
        }

        public void setReplies(String replies) {
            this.replies = replies;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getSubreddit() {
            return subreddit;
        }

        public void setSubreddit(String subreddit) {
            this.subreddit = subreddit;
        }

        public boolean wasComment() {
            return wasComment;
        }

        public void setWas_comment(boolean wasComment) {
            this.wasComment = wasComment;
        }
    }
}
