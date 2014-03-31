package com.github.jreddit.model.json.response;

public class SubredditListingItem extends RedditListingItem {

    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        private String accountsActive;
        private long commentScoreHideMins;
        private double created;
        private double createdUtc;
        private String description;
        private String descriptionHtml;
        private String displayName;
        private String headerImg;
        private long[] headerSize;
        private String headerTitle;
        private String id;
        private String name;
        private boolean over18;
        private String publicDescription;
        private boolean publicTraffic;
        private String submissionType;
        private String submitLinkLabel;
        private String submitText;
        private String submitTextHtml;
        private String submitTextLabel;
        private String subredditType;
        private long subscribers;
        private String title;
        private String url;
        private String userIsBanned;
        private String userIsContributor;
        private String userIsModerator;
        private String userIsSubscriber;

        public String getAccountsActive() {
            return accountsActive;
        }

        public void setAccounts_active(String accountsActive) {
            this.accountsActive = accountsActive;
        }

        public long getCommentScoreHideMins() {
            return commentScoreHideMins;
        }

        public void setComment_score_hide_mins(long commentScoreHideMins) {
            this.commentScoreHideMins = commentScoreHideMins;
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDescriptionHtml() {
            return descriptionHtml;
        }

        public void setDescription_html(String descriptionHtml) {
            this.descriptionHtml = descriptionHtml;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplay_name(String displayName) {
            this.displayName = displayName;
        }

        public String getHeaderImg() {
            return headerImg;
        }

        public void setHeader_img(String headerImg) {
            this.headerImg = headerImg;
        }

        public long[] getHeaderSize() {
            return headerSize;
        }

        public void setHeader_size(long[] headerSize) {
            this.headerSize = headerSize;
        }

        public String getHeaderTitle() {
            return headerTitle;
        }

        public void setHeader_title(String headerTitle) {
            this.headerTitle = headerTitle;
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

        public boolean isOver18() {
            return over18;
        }

        public void setOver18(boolean over18) {
            this.over18 = over18;
        }

        public String getPublicDescription() {
            return publicDescription;
        }

        public void setPublic_description(String publicDescription) {
            this.publicDescription = publicDescription;
        }

        public boolean isPublicTraffic() {
            return publicTraffic;
        }

        public void setPublic_traffic(boolean publicTraffic) {
            this.publicTraffic = publicTraffic;
        }

        public String getSubmissionType() {
            return submissionType;
        }

        public void setSubmission_type(String submissionType) {
            this.submissionType = submissionType;
        }

        public String getSubmitLinkLabel() {
            return submitLinkLabel;
        }

        public void setSubmit_link_label(String submitLinkLabel) {
            this.submitLinkLabel = submitLinkLabel;
        }

        public String getSubmitText() {
            return submitText;
        }

        public void setSubmit_text(String submitText) {
            this.submitText = submitText;
        }

        public String getSubmitTextHtml() {
            return submitTextHtml;
        }

        public void setSubmit_text_html(String submitTextHtml) {
            this.submitTextHtml = submitTextHtml;
        }

        public String getSubmitTextLabel() {
            return submitTextLabel;
        }

        public void setSubmit_text_label(String submitTextLabel) {
            this.submitTextLabel = submitTextLabel;
        }

        public String getSubredditType() {
            return subredditType;
        }

        public void setSubreddit_type(String subredditType) {
            this.subredditType = subredditType;
        }

        public long getSubscribers() {
            return subscribers;
        }

        public void setSubscribers(long subscribers) {
            this.subscribers = subscribers;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUserIsBanned() {
            return userIsBanned;
        }

        public void setUser_is_banned(String userIsBanned) {
            this.userIsBanned = userIsBanned;
        }

        public String getUserIsContributor() {
            return userIsContributor;
        }

        public void setUser_is_contributor(String userIsContributor) {
            this.userIsContributor = userIsContributor;
        }

        public String getUserIsModerator() {
            return userIsModerator;
        }

        public void setUser_is_moderator(String userIsModerator) {
            this.userIsModerator = userIsModerator;
        }

        public String getUserIsSubscriber() {
            return userIsSubscriber;
        }

        public void setUser_is_subscriber(String userIsSubscriber) {
            this.userIsSubscriber = userIsSubscriber;
        }
    }
}
