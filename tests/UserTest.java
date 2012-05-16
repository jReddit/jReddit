import static org.junit.Assert.*;

import org.junit.Test;

import com.omrlnr.jreddit.user.User;

public class UserTest {
	@Test
	public void test() {
		// Don't hax meh plz
		User user = new User("test_subject_666", "beef");
		try {
			user.connect();
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		assertNotNull(
				"The user's modhash should never be null, lest no API methods function properly. (Invoke the connect function to remedy this)",
				user.getModhash());
	}
}