package com.javarush.discussion.reaction;

import com.javarush.discussion.reaction.model.ReactionRequestTo;
import com.javarush.discussion.reaction.model.ReactionResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1.0/reactions")
@RequiredArgsConstructor
@Slf4j
public class ReactionController {
    private final ReactionService reactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReactionResponseTo createReaction(@RequestBody ReactionRequestTo reactionRequestTo, Locale locale) {
        ReactionResponseTo response;
        if (reactionRequestTo.getId() == null) {
            response = reactionService.create(reactionRequestTo, locale);
            log.debug("Created reaction: {}", response);
        } else {
            response = reactionService.update(reactionRequestTo, locale);
            log.debug("Updated reaction: {}", response);
        }
        return response;
    }

    @GetMapping
    public List<ReactionResponseTo> getReactions() {
        return reactionService.getAll();
    }

    @GetMapping("/{id}")
    public ReactionResponseTo getReaction(@PathVariable long id, Locale locale) {
        return reactionService.getById(id, locale);
    }

    @PutMapping("/{id}")
    public ReactionResponseTo updateReaction(@PathVariable(required = false) Long id,
                                             @RequestBody ReactionRequestTo reactionRequestTo,
                                             Locale locale) {
        log.debug("updateReaction(id in path: {}, request body: {})", id, reactionRequestTo);
        if (id != null) {
            reactionRequestTo.setId(id);
        } else if (reactionRequestTo.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Reaction ID must be provided in path or request body");
        }
        ReactionResponseTo response = reactionService.update(reactionRequestTo, locale);
        log.debug("Reaction updated: {}", response);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReaction(@PathVariable long id, Locale locale) {
        boolean isSuccess = reactionService.deleteById(id, locale);
        if (isSuccess) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
