package org.apache.coyote.http11.response;


public class StatusLine {

    private static final String HTTP_VERSION = "HTTP/1.1";

    private final String httpVersion;
    private HttpStatus status;

    public StatusLine() {
        this(HTTP_VERSION, HttpStatus.OK);
    }

    public StatusLine(String httpVersion, HttpStatus status) {
        this.httpVersion = httpVersion;
        this.status = status;
    }

    public String buildStatusLineResponse() {
        return String.format("%s %d %s ", httpVersion, status.getCode(), status.getMessage());
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.status = httpStatus;
    }
}
