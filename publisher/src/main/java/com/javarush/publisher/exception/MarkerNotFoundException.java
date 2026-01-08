package com.javarush.reactflow.exception;

public class MarkerNotFoundException extends RuntimeException {
    public MarkerNotFoundException(String message) {
        super(message);
    }

    public MarkerNotFoundException() {
        super();
    }

    public MarkerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public MarkerNotFoundException(Throwable cause) {
        super(cause);
    }
}
