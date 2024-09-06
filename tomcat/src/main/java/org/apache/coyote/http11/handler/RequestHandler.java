package org.apache.coyote.http11.handler;

import org.apache.coyote.http11.HttpRequest;
import org.apache.coyote.http11.HttpResponse;

public interface RequestHandler {

    void handle(HttpRequest httpRequest, HttpResponse httpResponse);
}
