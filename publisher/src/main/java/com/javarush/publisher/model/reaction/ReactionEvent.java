package com.javarush.publisher.model.reaction;

import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Locale;

public record ReactionEvent(
        Long id,
        Operation operation,
        ReactionRequestTo reactionRequestTo,
        List<ReactionResponseTo> reactionResponseTos,
        Locale locale,
        HttpStatus status
        ) {

    public enum Operation {
        CREATE, UPDATE, DELETE_BY_ID, GET_ALL, GET_BY_ID
    }

    public ReactionEvent(Operation operation, Locale locale) {
        this(null, operation, null, null, locale, null);
    }

    public ReactionEvent(Operation operation, Long id, Locale locale) {
        this(id, operation, null, null, locale, null);
    }

    public ReactionEvent(Operation operation, ReactionRequestTo reactionRequestTo, Locale locale) {
        this(null, operation, reactionRequestTo, null, locale, null);
    }

    public ReactionEvent(List<ReactionResponseTo> reactionResponseTos, Locale locale) {
        this(null, null, null, reactionResponseTos, locale, null);
    }

    public static ReactionEvent errorStatus(HttpStatus status) {
        return new ReactionEvent(null, null, null, null, null, status);
    }
}
