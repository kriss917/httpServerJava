package org.example;

import org.example.ResponseBuilders.AnagramResponseBuilder;
import org.example.ResponseBuilders.DefaultResponseBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RequestHandler implements Runnable {

    private final BufferedReader in;
    private final PrintWriter out;
    private final String crlf = "\r\n";

    public RequestHandler(Socket socket) throws IOException {
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    @Override
    public void run() {
        HttpResponse response = null;
        try {
            response = handleRequest();
            sendResponse(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpResponse handleRequest() throws IOException {

        var request = new HttpRequest(
                parseRequestLine(),
                parseHeaders(),
                null
        );
        return switch (request.getPath()) {
            case "/anagram" -> new AnagramResponseBuilder().build(request);
            default -> new DefaultResponseBuilder().build(request);
        };
    }

    private String parseRequestLine() throws IOException {
        return in.readLine();
    }

    private Map<String, String> parseHeaders() throws IOException {
        Map<String, String> headers = new HashMap<>();
        var line = in.readLine();
        while (!line.isEmpty()) {
            var headerParts = line.split(":");
            headers.put(headerParts[0], headerParts[1]);
            line = in.readLine();
        }
        return headers;
    }

    private void sendResponse(HttpResponse response) {
        out.print(response.getStatusLine() + crlf);
        response.getHeaders().forEach((key, value) -> out.print(key + ": " + value + crlf));
        out.print(crlf);
        out.print(response.getBody());
        out.flush();
        out.close();
    }
}

