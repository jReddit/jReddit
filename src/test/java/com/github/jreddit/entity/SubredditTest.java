package com.github.jreddit.entity;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.jreddit.testsupport.JsonHelpers;

public class SubredditTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testAllSubredditFields() {
		
		// Field values
		String submit_text_html = null;
		Boolean user_is_banned = null;
		String id = "SubredditID";
		String kind = Kind.SUBREDDIT.value();
		String submit_text = "submit text for subreddit";
		String display_name = "subredditDisplayName";
		String header_img = "http://a.thumbs.redditmedia.com/yyL5sveWcgkCPKbr.png";
		String description_html = "&lt;div&gt;HTML description for subreddit&lt;/d&gt;";
		String title = "SubredditTitle";
		Boolean over18 = false;
		Boolean user_is_moderator = null;
		String header_title = "Header title for subreddit";
		String description = "Description for subreddit";
		String submit_link_label = "Submit link label";
		String accounts_active = null;
		Boolean public_traffic = true;
		JSONArray header_size = JsonHelpers.jsonArrayOf(160, 64);
		long subscribers = 289252;
		String submit_text_label = "Submit text label";
		String name = kind + "_" + id;
		double created = 1201242956.0;
		String url = "/r/" + display_name;
		double created_utc = 1201242956.0;
		Boolean user_is_contributor = null;
		String public_description = "Public description of subreddit";
		long comment_score_hide_mins = 0;
		String subreddit_type = "public";
		String submission_type = "any";
		Boolean user_is_subscriber = null;
		
		// Create JSON Object
        JSONObject data = new JSONObject();
        data.put("submit_text_html", submit_text_html);
        data.put("user_is_banned", user_is_banned);
        data.put("id", id);
        data.put("submit_text", submit_text);
        data.put("display_name", display_name);
        data.put("header_img", header_img);
        data.put("description_html", description_html);
        data.put("title", title);
        data.put("over18", over18);
        data.put("user_is_moderator", user_is_moderator);
        data.put("header_title", header_title);
        data.put("description", description);
        data.put("submit_link_label", submit_link_label);
        data.put("accounts_active", accounts_active);
        data.put("public_traffic", public_traffic);
        data.put("header_size", header_size);
        data.put("subscribers", subscribers);
        data.put("submit_text_label", submit_text_label);
        data.put("name", name);
        data.put("created", created);
        data.put("url", url);
        data.put("created_utc", created_utc);
        data.put("user_is_contributor", user_is_contributor);
        data.put("public_description", public_description);
        data.put("comment_score_hide_mins", comment_score_hide_mins);
        data.put("subreddit_type", subreddit_type);
        data.put("submission_type", submission_type);
        data.put("user_is_subscriber", user_is_subscriber);
        
        // Parse
        Subreddit s = new Subreddit(data);
        
        // Test data fields
        assertEquals(s.getDisplayName(), display_name);
        assertEquals(s.getTitle(), title);
        assertEquals(s.getURL(), url);
        assertEquals(s.getCreated(), created, 0);
        assertEquals(s.getCreatedUTC(), created_utc, 0);
        assertEquals(s.isNSFW(), over18);
        assertEquals(s.getSubscribers(), subscribers);
        assertEquals(s.getDescription(), description);
        assertEquals(s.getSubredditType(), subreddit_type);
        
        // Possible tests to activate:
//        assertEquals(s.getSubmitTextHTML(), submit_text_html);
//        assertEquals(s.isUserBanned(), user_is_banned);
//        assertEquals(s.getSubmitText(), submit_text);
//        assertEquals(s.getHeaderIMG(), header_img);
//        assertEquals(s.getDescriptionHTML(), description_html);
//        assertEquals(s.isUserModerator(), user_is_moderator);
//        assertEquals(s.getHeaderTitle(), header_title);
//        assertEquals(s.getSubmitLinkLabel(), submit_link_label);
//        assertEquals(s.getAccountsActive(), accounts_active);
//        assertEquals(s.getPublicTraffic(), public_traffic);
//        assertEquals(s.getHeaderSize(), header_size);
//        assertEquals(s.getSubmitTextLabel(), submit_text_label);
//        assertEquals(s.isUserContributor(), user_is_contributor);
//        assertEquals(s.getPublicDescription(), public_description);
//        assertEquals(s.getCommentScoreHideMins(), comment_score_hide_mins, 0);
//        assertEquals(s.getSubmissionType(), submission_type);
//        assertEquals(s.isUserSubscriber(), user_is_subscriber);
        
	}
	
}
