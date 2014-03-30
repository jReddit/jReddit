package com.github.jreddit.model.json.response;

public class CommentListingItem extends RedditListingItem {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String approvedBy;
        private String author;
        private String authorFlairCssClass;
        private String authorFlairText;
        private String bannedBy;
        private String body;
        private String bodyHtml;
        private double created;
        private double createdUtc;
        private String distinguished;
        private long downs;
        private boolean edited;
        private long gilded;
        private String id;
        private String likes;
        private String linkAuthor;
        private String linkId;
        private String linkTitle;
        private String linkUrl;
        private String name;
        private long numReports;
        private String parentId;
        private String replies;
        private boolean saved;
        private boolean scoreHidden;
        private String subreddit;
        private String subredditId;
        private long ups;

        public String getApprovedBy() {
            return approvedBy;
        }

        public void setApproved_by(String approvedBy) {
            this.approvedBy = approvedBy;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getAuthorFlairCssClass() {
            return authorFlairCssClass;
        }

        public void setAuthor_flair_css_class(String authorFlairCssClass) {
            this.authorFlairCssClass = authorFlairCssClass;
        }

        public String getAuthorFlairText() {
            return authorFlairText;
        }

        public void setAuthor_flair_text(String authorFlairText) {
            this.authorFlairText = authorFlairText;
        }

        public String getBannedBy() {
            return bannedBy;
        }

        public void setBanned_by(String bannedBy) {
            this.bannedBy = bannedBy;
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

        public String getDistinguished() {
            return distinguished;
        }

        public void setDistinguished(String distinguished) {
            this.distinguished = distinguished;
        }

        public long getDowns() {
            return downs;
        }

        public void setDowns(long downs) {
            this.downs = downs;
        }

        public boolean getEdited() {
            return edited;
        }

        public void setEdited(boolean edited) {
            this.edited = edited;
        }

        public long getGilded() {
            return gilded;
        }

        public void setGilded(long gilded) {
            this.gilded = gilded;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLikes() {
            return likes;
        }

        public void setLikes(String likes) {
            this.likes = likes;
        }

        public String getLinkAuthor() {
            return linkAuthor;
        }

        public void setLink_author(String linkAuthor) {
            this.linkAuthor = linkAuthor;
        }

        public String getLinkId() {
            return linkId;
        }

        public void setLink_id(String linkId) {
            this.linkId = linkId;
        }

        public String getLinkTitle() {
            return linkTitle;
        }

        public void setLink_title(String linkTitle) {
            this.linkTitle = linkTitle;
        }

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLink_url(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getNumReports() {
            return numReports;
        }

        public void setNum_reports(long numReports) {
            this.numReports = numReports;
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

        public boolean isSaved() {
            return saved;
        }

        public void setSaved(boolean saved) {
            this.saved = saved;
        }

        public boolean isScoreHidden() {
            return scoreHidden;
        }

        public void setScore_hidden(boolean scoreHidden) {
            this.scoreHidden = scoreHidden;
        }

        public String getSubreddit() {
            return subreddit;
        }

        public void setSubreddit(String subreddit) {
            this.subreddit = subreddit;
        }

        public String getSubredditId() {
            return subredditId;
        }

        public void setSubreddit_id(String subredditId) {
            this.subredditId = subredditId;
        }

        public long getUps() {
            return ups;
        }

        public void setUps(long ups) {
            this.ups = ups;
        }
    }
}
