import static org.junit.Assert.*;
import im.goel.jreddit.user.User;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;


public class UserTest {
	@Test
	public void test() {
		// Don't hax meh plz
		User user = new User("jRedditTest", "jRedditTest"); // Enter your username and password here!
		try {
			user.connect();
            System.out.println(user.commentKarma());
		} catch (Exception exception) {
			exception.printStackTrace();
		}


		assertNotNull(
                "The user's modhash should never be null, lest no API methods function properly. (Invoke the connect function to remedy this)",
                user.getModhash());
	}
}