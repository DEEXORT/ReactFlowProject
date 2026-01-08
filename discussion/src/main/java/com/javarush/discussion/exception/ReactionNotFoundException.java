package com.javarush.discussion.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
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
