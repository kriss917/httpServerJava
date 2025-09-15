package org.example;


import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            var httpServer = new HttpServer(8080);
            httpServer.start();
        } catch (IOException e) {
            System.out.println("Oh shit everything borked");
        }
    }
}