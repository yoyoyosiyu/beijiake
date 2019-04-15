package com.beijiake.api_server;

import com.beijiake.api_server.exception.IntegrityConstraintViolationException;
import com.beijiake.api_server.exception.ReferenceViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;


@ControllerAdvice
public class GlobalControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleEntityNotFound(HttpServletRequest request, Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.error = "Entity Not Found";
        errorMessage.message = ex.getMessage();
        errorMessage.path = request.getRequestURI();
        errorMessage.status = HttpStatus.NOT_FOUND.value();

        logger.error(String.format("Entity not found: %s for path %s", ex.getMessage(), request.getRequestURI()));

        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ReferenceViolationException.class)
    public ResponseEntity<ErrorMessage> handleReferenceViolation(HttpServletRequest request, Exception ex) {

        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.error = "Reference Violation";
        errorMessage.message = ex.getMessage();
        errorMessage.path = request.getRequestURI();
        errorMessage.status = HttpStatus.BAD_REQUEST.value();

        logger.error(String.format("Reference Violation: %s for path %s", ex.getMessage(), request.getRequestURI()));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IntegrityConstraintViolationException.class)
    public ResponseEntity<ErrorMessage> handleSqlIntegrityConstraintViolation(HttpServletRequest request, Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage();

        errorMessage.error = "Integrity Constraint Violation";
        errorMessage.message = ex.getMessage();
        errorMessage.path = request.getRequestURI();
        errorMessage.status = HttpStatus.BAD_REQUEST.value();

        logger.error(String.format("Integrity Constraint Violation: %s for path %s", ex.getMessage(), request.getRequestURI()));

        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    static class ErrorMessage {
        public Date timestamp;
        public Integer status;
        public String error;
        public String message;
        public String path;

        ErrorMessage() {
            this.timestamp = new Date();
        }

    }
}
