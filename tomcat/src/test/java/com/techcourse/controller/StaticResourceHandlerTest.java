package com.techcourse.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import com.techcourse.servlet.DispatcherServlet;
import com.techcourse.servlet.RequestMapping;
import org.apache.catalina.servlet.Servlet;
import org.apache.catalina.servlet.ServletContainer;
import org.apache.coyote.http11.Http11Processor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.StubSocket;

class StaticResourceHandlerTest {

    private final RequestMapping requestMapping = new RequestMapping(Map.of());
    private final List<Servlet> servlet = List.of(new DispatcherServlet(requestMapping));
    private final ServletContainer servletContainer = ServletContainer.init(servlet);

    @Test
    @DisplayName("GET '/index,html' 요청에 대한 응답이 정상적으로 처리된다.")
    void index() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /index.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/index.html");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/html;charset=utf-8 \r\n" +
                          "Content-Length: 5515 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/index' 요청에 대한 응답은 처리되지 않고, '/404.html'로 리다이렉트한다.")
    void index_redirect() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /index HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/404.html");
        String expected = String.join("\r\n",
                "HTTP/1.1 404 Not Found ",
                "Content-Type: text/html;charset=utf-8 ",
                "Content-Length: 2426 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())
                ));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/login.html' 요청에 대한 응답이 정상적으로 처리된다.")
    void login() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /login.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/login.html");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/html;charset=utf-8 \r\n" +
                          "Content-Length: 3797 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/css/styles.css' 요청에 대한 응답이 정상적으로 처리된다.")
    void css() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /css/styles.css HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/css/styles.css");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/css;charset=utf-8 \r\n" +
                          "Content-Length: 211991 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/js/scripts.js' 요청에 대한 응답이 정상적으로 처리된다.")
    void js() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /js/scripts.js HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/js/scripts.js");
        String expected = String.join("\r\n",
                "HTTP/1.1 200 OK ",
                "Content-Type: text/javascript;charset=utf-8 ",
                "Content-Length: 976 ",
                "",
                new String(Files.readAllBytes(new File(resource.getFile()).toPath())
                ));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/error-404-monochrome.svg' 요청에 대한 응답이 정상적으로 처리된다.")
    void svg() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /assets/img/error-404-monochrome.svg HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/assets/img/error-404-monochrome.svg");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: image/svg+xml;charset=utf-8 \r\n" +
                          "Content-Length: 6119 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/400.html' 요청에 대한 응답이 정상적으로 처리된다.")
    void badRequest() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /400.html HTTP/1.1 ",
                "Host: localhost:2519 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/400.html");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/html;charset=utf-8 \r\n" +
                          "Content-Length: 2059 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/401.html' 요청에 대한 응답이 정상적으로 처리된다.")
    void unauthorized() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /401.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/401.html");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/html;charset=utf-8 \r\n" +
                          "Content-Length: 2426 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }

    @Test
    @DisplayName("GET '/404.html' 요청에 대한 응답이 정상적으로 처리된다.")
    void notFound() throws IOException {
        // given
        String httpRequest = String.join("\r\n",
                "GET /404.html HTTP/1.1 ",
                "Host: localhost:8080 ",
                "Connection: keep-alive ",
                "",
                "");

        StubSocket socket = new StubSocket(httpRequest);
        Http11Processor processor = new Http11Processor(servletContainer, socket);

        // when
        processor.process(socket);

        // then
        URL resource = getClass().getClassLoader().getResource("static/404.html");
        String expected = "HTTP/1.1 200 OK \r\n" +
                          "Content-Type: text/html;charset=utf-8 \r\n" +
                          "Content-Length: 2426 \r\n" +
                          "\r\n" +
                          new String(Files.readAllBytes(new File(resource.getFile()).toPath()));

        assertThat(socket.output()).isEqualTo(expected);
    }
}
