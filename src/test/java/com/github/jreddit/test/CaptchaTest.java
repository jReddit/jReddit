package com.github.jreddit.test;

import com.github.jreddit.utils.TestUtils;
import com.github.jreddit.captcha.Captcha;
import com.github.jreddit.user.User;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Class for testing the Captcha methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
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
