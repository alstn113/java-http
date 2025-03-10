package org.apache.coyote.http11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import com.techcourse.exception.UncheckedServletException;
import org.apache.catalina.servlet.ServletContainer;
import org.apache.coyote.Processor;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.HttpRequestFactory;
import org.apache.coyote.http11.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Http11Processor implements Runnable, Processor {

    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    private final ServletContainer servletContainer;
    private final Socket connection;

    public Http11Processor(ServletContainer servletContainer, Socket connection) {
        this.servletContainer = servletContainer;
        this.connection = connection;
    }

    @Override
    public void run() {
        log.info("connect host: {}, port: {}", connection.getInetAddress(), connection.getPort());
        process(connection);
    }

    @Override
    public void process(Socket connection) {
        try (
                InputStream inputStream = connection.getInputStream();
                OutputStream outputStream = connection.getOutputStream()
        ) {
            HttpRequest httpRequest = HttpRequestFactory.create(inputStream);
            HttpResponse httpResponse = HttpResponse.ok(outputStream);

            servletContainer.service(httpRequest, httpResponse);
        } catch (IOException | UncheckedServletException e) {
            log.error(e.getMessage(), e);
        }
    }
}
