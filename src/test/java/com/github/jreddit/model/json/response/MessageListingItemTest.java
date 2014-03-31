package com.github.jreddit.model.json.response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static java.lang.Thread.currentThread;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MessageListingItemTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void createMessageListing() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/messagelisting-item.json");
        MessageListingItem message = mapper.readValue(resource, MessageListingItem.class);

        assertFalse(message.getData().wasComment());
        assertTrue(message.getKind().equals("t4"));
    }

    @Test
    public void createInboxListing() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/inbox-messages.json");
        RedditListing<MessageListingItem> inboxListing = mapper.readValue(resource, new TypeReference<RedditListing<MessageListingItem>>() { });

        MessageListingItem[] messages = inboxListing.getData().getChildren();
        MessageListingItem message = messages[0];

        assertFalse(message.getData().wasComment());
        assertTrue(message.getKind().equals("t4"));
    }

}
