package com.github.jreddit.test;

import com.github.jreddit.captcha.Captcha;
import com.github.jreddit.captcha.CaptchaDownloader;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.RestClient.Response;
import com.github.jreddit.utils.RestClient.RestClient;
import com.github.jreddit.utils.UtilResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

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
    private Response response;
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

    private Response generateBadlyFormedJson() throws ParseException {
        return new UtilResponse("", fooJsonObject(), 200);
    }

    private JSONObject fooJsonObject() {
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(1);
        jsonArray.add(2);

        jsonObject.put("foo", jsonArray);
        jsonObject.put("bar", jsonArray);
        return jsonObject;
    }

    private Response generateNewCaptchaResponseWithIdent(String ident) throws ParseException {
        JSONObject responseObject = newCaptchaJsonResponseWith(ident);
        return new UtilResponse("", responseObject, 200);
    }

    private JSONObject newCaptchaJsonResponseWith(String ident) {
        JSONObject root = new JSONObject();
        JSONArray rootArray = jsonArrayBuilder(
                jsonArrayBuilder(0, 1, "call", singleElementJsonArray("body")),
                jsonArrayBuilder(1, 2, "attr", "find"),
                jsonArrayBuilder(2, 3, "call", singleElementJsonArray(".status")),
                jsonArrayBuilder(3, 4, "attr", "hide"),
                jsonArrayBuilder(4, 5, "call", new JSONArray()),
                jsonArrayBuilder(5, 6, "attr", "html"),
                jsonArrayBuilder(6, 7, "call", singleElementJsonArray("")),
                jsonArrayBuilder(7, 8, "attr", "end"),
                jsonArrayBuilder(8, 9, "call", new JSONArray()),
                jsonArrayBuilder(0, 10, "call", singleElementJsonArray("body")),
                jsonArrayBuilder(10, 11, "attr", "captcha"),
                jsonArrayBuilder(11, 12, "call", singleElementJsonArray(ident))
        );

        root.put("jquery", rootArray);
        return root;
    }

    private JSONArray jsonArrayBuilder(Object... args) {
        JSONArray array = new JSONArray();
        for (Object o : args) {
            array.add(o);
        }
        return array;
    }

    private JSONArray singleElementJsonArray(String value) {
        JSONArray firstChildsChildArray = new JSONArray();
        firstChildsChildArray.add(value);
        return firstChildsChildArray;
    }
}
