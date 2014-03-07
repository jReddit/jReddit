package com.reddit.test;

import com.reddit.utils.TestUtils;
import com.reddit.dev.api.jreddit.message.Message;
import im.goel.jreddit.message.MessageType;
import im.goel.jreddit.message.Messages;
import im.goel.jreddit.user.User;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * Class for testing Message-related methods
 *
 * @author Raul Rene Lepsa
 */
public class MessageTest {

    private static User user = TestUtils.createAndConnectUser();
    private static Messages messages = new Messages();

    @Test
    public void testUnread() {
        // Get all unread messages
        List<Message> allUnread = messages.getMessages(user, Messages.ALL_MESSAGES, MessageType.UNREAD);
        assertNotNull(allUnread);

        // Get one unread messages
        List<Message> unread = messages.getMessages(user, 1, MessageType.UNREAD);
        assertNotNull(unread);

        assertTrue(allUnread.size() >= unread.size());

        System.out.println("--- Printing unread messages ---");
        for (Message m : allUnread) {
            System.out.println(m.getSubject() + " by " + m.getAuthor());
        }
    }

    @Test
    public void testInbox() {
        List<Message> inbox = messages.getMessages(user, 10, MessageType.INBOX);
        assertNotNull(inbox);

        System.out.println("--- Printing inbox messages ---");
        for (Message m : inbox) {
            System.out.println(m.getSubject() + " by " + m.getAuthor());
        }
    }

    @Test
    public void testSent() {
        List<Message> sent = messages.getMessages(user, 10, MessageType.SENT);
        assertNotNull(sent);

        System.out.println("--- Printing sent messages ---");
        for (Message m : sent) {
            System.out.println(m.getSubject() + " by " + m.getAuthor());
        }
    }

    @Test
    public void testCompose() {
        /*
        These default values will, of course, not work.
        In order to test that the composition indeed works, one has to generate a new Captcha and try with the identification
        and letters from the captcha image respectively. This combination cannot be stored as it is valid only once.
        */
        String captcha_iden = "";
        String captcha_value = "";

        assertFalse(messages.compose(user, "jReddittest", "Test Subject", "Test Body", captcha_iden, captcha_value));

    }
}
