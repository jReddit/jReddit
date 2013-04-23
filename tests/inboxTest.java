import java.util.List;

import message.Message;
import message.Messages;

import com.omrlnr.jreddit.user.User;


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
