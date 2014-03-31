package com.github.jreddit.model.json.response;


public class UserLogin {
    private Json json;

    public Json getJson() {
        return json;
    }

    public void setJson(Json json) {
        this.json = json;
    }

    public static class Json {
        private Data data;

        private String[][] errors;

        public static class Data {
            private String cookie;
            private String modhash;

            public String getCookie() {
                return cookie;
            }

            public void setCookie(String cookie) {
                this.cookie = cookie;
            }

            public String getModhash() {
                return modhash;
            }

            public void setModhash(String modhash) {
                this.modhash = modhash;
            }
        }

        public String[][] getErrors() {
            return errors;
        }

        public void setErrors(String[][] errors) {
            this.errors = errors;
        }

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }
    }
}
