package com.github.jreddit.utils;

import com.github.jreddit.RedditServices;
import com.github.jreddit.model.domain.CommentSortType;
import com.github.jreddit.model.domain.SubmissionSortType;
import com.github.jreddit.model.json.response.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.github.jreddit.model.domain.CommentSortType.NEW;
import static com.github.jreddit.restclient.submitbuilders.LinkPostBuilder.linkPost;
import static com.github.jreddit.restclient.submitbuilders.SelfPostBuilder.selfPost;

/**
 * This class represents a user connected to Reddit.
 *
 * @author Omer Elnour
 * @author Karan Goel
 * @author Raul Rene Lepsa
 * @author Benjamin Jakobus
 * @author Evin Ugur
 * @author Andrei Sfat
 */
public class User {

    private final String username;
    private final RedditServices redditServices;
    private String modhash, cookie, password;
    private String kind;
    private String fullName;

    public User(RedditServices redditServices, String username, String password) {
        this.redditServices = redditServices;
        this.username = username;
        this.password = password;
    }

    public String getModhash() {
        return modhash;
    }

    public String getCookie() {
        return cookie;
    }

    /**
     * Call this function to connect the user. <br /> By "connect" I mean
     * effectively sending a POST request to reddit and getting the modhash and
     * cookie, which are required for most reddit API functions.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public void connect() throws IOException, URISyntaxException {
        UserLogin userLogin = redditServices.userLogin(username, password);
        this.modhash = userLogin.getJson().getData().getModhash();
        this.cookie = userLogin.getJson().getData().getCookie();
    }

    /**
     * This function submits a link to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param link      The link to the submission
     * @param subreddit The subreddit to submit to
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public void submitLink(String title, String link, String subreddit) throws IOException, URISyntaxException {
        redditServices.submit(linkPost().withUrl(link).withTitle(title).withSubreddit(subreddit).withModhash(modhash));
    }

    /**
     * This function submits a self post to the specified subreddit.
     * Requires authentication.
     *
     * @param title     The title of the submission
     * @param text      The text of the submission
     * @param subreddit The subreddit to submit to
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public void submitSelfPost(String title, String text, String subreddit) throws IOException, URISyntaxException {
        redditServices.submit(selfPost().withBody(text).withTitle(title).withSubreddit(subreddit).withModhash(modhash));
    }

    /**
     * Get info about the currently authenticated user.
     * Corresponds to the API "/me.json" method
     *
     * @return UserInfo object containing the user's info, or null if the retrieval fails
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public UserInfo getUserInformation() throws IOException, URISyntaxException {
        if (cookie == null || modhash == null) {
            System.err.printf("Please invoke the connect method in order to login the user");
            return null;
        }

        return redditServices.getUserInfo();
    }

    /**
     * Returns misc info about the user
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @return UserAbout object consisting of information about the user identified by "username".
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public UserAbout about(String username) throws IOException, URISyntaxException {
        return redditServices.getUserAbout(username);
    }

    // TODO: Move comment-related and submission-related methods from the User class

    /**
     * Returns a list of submissions made by this user.
     *
     * @return RedditListing of submissions made by this user.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<CommentListingItem> comments() throws IOException, URISyntaxException {
        return comments(username);
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose comments you want to retrieve.
     * @return RedditListing of submissions made by user specified.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<CommentListingItem> comments(String username) throws IOException, URISyntaxException {
        return comments(username, NEW);
    }

    /**
     * Returns a list of comments made by this user.
     *
     * @param username The username of the user whose comments you want
     *                 to retrieve.
     * @return RedditListing of top 500 comments made by this user.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<CommentListingItem> comments(String username, CommentSortType commentSort) throws IOException, URISyntaxException {
        return redditServices.getUserComments(username, commentSort);
    }

    /**
     * Returns a list of submissions made by this user.
     *
     * @param username The username of the user whose submissions you want
     *                 to retrieve.
     * @return RedditListing of submissions made by this user.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> submissions(String username) throws IOException, URISyntaxException {
        return getSubmissions(username, "submissions", SubmissionSortType.HOT);
    }

    /**
     * Returns a list of submissions that this user liked.
     *
     * @return RedditListing of liked links with default sorting.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getLikedSubmissions() throws IOException, URISyntaxException {
        return getLikedSubmissions(SubmissionSortType.HOT);
    }

    /**
     * Returns a list of submissions that this user liked with a Reddit sort
     *
     * @param sort Which sort you'd like to apply
     * @return RedditListing of liked submissions with a Reddit sort
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getLikedSubmissions(SubmissionSortType sort) throws IOException, URISyntaxException {
        return getUserSubmissions("liked", sort);
    }

    /**
     * Returns a list of submissions that this user chose to hide. with the default sorting
     *
     * @return RedditListing of disliked links.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getHiddenSubmissions() throws IOException, URISyntaxException {
        return getHiddenSubmissions(SubmissionSortType.HOT);
    }

    /**
     * Returns a list of Submissions that this user chose to hide with a specified sorting
     *
     * @param sort Which sort you'd like to apply
     * @return RedditListing of hidden Submissions with a Reddit sort
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getHiddenSubmissions(SubmissionSortType sort) throws IOException, URISyntaxException {
        return getUserSubmissions("hidden", sort);
    }

    /**
     * Returns a list of Submissions that the user saved with the default sort
     *
     * @return RedditListing of saved links
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getSavedSubmissions() throws IOException, URISyntaxException {
        return getSavedSubmissions(SubmissionSortType.HOT);
    }

    /**
     * Returns a list of Submissions that the user saved with a specified sorting
     *
     * @param sort Which sort you'd like to apply
     * @return RedditListing of saved links with a Reddit sort
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getSavedSubmissions(SubmissionSortType sort) throws IOException, URISyntaxException {
        return getUserSubmissions("saved", sort);
    }

    /**
     * Returns a list of Submissions that the user submitted with the default Reddit sort
     *
     * @return RedditListing of submitted Submissions
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getSubmissions() throws IOException, URISyntaxException {
        return getSubmissions(SubmissionSortType.HOT);
    }

    /**
     * Returns a list of Submissions that the user submitted with a specified Reddit sort
     *
     * @return RedditListing of submitted Submissions with a specified Reddit sort
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getSubmissions(SubmissionSortType sort) throws IOException, URISyntaxException {
        return getUserSubmissions("submitted", sort);
    }

    /**
     * Returns a list of submissions that this user disliked with the default Reddit sort
     *
     * @return RedditListing of disliked links.
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getDislikedSubmissions() throws IOException, URISyntaxException {
        return getDislikedSubmissions(SubmissionSortType.HOT);
    }

    /**
     * Returns a list of Submissions that this user disliked with a specified Reddit sort
     *
     * @param sort Which sort you'd like to apply
     * @return RedditListing of disliked sorts with a specified sort
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getDislikedSubmissions(SubmissionSortType sort) throws IOException, URISyntaxException {
        return getUserSubmissions("disliked", sort);
    }

    /**
     * Returns a list of the submission overview for this user
     *
     * @return RedditListing submissions
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getUserOverview() throws IOException, URISyntaxException {
        return getUserSubmissions("overview", SubmissionSortType.HOT);
    }

    /**
     * private function used to get submissions that a user interacts with on Reddit
     *
     * @param where place of Submission - see http://www.reddit.com/dev/api#GET_user_{username}_{where}
     * @param sort Which sort you'd like to apply
     *
     * @return RedditListing from the specified location, null if the location is invalid
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    private RedditListing<SubmissionListingItem> getUserSubmissions(String where, SubmissionSortType sort) throws IOException, URISyntaxException {
        return getSubmissions(username, where, sort);
    }

    /**
     * get submissions for a given user, of a given type, with a given sort
     *
     * @param username The username of the user whose account info you want to retrieve.
     * @param where place of Submission - see http://www.reddit.com/dev/api#GET_user_{username}_{where}
     * @param sort Which sort you'd like to apply
     *
     * @return RedditListing from the specified location, null if the location is invalid
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubmissionListingItem> getSubmissions(String username, String where, SubmissionSortType sort) throws IOException, URISyntaxException {
        //valid arguments for where the Submission can come from; should this be an enum?
        //TODO: not all of these JSONs return something that can be processed purely into a Submission class, until those are taken care of, I commented them out
        final Set<String> validLocations = new HashSet<String>(Arrays.asList(/*"overview",*/ "submitted", /*"comments",*/ "liked", "disliked", "hidden", "saved"));

        if (!validLocations.contains(where)) {
            System.err.println(where + " is an invalid location");
            return null;
        }

        return redditServices.getUserSubmissions(username, where, sort);
    }

    /**
     * Returns a list of Subreddits to which the user is subscribed.
     *
     * @return RedditListing of Subreddits
     *
     * @throws URISyntaxException If not a valid url.
     * @throws IOException        If connection fails
     */
    public RedditListing<SubredditListingItem> getSubscribed() throws IOException, URISyntaxException {
        return redditServices.getSubscribed();
    }

    public String getKind() {
        return kind;
    }

    public String getFullName() {
        return fullName;
    }
}
