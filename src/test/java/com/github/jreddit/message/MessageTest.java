package com.github.jreddit.message;

import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.entity.User;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static com.github.jreddit.message.MessageType.*;
import static com.github.jreddit.message.Messages.ALL_MESSAGES;
import static com.github.jreddit.testsupport.JsonHelpers.*;
import static com.github.jreddit.utils.ApiEndpointUtils.MESSAGE_GET;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class for testing Message-related methods
 *
 * @author Tom Beresford
 */
public class MessageTest {

    private static final String COOKIE = "cookie";
    private static final String MOD_HASH = "modHash";
    private static final String CAPTCHA_TEXT = "captchaText";
    private static final String CAPTCHA_IDEN = "captcha_iden";
    private static final String TEST_SUBJECT = "Test Subject";
    private static final String TEST_BODY = "Test Body";
    private static final String DESTINATION_USER = "Destination User";

    private User user;
    private RestClient restClient;
    private Messages underTest;

    @Before
    public void setUp() {
        user = mock(User.class);
        restClient = mock(RestClient.class);
        underTest = new Messages(restClient);

        when(user.getCookie()).thenReturn(COOKIE);
        when(user.getModhash()).thenReturn(MOD_HASH);
    }

    @Test
    public void getUnreadMessages() {
        Response response = new UtilResponse("", someUnreadDirectMessage(), 200);

        when(restClient.get(String.format(MESSAGE_GET, UNREAD.getValue()), COOKIE)).thenReturn(response);

        List<Message> allUnread = underTest.getMessages(user, ALL_MESSAGES, UNREAD);
        assertNotNull(allUnread);
        assertTrue(allUnread.size() == 2);
    }

    @Test
    public void getInboxMessages() {
        Response response = new UtilResponse("", allDirectMessage(), 200);

        when(restClient.get(String.format(MESSAGE_GET, SENT.getValue()), COOKIE)).thenReturn(response);

        List<Message> allUnread = underTest.getMessages(user, ALL_MESSAGES, SENT);
        assertNotNull(allUnread);
        assertTrue(allUnread.size() == 3);
    }

    @Test
    public void getSentMessages() {
        Response response = new UtilResponse("", someSentMessages(), 200);

        when(restClient.get(String.format(MESSAGE_GET, INBOX.getValue()), COOKIE)).thenReturn(response);

        List<Message> allUnread = underTest.getMessages(user, ALL_MESSAGES, INBOX);
        assertNotNull(allUnread);
        assertTrue(allUnread.size() == 2);
    }

    @Test
    public void successfullySendMessage() {
        Response response = new UtilResponse("", messageSendSuccessResponse(), 200);

        when(restClient.post("captcha=" + CAPTCHA_TEXT + "&iden=" + CAPTCHA_IDEN +
                        "&subject=" + TEST_SUBJECT + "&text=" + TEST_BODY + "&to=" + DESTINATION_USER +
                        "&uh=" + user.getModhash(),
                        ApiEndpointUtils.MESSAGE_COMPOSE, user.getCookie())
        ).thenReturn(response);

        assertTrue(underTest.compose(user, DESTINATION_USER, TEST_SUBJECT, TEST_BODY, CAPTCHA_IDEN, CAPTCHA_TEXT));
    }

    @Test
    public void failWithLongSubject() {
        assertFalse(underTest.compose(user, DESTINATION_USER, veryLongSubject(), TEST_BODY, CAPTCHA_IDEN, CAPTCHA_TEXT));
    }

    @Test
    public void markMessageAsRead() {
        underTest.readMessage("messageName", user);

        verify(restClient).post("id=" + "messageName" + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
    }

    @Test
    public void markMessageAsUnread() {
        underTest.readMessage("messageName", user);

        verify(restClient).post("id=" + "messageName" + "&uh=" + user.getModhash(), ApiEndpointUtils.MESSAGE_READ, user.getCookie());
    }


    private String veryLongSubject() {
        return StringUtils.rightPad("Title", 100, '*');
    }

    @SuppressWarnings("unchecked") //JSONSimple is not great..
    private JSONObject messageSendSuccessResponse() {
        JSONObject root = new JSONObject();
        JSONArray rootArray = jsonArrayOf(
                jsonArrayOf(0, 1, "call", jsonArrayOf("#compose-message")),
                jsonArrayOf(1, 2, "attr", "find"),
                jsonArrayOf(2, 3, "call", jsonArrayOf(".status")),
                jsonArrayOf(3, 4, "attr", "hide"),
                jsonArrayOf(4, 5, "call", emptyJsonArray()),
                jsonArrayOf(5, 6, "attr", "html"),
                jsonArrayOf(6, 7, "call", jsonArrayOf("")),
                jsonArrayOf(7, 8, "attr", "end"),
                jsonArrayOf(8, 9, "call", emptyJsonArray()),
                jsonArrayOf(1, 10, "attr", "find"),
                jsonArrayOf(10, 11, "call", jsonArrayOf(".status")),
                jsonArrayOf(11, 12, "attr", "show"),
                jsonArrayOf(12, 13, "call", emptyJsonArray()),
                jsonArrayOf(13, 14, "attr", "html"),
                jsonArrayOf(14, 15, "call", jsonArrayOf("your message has been delivered")),
                jsonArrayOf(15, 16, "attr", "end"),
                jsonArrayOf(16, 17, "call", emptyJsonArray()),
                jsonArrayOf(1, 18, "attr", "find"),
                jsonArrayOf(18, 19, "call", jsonArrayOf("*[name=captcha]")),
                jsonArrayOf(19, 20, "attr", "attr"),
                jsonArrayOf(20, 21, "call", jsonArrayOf("value", "")),
                jsonArrayOf(21, 22, "attr", "end"),
                jsonArrayOf(22, 23, "call", emptyJsonArray()),
                jsonArrayOf(1, 24, "attr", "find"),
                jsonArrayOf(24, 25, "call", jsonArrayOf("*[name=to]")),
                jsonArrayOf(25, 26, "attr", "attr"),
                jsonArrayOf(26, 27, "call", jsonArrayOf("value", "")),
                jsonArrayOf(27, 28, "attr", "end"),
                jsonArrayOf(28, 29, "call", emptyJsonArray()),
                jsonArrayOf(1, 30, "attr", "find"),
                jsonArrayOf(30, 31, "call", jsonArrayOf("*[name=text]")),
                jsonArrayOf(31, 32, "attr", "attr"),
                jsonArrayOf(32, 33, "call", jsonArrayOf("value", "")),
                jsonArrayOf(33, 34, "attr", "end"),
                jsonArrayOf(34, 35, "call", emptyJsonArray()),
                jsonArrayOf(1, 36, "attr", "find"),
                jsonArrayOf(36, 37, "call", jsonArrayOf("*[name=subject]")),
                jsonArrayOf(37, 38, "attr", "attr"),
                jsonArrayOf(38, 39, "call", jsonArrayOf("value", "")),
                jsonArrayOf(39, 40, "attr", "end"),
                jsonArrayOf(40, 41, "call", emptyJsonArray())
        );

        root.put("jquery", rootArray);
        return root;
    }

    private JSONObject someSentMessages() {
        JSONObject message = createMessage("ConnectedUser", "messageId", null, false, false);
        JSONObject message1 = createMessage("ConnectedUser", "messageId1", null, false, false);
        return redditListing(message, message1);
    }

    private JSONObject allDirectMessage() {
        JSONObject message = createMessage("SomeUser", "messageId", null, true, false);
        JSONObject message1 = createMessage("SomeUser", "messageId1", null, true, false);
        JSONObject message2 = createMessage("SomeUser", "messageId1", null, false, false);
        return redditListing(message, message1, message2);
    }

    private JSONObject someUnreadDirectMessage() {
        JSONObject message = createMessage("SomeUser", "messageId", null, true, false);
        JSONObject message1 = createMessage("SomeUser", "messageId1", null, true, false);
        return redditListing(message, message1);
    }
}
