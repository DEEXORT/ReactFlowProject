package com.javarush.publisher.exception;

public class WriterAlreadyExistsException extends RuntimeException {
    public WriterAlreadyExistsException(String message) {
        super(message);
    }
}
