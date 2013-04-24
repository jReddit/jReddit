package im.goel.jreddit.MessageTest;
import java.util.List;

import message.Message;
import message.Messages;

import com.omrlnr.jreddit.user.User;


public class SentMessageTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		User user = null;

		try {
			user = new User("jReddittest", "jReddittest"); // Add your username and password
			user.connect();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		
		List<Message> unread = new Messages().sent(user, 10);

		for (Message m : unread) {
			System.out.println(m.getSubject() + " by " + m.getAuthor());
		}
	}

}
