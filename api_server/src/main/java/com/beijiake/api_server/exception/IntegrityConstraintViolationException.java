package com.beijiake.api_server.exception;

public class IntegrityConstraintViolationException extends RuntimeException {

    public IntegrityConstraintViolationException(String message) {
        super(message);
    }

    public IntegrityConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }
}
