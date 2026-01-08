package com.javarush.reactflow.exception;

public class TopicAlreadyExistsException extends RuntimeException {
    public TopicAlreadyExistsException(String message) {
        super(message);
    }

    public TopicAlreadyExistsException() {
        super();
    }

    public TopicAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public TopicAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
