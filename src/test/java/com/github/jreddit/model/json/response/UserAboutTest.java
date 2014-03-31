package com.github.jreddit.model.json.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.currentThread;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserAboutTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void createHappyAboutObject() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/user-about.json");
        UserAbout userInfo = mapper.readValue(resource, UserAbout.class);

        assertFalse(userInfo.getData().isFriend());
    }
}
