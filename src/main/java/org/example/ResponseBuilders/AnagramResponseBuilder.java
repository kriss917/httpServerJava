package org.example.ResponseBuilders;


import org.example.AnagramFinder;
import org.example.HttpRequest;
import org.example.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnagramResponseBuilder implements ResponseBuilder {

    @Override
    public HttpResponse build(HttpRequest request) {
        List<String> anagrams;
        try {
            anagrams = new AnagramFinder().findAnagrams(request.getUrlParam());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var statusLine = createStatusLine();
        var body = createJsonString(anagrams);
        var headers = createResponseHeaders(body);
        return new HttpResponse(statusLine, headers, body);
    }

    private String createStatusLine(){
        return "HTTP/1.1 200 OK";
    };

    private Map<String, String> createResponseHeaders(String body) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Content-Length", String.valueOf(body.length()));
        return headers;
    }

    private String createJsonString(List<String> body) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"anagrams\":[");
        body.forEach(b -> {
            sb.append("\"").append(b).append("\"").append(",");
        });
        sb.deleteCharAt(sb.length() - 1);
        sb.append("]}");
        return sb.toString();
    }
}

