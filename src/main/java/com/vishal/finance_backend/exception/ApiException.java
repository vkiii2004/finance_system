package com.vishal.finance_backend.exception;

import org.springframework.http.HttpStatus;

/**
 * Central exception type for API errors with an explicit HTTP status.
 */
public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

