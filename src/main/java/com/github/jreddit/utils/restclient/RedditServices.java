package com.github.jreddit.utils.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jreddit.exception.InvalidCookieException;
import com.github.jreddit.model.json.response.CommentListingItem;
import com.github.jreddit.model.json.response.RedditListing;
import com.github.jreddit.model.json.response.SubmissionListingItem;
import com.github.jreddit.model.json.response.SubredditListingItem;
import com.github.jreddit.model.json.response.UserAbout;
import com.github.jreddit.model.json.response.UserInfo;
import com.github.jreddit.model.json.response.UserLogin;
import com.github.jreddit.submissions.Page;
import com.github.jreddit.submissions.Popularity;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import com.github.jreddit.utils.restclient.submitbuilders.CommentBuilder;
import com.github.jreddit.utils.restclient.submitbuilders.PostBuilder;
import com.github.jreddit.utils.restclient.submitbuilders.VoteBuilder;
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

    public RedditListing<SubredditListingItem> getSubscribed() throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + USER_GET_SUBSCRIBED);

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<SubredditListingItem>>() { });
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
