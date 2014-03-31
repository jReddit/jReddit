package com.github.jreddit.utils.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.message.MessageType;
import com.github.jreddit.model.json.response.*;
import com.github.jreddit.submissions.Page;
import com.github.jreddit.submissions.Popularity;
import com.github.jreddit.subreddit.SubredditType;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import com.github.jreddit.utils.restclient.submitbuilders.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static com.github.jreddit.utils.ApiEndpointUtils.*;
import static com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder.httpGetMethod;
import static com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder.httpPostMethod;
import static java.lang.String.format;

public class RedditServices {

    private final BetterRestClient restClient;
    private final ObjectMapper mapper;

    public RedditServices(BetterRestClient restClient, ObjectMapper mapper) {
        this.restClient = restClient;
        this.mapper = mapper;
    }

    public UserLogin userLogin(String username, String password) throws URISyntaxException, IOException {
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + format(USER_LOGIN, username));

        return mapper.readValue(restClient.post(builder, loginParameters(username, password)).getResponseBody(), UserLogin.class);
    }

    public Boolean userNeedsCaptcha() throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + CAPTCHA_NEEDS);

        String responseBody = restClient.get(builder).getResponseBody();
        return mapper.readValue(responseBody, Boolean.class);
    }

    public String newCaptcha() throws URISyntaxException, IOException {
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + CAPTCHA_NEW);

        //TODO: make this less rubbish
        return mapper.readValue(restClient.post(builder, new ArrayList<NameValuePair>(1)).getResponseBody(), JsonNode.class)
                .get("jquery").path(11).path(3).path(0).asText();
    }

    public RedditListing<SubredditListingItem> getSubscribed() throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + USER_GET_SUBSCRIBED);

        return getSubredditListings(builder);
    }

    public RedditListing<SubredditListingItem> getDefaultReddits() throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + SUBREDDITS);

        return getSubredditListings(builder);
    }

    public RedditListing<SubredditListingItem> getRedditsOfType(SubredditType subredditType) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(ApiEndpointUtils.SUBREDDITS_GET, subredditType.getValue()));

        return getSubredditListings(builder);
    }

    private RedditListing<SubredditListingItem> getSubredditListings(HttpGetMethodBuilder builder) throws IOException {
        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<SubredditListingItem>>() { });
    }

    public SubredditListingItem getSubredditListing(String redditName) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(ApiEndpointUtils.SUBREDDIT_INFO, redditName));

        return mapper.readValue(restClient.get(builder).getResponseBody(), SubredditListingItem.class);
    }

    public UserInfo getUserInfo() throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + USER_INFO);

        return mapper.readValue(restClient.get(builder).getResponseBody(), UserInfo.class);
    }

    public UserAbout getUserAbout(String username) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(ApiEndpointUtils.USER_ABOUT, username));

        return mapper.readValue(restClient.get(builder).getResponseBody(), UserAbout.class);
    }

    public RedditListing<SubmissionListingItem> getUserSubmissions(String username, String type, Sort sorting) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(USER_SUBMISSIONS_INTERACTION, username, type, sorting.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<SubmissionListingItem>>() { });
    }

    public RedditListing<CommentListingItem> getUserComments(String username, CommentSort commentSort) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(ApiEndpointUtils.USER_COMMENTS, username, commentSort.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<CommentListingItem>>() { });
    }

    public RedditListing<SubmissionListingItem> getRedditSubmissions(String redditName, Popularity popularity, Page page) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(SUBREDDIT_SUBMISSIONS, redditName, popularity.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<SubmissionListingItem>>() { });
    }

    public RedditListing<MessageListingItem> getMessages(MessageType messageType) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(MESSAGE_GET, messageType.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<MessageListingItem>>() { });
    }

    public RedditListing<SubmissionListingItem> getSubmissionInfo(String submissionPermaLink) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + submissionPermaLink + "/info.json");

        String responseBody = restClient.get(builder).getResponseBody();

        // Another annoying part of the reddit api: for a single submission info you get a redditListing returned, wrapped in an array...
        // So lets hack up a removal of that array so we cna process like a normal submission listing
        int first = responseBody.indexOf('[');
        int last = responseBody.lastIndexOf(']');
        String substring = responseBody.substring(first + 1, last -1);

        return mapper.readValue(substring, new TypeReference<RedditListing<SubmissionListingItem>>() { });
    }

    public void composeNewMessage(ComposeMessageBuilder composeMessageBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + MESSAGE_COMPOSE);

        BasicResponse response = restClient.post(builder, composeMessageBuilder.build());
    }

    public void markNsfw(RedditInteractionBuilder interactionBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + SUBMISSION_MARK_AS_NSFW);

        BasicResponse response = restClient.post(builder, interactionBuilder.build());
    }

    public void unMarkNsfw(RedditInteractionBuilder interactionBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + SUBMISSION_UNMARK_AS_NSFW);

        BasicResponse response = restClient.post(builder, interactionBuilder.build());
    }

    public void markMessageRead(RedditInteractionBuilder interactionBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + MESSAGE_READ);

        BasicResponse response = restClient.post(builder, interactionBuilder.build());
    }

    public void markMessageUnRead(RedditInteractionBuilder interactionBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + MESSAGE_UNREAD);

        BasicResponse response = restClient.post(builder, interactionBuilder.build());
    }

    public void vote(VoteBuilder votebuilder) throws URISyntaxException, IOException {
        //TODO: what does this return? if it's same return type as submit generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + SUBMISSION_VOTE);

        BasicResponse response = restClient.post(builder, votebuilder.build());

        //TODO: work out how to handle the response to this
        if (response.getResponseBody().contains(".error.USER_REQUIRED")){
            throw new InvalidCookieException("Cookie not present");
        }
    }

    public void comment(CommentBuilder commentBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return? if it's same return type as submit generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + COMMENT);

        BasicResponse response = restClient.post(builder, commentBuilder.build());

        //TODO: work out how to handle the response to this
        if (response.getResponseBody().contains(".error.USER_REQUIRED")){
            throw new InvalidCookieException("Cookie not present");
        }
    }

    public void submit(PostBuilder postBuilder) throws URISyntaxException, IOException {
        //TODO: what does this return?  if it's same return type as comment generify
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + USER_SUBMIT);

        BasicResponse response = restClient.post(builder, postBuilder.build());
    }

    public static List<NameValuePair> loginParameters(String username, String password) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_type", "json"));
        params.add(new BasicNameValuePair("user", username));
        params.add(new BasicNameValuePair("passwd", password));
        return params;
    }
}
