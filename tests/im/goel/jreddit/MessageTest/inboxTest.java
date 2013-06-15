package im.goel.jreddit.MessageTest;
import im.goel.jreddit.message.Message;
import im.goel.jreddit.message.Messages;
import im.goel.jreddit.user.User;

import java.util.List;




public class inboxTest {

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
		
		List<Message> unread = new Messages().inbox(user, 10);

		for (Message m : unread) {
			System.out.println(m.getSubject() + " by " + m.getAuthor());
		}
	}

}
