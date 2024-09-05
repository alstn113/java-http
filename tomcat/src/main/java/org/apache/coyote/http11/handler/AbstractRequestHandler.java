package org.apache.coyote.http11.handler;

import java.io.IOException;
import org.apache.coyote.http11.HttpMethod;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;

public abstract class AbstractRequestHandler implements RequestHandler {

    @Override
    public void handle(HttpRequest request, HttpResponse response) throws IOException {
        if (request.isSameMethod(HttpMethod.GET)) {
            doGet(request, response);
        } else if (request.isSameMethod(HttpMethod.POST)) {
            doPost(request, response);
        }
    }

    protected void doPost(HttpRequest request, HttpResponse response) throws IOException {
    }

    protected void doGet(HttpRequest request, HttpResponse response) throws IOException {
    }
}
