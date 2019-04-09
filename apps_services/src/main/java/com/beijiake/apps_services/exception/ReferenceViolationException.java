package com.beijiake.apps_services.exception;

public class ReferenceViolationException extends RuntimeException {

    public ReferenceViolationException(String message) {
        super(message);
    }

    public ReferenceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
