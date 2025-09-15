package org.example;

import java.io.IOException;
import java.net.ServerSocket;


public class HttpServer {

    private ServerSocket serverSocket;

    public HttpServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() throws IOException {
        while (true) {
            var connection = serverSocket.accept();
            new Thread(new RequestHandler(connection)).start();

        }
    }

}
