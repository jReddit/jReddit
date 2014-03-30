package com.github.jreddit.utils.restclient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jreddit.model.json.response.*;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.CommentSort;
import com.github.jreddit.utils.Sort;
import com.github.jreddit.utils.restclient.methodbuilders.HttpGetMethodBuilder;
import com.github.jreddit.utils.restclient.methodbuilders.HttpPostMethodBuilder;
import com.github.jreddit.utils.restclient.submissionbuilders.PostBuilder;
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

    public RedditListing<SubmissionListingItem> getSubmissions(String username, String type, Sort sorting) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(USER_SUBMISSIONS_INTERACTION, username, type, sorting.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<SubmissionListingItem>>() { });
    }

    public RedditListing<CommentListingItem> getComments(String username, CommentSort commentSort) throws URISyntaxException, IOException {
        HttpGetMethodBuilder builder = httpGetMethod().withUrl(REDDIT_BASE_URL + format(ApiEndpointUtils.USER_COMMENTS, username, commentSort.getValue()));

        return mapper.readValue(restClient.get(builder).getResponseBody(), new TypeReference<RedditListing<CommentListingItem>>() { });
    }

    public void submit(PostBuilder postBuilder) throws URISyntaxException, IOException {
        HttpPostMethodBuilder builder = httpPostMethod().withUrl(REDDIT_BASE_URL + USER_SUBMIT);

        restClient.post(builder, postBuilder.build());
    }

    public static List<NameValuePair> loginParameters(String username, String password) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();

        params.add(new BasicNameValuePair("api_type", "json"));
        params.add(new BasicNameValuePair("user", username));
        params.add(new BasicNameValuePair("passwd", password));
        return params;
    }
}
