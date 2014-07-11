package com.github.jreddit.utils.restclient;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class RestResponseHandler implements ResponseHandler<Response> {

    private final JSONParser jsonParser;

    public RestResponseHandler() {
        this.jsonParser = new JSONParser();
    }

    public RestResponseHandler(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    public Response handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        try {
            return parse(response);
        }
        catch (ParseException e) {
            System.err.println("Error parsing response from Reddit");
        }
        return null;
    }

    private Response parse(HttpResponse httpResponse) throws IOException, ParseException {
        InputStream responseStream = httpResponse.getEntity().getContent();
        String content = IOUtils.toString(responseStream, "UTF-8");
        Object responseObject = jsonParser.parse(content);
        return new RestResponse(content, responseObject, httpResponse);
    }
}
