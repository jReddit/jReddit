package com.github.jreddit.utils.restclient;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.jreddit.exception.RetrievalFailedException;

/**
 * Test the HTTP Rest Client.
 *
 * @author Simon Kassing
 */
@RunWith(MockitoJUnitRunner.class)
public class HttpRestClientTest {

	@Mock private HttpClient mockHttpClient;
	@Mock private ResponseHandler<Response> mockResponseHandler;
	private String mockCookie;
	private HttpRestClient subject;
	
	@Before
	public void setup() {
		subject = new HttpRestClient(mockHttpClient, mockResponseHandler);
		mockCookie = "";
		//verify(mockCookie, times(10)).charAt(0);
		//assertTrue(false);
	}
	
	@Test(expected=RetrievalFailedException.class)
	public void testIncorrectSyntax() {
		subject.get("jkldsja8989&*&*&(98989s89f89s89/\\/// %39;22090", mockCookie);
	}
	
	@Test(expected=RetrievalFailedException.class)
	public void testInvalidURI() {
		subject.get("/this/is/not/part/of/reddit", mockCookie);
	}
	
}
