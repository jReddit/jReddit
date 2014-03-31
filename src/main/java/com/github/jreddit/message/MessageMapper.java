package com.github.jreddit.message;

import org.json.simple.JSONObject;

/**
 * Map a JSON response message to a Message class
 *
 * @author Raul Rene Lepsa
 */
public class MessageMapper {

    /**
     * Map a JSON object to a <code>Message</code> class
     *
     * @param jsonObject JSON object to map
     * @return <code>Message</code> object instance, or NULL if an error occurs during mapping
     */
    public static Message mapMessage(JSONObject jsonObject) {
        Message message = null;

        try {
            message = new Message();
            message.setBody(jsonObject.get("body").toString());
            message.setComment(Boolean.valueOf(jsonObject.get("was_comment").toString()));
            message.setFullName(jsonObject.get("name").toString());
            if (jsonObject.get("author") == null)
            {
                message.setAuthor("reddit");
            } else {
                message.setAuthor(jsonObject.get("author").toString());
            }
            message.setCreated(jsonObject.get("created").toString());
            message.setRecipient(jsonObject.get("dest").toString());
            message.setCreatedUTC(jsonObject.get("created_utc").toString());
            message.setBodyHtml(jsonObject.get("body_html").toString());
            message.setSubject(jsonObject.get("subject").toString());
            message.setContext(jsonObject.get("context").toString());
            message.setId(jsonObject.get("id").toString());
        } catch (Exception e) {
            System.err.println("Error mapping JSON object to a Message class");
        }

        return message;
    }
}
