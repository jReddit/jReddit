package com.github.jreddit.model.json.response;

public class UserAbout {

    private Data data;

    private String kind;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public static class Data {
        private long commentKarma;
        private double created;
        private double createdUtc;
        private boolean hasVerifiedEmail;
        private String id;
        private boolean isFriend;
        private boolean isGold;
        private boolean isMod;
        private long linkKarma;
        private String name;

        public long getCommentKarma() {
            return commentKarma;
        }

        public void setComment_karma(long commentKarma) {
            this.commentKarma = commentKarma;
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

        public boolean hasVerifiedEmail() {
            return hasVerifiedEmail;
        }

        public void setHas_verified_email(boolean hasVerifiedEmail) {
            this.hasVerifiedEmail = hasVerifiedEmail;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public boolean isFriend() {
            return isFriend;
        }

        public void setIs_friend(boolean isFriend) {
            this.isFriend = isFriend;
        }

        public boolean isGold() {
            return isGold;
        }

        public void setIs_gold(boolean isGold) {
            this.isGold = isGold;
        }

        public boolean isMod() {
            return isMod;
        }

        public void setIs_mod(boolean isMod) {
            this.isMod = isMod;
        }

        public long getLink_karma() {
            return linkKarma;
        }

        public void setlink_karma(long linkKarma) {
            this.linkKarma = linkKarma;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
