package com.github.jreddit.action;

import com.github.jreddit.entity.User;
import com.github.jreddit.utils.restclient.HttpRestClient;
import com.github.jreddit.utils.restclient.Response;
import com.github.jreddit.utils.restclient.RestClient;
import com.github.jreddit.utils.restclient.RestResponse;
import examples.Authentication;
import org.apache.http.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

/**
 * Created by Ryan on 04/01/2015.
 *
 * @author Ryan
 * @since 04/01/2015
 */
public class FlairActionsTest {

    private FlairActions subject;
    private Response response;
    private Response desiredResponse;

    /**
     * Mock depended classes and stub if necessary.
     *
     * @throws Exception
     */
    @Before
    public void setup() throws Exception {
        RestClient restClient = new HttpRestClient();
        restClient.setUserAgent("FlairTesterClass/1.0 by name");

        User user = new User(restClient, Authentication.getUsername(), Authentication.getPassword());
        try {
            user.connect();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ParseException e1) {
            e1.printStackTrace();
        }

        desiredResponse = new RestResponse("{\"json\": {\"errors\": []}}", (JSONObject) JSONValue.parse("{\"json\": {\"errors\": []}}"), mock(HttpResponse.class));
        subject = new FlairActions(restClient, user);
    }

    /**
     * Succesfully get user information.
     */
    @Test
    public void testSetUserFlair() {
        response = subject.flair("red", null, "Tridentac", "Blueprinter", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testSetLinkFlair() {
        response = subject.flair("red", "t3_2r86db", null, "Blueprinter", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testRemoveUserFlair() {
        response = subject.deleteFlair("Tridentac", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testRemoveUserFlairTemplates() {
        response = subject.clearFlairTemplates("USER_FLAIR", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testRemoveLinkFlairTemplates() {
        response = subject.clearFlairTemplates("LINK_FLAIR", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testSetFlairConfigs() {
        response = subject.flairConfig(true, "left", false, "right", true, "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testAddFlairTemplate() {
        response = subject.flairTemplate("blueteam", "BLUE-TAG", "USER_FLAIR", "Blue Team", false, "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testSelectFlair() {
        response = subject.selectFlair("BLUE-TAG", null, "Vitineth", "Blue team", "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }

    /**
     * Successfully get the information about a user.
     */
    @Test
    public void testSetFlairEnabled() {
        response = subject.setFlairEnabled(true, "myblueprints");
        assertNotNull(response);
        assertEquals(desiredResponse.getResponseText(), response.getResponseText());
        assertEquals(desiredResponse.getResponseObject(), response.getResponseObject());
    }


}
