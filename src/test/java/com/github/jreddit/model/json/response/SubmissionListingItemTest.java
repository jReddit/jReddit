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

public class SubmissionListingItemTest {

    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void createMessageListing() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/submissionlisting-item.json");
        SubmissionListingItem submission = mapper.readValue(resource, SubmissionListingItem.class);

        assertTrue(submission.getData().getUps() == 1L);
    }

    @Test
    public void createInboxListing() throws IOException {
        URL resource = currentThread().getContextClassLoader().getResource("examples/submissionlistings.json");
        RedditListing<SubmissionListingItem> submissionListings = mapper.readValue(resource, new TypeReference<RedditListing<SubmissionListingItem>>() { });

        SubmissionListingItem[] submissions = submissionListings.getData().getChildren();
        SubmissionListingItem submission = submissions[0];

        assertTrue(submission.getData().getUps() == 1L);
    }
}
