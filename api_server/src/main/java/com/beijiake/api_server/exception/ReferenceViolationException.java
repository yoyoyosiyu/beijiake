package com.beijiake.api_server.exception;

public class ReferenceViolationException extends RuntimeException {

    public ReferenceViolationException(String message) {
        super(message);
    }

    public ReferenceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
