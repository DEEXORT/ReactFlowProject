package com.javarush.reactflow.exception;

public class WriterAlreadyExistsException extends RuntimeException {
    public WriterAlreadyExistsException(String message) {
        super(message);
    }
}
