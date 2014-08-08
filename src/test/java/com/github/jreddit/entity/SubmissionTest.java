package com.github.jreddit.entity;

import static org.junit.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.github.jreddit.testsupport.JsonHelpers;

/**
 * Class for testing Submission of JSON parsing.
 *
 * @author Simon Kassing
 */
public class SubmissionTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testAllSubmissionFields() {
		
		// Field values
		String kind = Kind.LINK.value();
		String domain = "imgur.com";
		String banned_by = null;
		JSONObject media_embed = JsonHelpers.createMediaEmbedObject();
		String subreddit = "subredditName";
		String selftext_html = "Self text HTML";
		String selftext = "Self text";
		String likes = null;
		Boolean secure_media = null;
		String link_flair_text = null;
		String id = "SubmID";
		Long gilded = (long) 0;
		JSONObject secure_media_embed = new JSONObject();
		Boolean clicked = false;
		Boolean stickied = false;
		String author = "authorName";
		JSONObject media = JsonHelpers.createMediaObject();
		Long score = (long) 613;
		String approved_by = null;
		Boolean over_18 = true;
		Boolean hidden = false;
		String thumbnail = "nsfw";
		String subreddit_id = Kind.SUBREDDIT.value() + "_" + "SubrID";
		Boolean edited = false;
		String link_flair_css_class = null;
		String author_flair_css_class = null;
		Long downs = (long) 0;
		Boolean saved = false;
		Boolean is_self = false;
		String title = "submTitle";
		String permalink = "/r/" + subreddit + "/comments" + id + "/" + title + "/";
		String name = kind + "_" + id;
		Double created = 1405093719.0;
		String url = "http://imgur.com/a/dxHTq";
		String author_flair_text = null;
		Double created_utc = 1405064919.0;
		Long ups = (long) 613;
		Long num_comments = (long) 112;
		Boolean visited = false;
		Long num_reports = null;
		String distinguished = null;
				
		// Create JSON Object
        JSONObject data = new JSONObject();
        data.put("kind", kind);
        data.put("domain", domain);
        data.put("banned_by", banned_by);
        data.put("media_embed", media_embed);
        data.put("subreddit", subreddit);
        data.put("selftext_html", selftext_html);
        data.put("selftext", selftext);
        data.put("likes", likes);
        data.put("secure_media", secure_media);
        data.put("link_flair_text", link_flair_text);
        data.put("id", id);
        data.put("gilded", gilded);
        data.put("secure_media_embed", secure_media_embed);
        data.put("clicked", clicked);
        data.put("stickied", stickied);
        data.put("author", author);
        data.put("media", media);
        data.put("score", score);
        data.put("approved_by", approved_by);
        data.put("over_18", over_18);
        data.put("hidden", hidden);
        data.put("thumbnail", thumbnail);
        data.put("subreddit_id", subreddit_id);
        data.put("edited", edited);
        data.put("link_flair_css_class", link_flair_css_class);
        data.put("author_flair_css_class", author_flair_css_class);
        data.put("downs", downs);
        data.put("saved", saved);
        data.put("is_self", is_self);
        data.put("title", title);
        data.put("permalink", permalink);
        data.put("name", name);
        data.put("created", created);
        data.put("url", url);
        data.put("author_flair_text", author_flair_text);
        data.put("created_utc", created_utc);
        data.put("ups", ups);
        data.put("num_comments", num_comments);
        data.put("visited", visited);
        data.put("num_reports", num_reports);
        data.put("distinguished", distinguished);
        
        // Parse
        Submission s = new Submission(data);
        
        // Test data fields
        assertEquals(s.getKind(), kind);
        assertEquals(s.getDomain(), domain);
        assertEquals(s.getBannedBy(), banned_by);
        //assertEquals(s.getMediaEmbed(), media_embed);
        assertEquals(s.getSubreddit(), subreddit);
        assertEquals(s.getSelftextHTML(), selftext_html);
        assertEquals(s.getSelftext(), selftext);
        //assertEquals(s.getLikes(), likes);
        //assertEquals(s.getSecureMedia(), secure_media);
        //assertEquals(s.getLinkFlairText(), link_flair_text);
        assertEquals(s.getIdentifier(), id);
        assertEquals(s.getGilded(), gilded);
        //assertEquals(s.getSecureMediaEmbed(), secure_media_embed);
        assertEquals(s.isClicked(), clicked);
        assertEquals(s.isStickied(), stickied);
        assertEquals(s.getAuthor(), author);
        //assertEquals(s.getMedia(), media);
        assertEquals(s.getScore(), score);
        assertEquals(s.getApprovedBy(), approved_by);
        assertEquals(s.isNSFW(), over_18);
        assertEquals(s.isHidden(), hidden);
        assertEquals(s.getThumbnail(), thumbnail);
        assertEquals(s.getSubredditId(), subreddit_id);
        assertEquals(s.isEdited(), edited);
        //assertEquals(s.getLinkFlairCSSClass(), link_flair_css_class);
        //assertEquals(s.getAuthorFlairCSSClass(), author_flair_css_class);
        assertEquals(s.getDownVotes(), downs);
        assertEquals(s.isSaved(), saved);
        assertEquals(s.isSelf(), is_self);
        assertEquals(s.getTitle(), title);
        assertEquals(s.getPermalink(), permalink);
        assertEquals(s.getFullName(), name);
        assertEquals(s.getCreated(), created, 0);
        assertEquals(s.getURL(), url);
        //assertEquals(s.getAuthorFlairText(), author_flair_text);
        assertEquals(s.getCreatedUTC(), created_utc, 0);
        assertEquals(s.getUpVotes(), ups);
        assertEquals(s.getCommentCount(), num_comments);
        assertEquals(s.isVisited(), visited);
        assertEquals(s.getReportCount(), num_reports);//assertEquals(s.getDistinguished(), distinguished);
        
	}
	
}