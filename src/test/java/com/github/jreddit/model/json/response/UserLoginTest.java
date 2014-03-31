package com.github.jreddit.model.json.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.currentThread;
import static org.junit.Assert.assertTrue;

public class UserLoginTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void createHappyLoginObject() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/login-response-success.json");
        UserLogin userLogin = mapper.readValue(resource, UserLogin.class);

        assertTrue(userLogin.getJson().getData().getCookie().equals("cookie"));
        assertTrue(userLogin.getJson().getData().getModhash().equals("modHash"));
    }

    @Test
    public void createFailureLoginObject() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/login-response-failure.json");
        UserLogin userLogin = mapper.readValue(resource, UserLogin.class);

        String[][] errors = userLogin.getJson().getErrors();

        assertTrue(errors[0].length == 3);
    }
}
