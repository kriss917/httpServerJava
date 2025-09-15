package org.example.ResponseBuilders;


import org.example.HttpRequest;
import org.example.HttpResponse;

public interface ResponseBuilder {

        HttpResponse build(HttpRequest request);
    }

