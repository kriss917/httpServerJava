package org.example.ResponseBuilders;



import org.example.HttpRequest;
import org.example.HttpResponse;

import java.util.HashMap;
import java.util.Map;

public class DefaultResponseBuilder implements ResponseBuilder {

    @Override
    public HttpResponse build(HttpRequest request) {
        var body = "<html><h1>Welcome to the default response</html></h1>";

        return new HttpResponse(createStatusLine(),
                createResponseHeaders(body), body);
    }

    private String createStatusLine(){
        return "HTTP/1.1 200 OK";
    }

    private Map<String, String> createResponseHeaders(String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "text/html");
        headers.put("Content-Length", String.valueOf(body.length()));
        return headers;
    }
}

