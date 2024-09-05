package org.apache.coyote.http11.handler;

import java.io.IOException;
import java.util.Map;
import com.techcourse.db.InMemoryUserRepository;
import com.techcourse.model.User;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginHandler extends AbstractRequestHandler {

    private static final Logger log = LoggerFactory.getLogger(LoginHandler.class);

    @Override
    public void doGet(HttpRequest request, HttpResponse response) throws IOException {
        response.setResponseBodyFile(request);

        response.write();
    }

    @Override
    public void doPost(HttpRequest request, HttpResponse response) throws IOException {
        Map<String, String> queryString = request.getQueryString();

        if (queryString.containsKey("account") && queryString.containsKey("password")) {
            String account = queryString.get("account");
            String password = queryString.get("password");
            try {
                User user = InMemoryUserRepository.findByAccount(account)
                        .filter(it -> it.checkPassword(password))
                        .orElseThrow(() -> new IllegalArgumentException("로그인 실패"));

                log.debug("user: {}", user);
                response.sendRedirect("/index.html");
            } catch (IllegalArgumentException e) {
                response.sendRedirect("/401.html");
            }
        } else {
            response.sendRedirect("/404.html");
        }
    }
}
