package com.github.jreddit.captcha;

import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.testsupport.UtilResponse;
import com.github.jreddit.utils.restclient.ResponseWithJsonSimple;
import com.github.jreddit.utils.restclient.RestClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static com.github.jreddit.testsupport.JsonHelpers.emptyJsonArray;
import static com.github.jreddit.testsupport.JsonHelpers.jsonArrayOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Class for testing the Captcha methods
 *
 * @author Karan Goel
 * @author Raul Rene Lepsa
 */
public class CaptchaTest {

    private User user;
    private RestClient restClient;
    private ResponseWithJsonSimple response;
    private CaptchaDownloader captchaDownloader;
    private Captcha underTest;

    @Before
    public void setUp() {
        user = mock(User.class);
        restClient = mock(RestClient.class);
        captchaDownloader = mock(CaptchaDownloader.class);
        underTest = new Captcha(restClient, captchaDownloader);
    }

    @Test
    public void newCaptchaHappy() throws IOException, ParseException {
        response = generateNewCaptchaResponseWithIdent("ident");

        when(user.getCookie()).thenReturn("cookie");
        when(restClient.post(null, ApiEndpointUtils.CAPTCHA_NEW, "cookie")).thenReturn(response);

        underTest.newCaptcha(user);
        verify(captchaDownloader).getCaptchaImage("ident");
    }

    @Ignore
    // TODO; NullPointerException thrown
    @Test
    public void newCaptchaUnexpectedJsonStructure() throws IOException, ParseException {
        response = generateBadlyFormedJson();

        when(user.getCookie()).thenReturn("cookie");
        when(restClient.post(null, ApiEndpointUtils.CAPTCHA_NEW, "cookie")).thenReturn(response);

        underTest.newCaptcha(user);
        verify(captchaDownloader).getCaptchaImage("ident");
    }

    @Test
    public void needsCaptchaReturnsTrue() {
        response = new UtilResponse("", true, 200);

        when(user.getCookie()).thenReturn("cookie");
        when(restClient.get(ApiEndpointUtils.CAPTCHA_NEEDS, "cookie")).thenReturn(response);

        assertTrue(underTest.needsCaptcha(user));
    }

    @Test
    public void needsCaptchaReturnsFalse() {
        response = new UtilResponse("", false, 200);

        when(user.getCookie()).thenReturn("cookie");
        when(restClient.get(ApiEndpointUtils.CAPTCHA_NEEDS, "cookie")).thenReturn(response);

        assertFalse(underTest.needsCaptcha(user));
    }

    @Test
    public void needsCaptchaReceivesJSONObjectAndReturnsFalse() {
        response = new UtilResponse("", new JSONObject(Collections.singletonMap("key", "value")), 200);

        when(user.getCookie()).thenReturn("cookie");
        when(restClient.get(ApiEndpointUtils.CAPTCHA_NEEDS, "cookie")).thenReturn(response);

        assertFalse(underTest.needsCaptcha(user));
    }

    private ResponseWithJsonSimple generateBadlyFormedJson() throws ParseException {
        return new UtilResponse("", fooJsonObject(), 200);
    }

    @SuppressWarnings("unchecked") //JSONSimple is not great..
    private JSONObject fooJsonObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(1);
        jsonArray.add(2);

        jsonObject.put("foo", jsonArray);
        jsonObject.put("bar", jsonArray);
        return jsonObject;
    }

    private ResponseWithJsonSimple generateNewCaptchaResponseWithIdent(String ident) throws ParseException {
        JSONObject responseObject = newCaptchaJsonResponseWith(ident);
        return new UtilResponse("", responseObject, 200);
    }

    @SuppressWarnings("unchecked") //JSONSimple is not great..
    private JSONObject newCaptchaJsonResponseWith(String ident) {
        JSONObject root = new JSONObject();
        JSONArray rootArray = jsonArrayOf(
                jsonArrayOf(0, 1, "call", jsonArrayOf("body")),
                jsonArrayOf(1, 2, "attr", "find"),
                jsonArrayOf(2, 3, "call", jsonArrayOf(".status")),
                jsonArrayOf(3, 4, "attr", "hide"),
                jsonArrayOf(4, 5, "call", emptyJsonArray()),
                jsonArrayOf(5, 6, "attr", "html"),
                jsonArrayOf(6, 7, "call", jsonArrayOf("")),
                jsonArrayOf(7, 8, "attr", "end"),
                jsonArrayOf(8, 9, "call", emptyJsonArray()),
                jsonArrayOf(0, 10, "call", jsonArrayOf("body")),
                jsonArrayOf(10, 11, "attr", "captcha"),
                jsonArrayOf(11, 12, "call", jsonArrayOf(ident))
        );

        root.put("jquery", rootArray);
        return root;
    }
}
