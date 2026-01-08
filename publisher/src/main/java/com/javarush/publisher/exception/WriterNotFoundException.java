package com.javarush.publisher.exception;

public class WriterNotFoundException extends RuntimeException {
    public WriterNotFoundException(String message) {
        super(message);
    }

    public WriterNotFoundException() {
        super();
    }

    public WriterNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WriterNotFoundException(Throwable cause) {
        super(cause);
    }
}
