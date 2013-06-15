package im.goel.jreddit.MessageTest;
import im.goel.jreddit.message.Messages;
import im.goel.jreddit.user.User;



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
