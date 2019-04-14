package com.beijiake.web.rest;

public class ReferenceViolationException extends RuntimeException {

    public ReferenceViolationException(String message) {
        super(message);
    }

    public ReferenceViolationException(String message, Throwable cause) {
        super(message, cause);
    }

}
