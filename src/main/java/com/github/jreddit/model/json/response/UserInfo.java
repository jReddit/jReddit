package com.github.jreddit.model.json.response;

public class UserInfo {

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

    public static class Data extends UserAbout.Data{
        private long commentKarma;
        private double created;
        private double createdUtc;
        private boolean hasMail;
        private boolean hasModMail;
        private boolean hasVerifiedEmail;
        private String id;
        private boolean isFriend;
        private boolean isGold;
        private boolean isMod;
        private long linkKarma;
        private String modhash;
        private String name;
        private boolean over18;

        public boolean hasMail() {
            return hasMail;
        }

        public void setHas_mail(boolean hasMail) {
            this.hasMail = hasMail;
        }

        public boolean hasModMail() {
            return hasModMail;
        }

        public void setHas_mod_mail(boolean hasModMail) {
            this.hasModMail = hasModMail;
        }

        public String getModhash() {
            return modhash;
        }

        public void setModhash(String modhash) {
            this.modhash = modhash;
        }

        public boolean over18() {
            return over18;
        }

        public void setOver_18(boolean over18) {
            this.over18 = over18;
        }
    }

}
