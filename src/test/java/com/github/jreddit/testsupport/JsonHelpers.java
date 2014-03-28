package com.github.jreddit.testsupport;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import static java.util.Collections.addAll;

@SuppressWarnings("unchecked") //JSONSimple is not great..
public class JsonHelpers {

    public static JSONObject redditListing(JSONObject... children) {
        JSONObject data = new JSONObject();
        data.put("after", null);
        data.put("before", null);
        data.put("children", jsonArrayOf(children));
        data.put("modhash", "");

        JSONObject root = new JSONObject();
        root.put("data", data);
        root.put("kind", "Listing");

        return root;
    }

    public static JSONObject createMessage(String author, String messageId, String parentId, boolean newFlag, boolean wasComment) {
        JSONObject data = new JSONObject();
        data.put("author", author);
        data.put("body", "message body");
        data.put("body_html", "&lt;!-- SC_OFF --&gt;&lt;div class=\"md\"&gt;&lt;p&gt;message body&lt;/p&gt;\n&lt;/div&gt;&lt;!-- SC_ON --&gt;");
        data.put("context", "");
        data.put("created", 1395989716.0);
        data.put("created_utc", 1395989716.0);
        data.put("dest", "destinationUser");
        data.put("first_message", null);
        data.put("first_message_name", null);
        data.put("id", messageId);
        data.put("name", "t4_" + messageId);
        data.put("new", newFlag);
        data.put("parent_id", parentId);
        data.put("replies", "");
        data.put("subject", "TestMessage");
        data.put("subreddit", null);
        data.put("was_comment", wasComment);


        JSONObject message = new JSONObject();
        message.put("data", data);
        message.put("kind", "t4");
        return message;
    }

    public static JSONArray jsonArrayOf(Object... args) {
        JSONArray array = new JSONArray();
        addAll(array, args);
        return array;
    }

    public static JSONArray emptyJsonArray() {
        return new JSONArray();
    }
}
