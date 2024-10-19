package com.dangelic.realtimesyncserver.exception;

public class ClientRequestValidationException extends RuntimeException {
    public ClientRequestValidationException(String message) {
        super(message);
    }
}
