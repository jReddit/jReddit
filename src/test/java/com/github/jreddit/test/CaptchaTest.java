package com.github.jreddit.test;

import com.github.jreddit.captcha.Captcha;
import com.github.jreddit.captcha.CaptchaDownloader;
import com.github.jreddit.user.User;
import com.github.jreddit.utils.ApiEndpointUtils;
import com.github.jreddit.utils.RestClient.Response;
import com.github.jreddit.utils.RestClient.RestClient;
import com.github.jreddit.utils.UtilResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
        JSONObject responseObject = (JSONObject) new JSONParser().parse("{\"foo\": [0, 1 ], \"bar\": [0, 1 ] }");
        return new UtilResponse("", responseObject, 200);
    }

    private Response generateNewCaptchaResponseWithIdent(String ident) throws ParseException {
        JSONObject responseObject = (JSONObject) new JSONParser().parse(jsonStringWithIdent(ident));
        return new UtilResponse("", responseObject, 200);
    }

    private String jsonStringWithIdent(String ident) {
        return String.format("{\n" +
                "    \"jquery\": [\n" +
                "        [\n" +
                "            0,\n" +
                "            1,\n" +
                "            \"call\",\n" +
                "            [\n" +
                "                \"body\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        [\n" +
                "            1,\n" +
                "            2,\n" +
                "            \"attr\",\n" +
                "            \"find\"\n" +
                "        ],\n" +
                "        [\n" +
                "            2,\n" +
                "            3,\n" +
                "            \"call\",\n" +
                "            [\n" +
                "                \".status\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        [\n" +
                "            3,\n" +
                "            4,\n" +
                "            \"attr\",\n" +
                "            \"hide\"\n" +
                "        ],\n" +
                "        [\n" +
                "            4,\n" +
                "            5,\n" +
                "            \"call\",\n" +
                "            []\n" +
                "        ],\n" +
                "        [\n" +
                "            5,\n" +
                "            6,\n" +
                "            \"attr\",\n" +
                "            \"html\"\n" +
                "        ],\n" +
                "        [\n" +
                "            6,\n" +
                "            7,\n" +
                "            \"call\",\n" +
                "            [\n" +
                "                \"\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        [\n" +
                "            7,\n" +
                "            8,\n" +
                "            \"attr\",\n" +
                "            \"end\"\n" +
                "        ],\n" +
                "        [\n" +
                "            8,\n" +
                "            9,\n" +
                "            \"call\",\n" +
                "            []\n" +
                "        ],\n" +
                "        [\n" +
                "            0,\n" +
                "            10,\n" +
                "            \"call\",\n" +
                "            [\n" +
                "                \"body\"\n" +
                "            ]\n" +
                "        ],\n" +
                "        [\n" +
                "            10,\n" +
                "            11,\n" +
                "            \"attr\",\n" +
                "            \"captcha\"\n" +
                "        ],\n" +
                "        [\n" +
                "            11,\n" +
                "            12,\n" +
                "            \"call\",\n" +
                "            [\n" +
                "                \"%s\"\n" +
                "            ]\n" +
                "        ]\n" +
                "    ]\n" +
                "}", ident);
    }
}
