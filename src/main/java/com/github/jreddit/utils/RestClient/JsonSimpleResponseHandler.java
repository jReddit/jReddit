package com.github.jreddit.utils.RestClient;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;

public class JsonSimpleResponseHandler implements ResponseHandler<Object> {

    private JSONParser jsonParser;

    public JsonSimpleResponseHandler() {
        this.jsonParser = new JSONParser();
    }

    public JsonSimpleResponseHandler(JSONParser jsonParser) {
        this.jsonParser = jsonParser;
    }

    @Override
    public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        try {
            return parse(response);
        }
        catch (ParseException e) {
            System.err.println("Error parsing response from Reddit");
        }
        return null;
    }

    private Object parse(HttpResponse httpResponse) throws IOException, ParseException {
        InputStream responseStream = httpResponse.getEntity().getContent();
        String content = IOUtils.toString(responseStream, "UTF-8");
        return jsonParser.parse(content);
    }
}
