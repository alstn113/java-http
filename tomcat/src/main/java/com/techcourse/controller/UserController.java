package com.techcourse.controller;

import java.io.IOException;
import com.techcourse.model.User;
import com.techcourse.servlet.AbstractController;
import org.apache.catalina.session.Session;
import org.apache.coyote.http11.common.HttpHeaders;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.HttpStatus;

public class UserController extends AbstractController {

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        Session session = request.getSession(true);

        if (loggedIn(response, session)) return;

        notLoggedIn(response);
    }

    private boolean loggedIn(HttpResponse response, Session session) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            response.setResponseBody("User: " + user);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain;charset=utf-8");
            response.write();
            return true;
        }
        return false;
    }

    private void notLoggedIn(HttpResponse response) {
        response.setResponseBody("로그인 상태가 아닙니다.");
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "text/plain;charset=utf-8");
        response.write();
    }
}
