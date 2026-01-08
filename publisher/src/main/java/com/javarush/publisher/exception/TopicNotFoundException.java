package com.javarush.reactflow.exception;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(String message) {
        super(message);
    }

    public TopicNotFoundException() {
        super();
    }

    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicNotFoundException(Throwable cause) {
        super(cause);
    }
}
