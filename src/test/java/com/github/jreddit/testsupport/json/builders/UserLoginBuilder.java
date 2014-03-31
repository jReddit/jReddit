package com.github.jreddit.testsupport.json.builders;

import com.github.jreddit.model.json.response.UserLogin;

public class UserLoginBuilder {

    private String cookie;
    private String modhash;
    private String[][] errors;

    public static UserLoginBuilder userLogin() {
        return new UserLoginBuilder();
    }

    public UserLoginBuilder withCookie(String cookie) {
        this.cookie = cookie;
        return this;
    }

    public UserLoginBuilder withModhash(String modhash) {
        this.modhash = modhash;
        return this;
    }

    public UserLoginBuilder withErrors(String[][] errors) {
        this.errors = errors;
        return this;
    }

    public UserLogin build() {
        UserLogin userLogin = new UserLogin();
        UserLogin.Json json = new UserLogin.Json();
        UserLogin.Json.Data data = new UserLogin.Json.Data();
        json.setData(data);
        userLogin.setJson(json);

        if (cookie != null) {
            data.setCookie(cookie);
        }

        if (modhash != null) {
            data.setModhash(modhash);
        }

        if (errors != null) {
            json.setErrors(errors);
        }

        return userLogin;
    }
}
