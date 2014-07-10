package com.github.jreddit.entity;

import org.json.simple.JSONObject;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CommentTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testAllCommentFields() {
		
		// Field values
		String subreddit_id = "SubrID";
		String banned_by = null;
		String subreddit = "SubredditName";
		String likes = null;
		String replies = "";
		boolean saved = false;
		String id = "CommID";
		String kind = "t1";
		long gilded = 0;
		String author = "author";
		String parent_id = "ParID";
		long score = 2;
		String approved_by = null;
		long controversiality = 0;
		String body = "comment body";
		boolean edited = false;
		String author_flair_css_class = null;
		long downs = 0;
		String body_html = "&lt;div&gt;" + body + "&lt;/div&gt;";
		String link_id = "LinkIdentifier";
		boolean score_hidden = false;
		String name = kind + "_" + id;
		double created = 1404969798.0;
		String author_flair_text = null;
		double created_utc = 1404940998.0;
		long ups = 2;
		String num_reports = null;
		String distinguished = null;
		
		// Create JSON Object
        JSONObject data = new JSONObject();
        data.put("subreddit_id", subreddit_id);
        data.put("banned_by", banned_by);
        data.put("subreddit", subreddit);
        data.put("likes", likes);
        data.put("replies", replies);
        data.put("saved", saved);
        data.put("id", id);
        data.put("gilded", gilded);
        data.put("author", author);
        data.put("parent_id", parent_id);
        data.put("score", score);
        data.put("approved_by", approved_by);
        data.put("controversiality", controversiality);
        data.put("body", body);
        data.put("edited", edited);
        data.put("author_flair_css_class", author_flair_css_class);
        data.put("downs", downs);
        data.put("body_html", body_html);
        data.put("link_id", link_id);
        data.put("score_hidden", score_hidden);
        data.put("name", name);
        data.put("created", created);
        data.put("author_flair_text", author_flair_text);
        data.put("created_utc", created_utc);
        data.put("ups", ups);
        data.put("num_reports", num_reports);
        data.put("distinguished", distinguished);
        
        // Parse
        Comment c = new Comment(data);
        
        // Test data fields
        assertEquals(c.getFullName(), name);
        assertEquals(c.getAuthor(), author);
        assertEquals(c.getBody(), body);
        assertEquals(c.getCreated(), created, 0);
        assertEquals(c.getCreatedUTC(), created_utc, 0);
        assertEquals(c.getDownvotes(), downs, 0);
        assertEquals(c.getEdited(), edited);
        assertEquals(c.getGilded(), gilded, 0);
        assertEquals(c.getIdentifier(), id);
        assertEquals(c.getKind(), kind);
        assertEquals(c.getParentId(), parent_id);
        assertEquals(c.getScore(), score, 0);
        assertEquals(c.getUpvotes(), ups, 0);
        assertEquals(c.getSubreddit(), subreddit);
        assertEquals(c.getSubredditId(), subreddit_id);
        assertEquals(c.getLinkId(), link_id);
        assertEquals(c.getBodyHTML(), body_html);
        assertEquals(c.isScoreHidden(), score_hidden);
        
        // Possible tests to activate:
//        assertEquals(c.getBannedBy(), banned_by);
//        assertEquals(c.getLikes(), likes);
//        assertEquals(c.getApprovedBy(), approved_by);
//        assertEquals(c.getAuthorFlairCSSClass(), author_flair_css_class);
//        assertEquals(c.getAuthorFlairText(), author_flair_text);
//        assertEquals(c.getNumReports(), num_reports);
//        assertEquals(c.getDistinguised(), distinguished);
        
	}
	
}
