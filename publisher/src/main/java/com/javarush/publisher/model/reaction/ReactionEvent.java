package com.javarush.reactflow.model.reaction;

import java.util.List;
import java.util.Locale;

public record ReactionEvent(
        Long id,
        Operation operation,
        ReactionRequestTo reactionRequestTo,
        List<ReactionResponseTo> reactionResponseTos,
        Locale locale
        ) {

    public ReactionEvent(Operation operation, Locale locale) {
        this(null, operation, null, null, locale);
    }

    public ReactionEvent(Operation operation, Long id, Locale locale) {
        this(id, operation, null, null, locale);
    }

    public ReactionEvent(Operation operation, ReactionRequestTo reactionRequestTo, Locale locale) {
        this(null, operation, reactionRequestTo, null, locale);
    }

    public ReactionEvent(List<ReactionResponseTo> reactionResponseTos, Locale locale) {
        this(null, null, null, reactionResponseTos, locale);
    }

    public enum Operation {
        CREATE, UPDATE, DELETE_BY_ID, GET_ALL, GET_BY_ID
    }
}
