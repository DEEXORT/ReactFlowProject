package com.javarush.reactflow.exception;

public class ReactionNotFoundException extends RuntimeException {
    public ReactionNotFoundException(String message) {
        super(message);
    }

    public ReactionNotFoundException() {
        super();
    }

    public ReactionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ReactionNotFoundException(Throwable cause) {
        super(cause);
    }
}
