package im.goel.jreddit.MessageTest;
import message.Messages;

import com.omrlnr.jreddit.user.User;


public class ComposeTest {

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
		
		new Messages().compose(user, "jReddittest", "Test", "Testing 123", "", "");
	}

}
