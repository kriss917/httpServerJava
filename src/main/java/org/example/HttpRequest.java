package org.example;

import java.util.Map;

public class HttpRequest {

    private String method;
    private String url;
    private String path;
    private String urlParam;
    private String version;
    private Map<String, String> headers;
    private String body;

    public HttpRequest(String requestLine, Map<String, String> headers, String body) {
        var requestElements = requestLine.split(" ");
        this.method = requestElements[0];
        this.url = requestElements[1];
        this.path = parsePath(requestElements[1]);
        this.urlParam = parseUrlParam(requestElements[1]);
        this.version = requestElements[2];
        this.headers = headers;
        this.body = body;
    }

    private String parsePath(String url) {
        if (!url.contains("?")) return url;
        return url.split("\\?")[0];
    }

    private String parseUrlParam(String url) {
        if (!url.contains("=")) return url;
        var params = url.split("\\?")[1];
        return params.split("=")[1];
    }

    public String getPath() {
        return path;
    }

    public String getUrlParam() {
        return urlParam;
    }
}

