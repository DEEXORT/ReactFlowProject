package com.javarush.reactflow.exception;

public class MarkerAlreadyExistsException extends RuntimeException {
    public MarkerAlreadyExistsException(String message) {
        super(message);
    }

    public MarkerAlreadyExistsException() {
        super();
    }

    public MarkerAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarkerAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
