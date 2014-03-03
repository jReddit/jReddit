package im.goel.jreddit.MessageTest;

import com.reddit.test.TestUtils;
import im.goel.jreddit.captcha.Captcha;
import im.goel.jreddit.user.User;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;


public class CaptchaTest {

    private static Captcha captcha = new Captcha();

    @Test
    public void testNewCaptcha() {
        User user = TestUtils.createAndConnectUser();
        if (user != null) {
            String captchaIden = captcha.newCaptcha(user);
            assertNotNull(captchaIden);
        }
    }

    @Test
    public void testNeedsCaptcha() {
        User user = TestUtils.createAndConnectUser();
        if (user != null) {
            boolean needsCaptcha = captcha.needsCaptcha(user);

            // The test user needs a captcha. Change assert condition accordingly for other users
            assertTrue(needsCaptcha);
        }
    }
}
