package com.javarush.publisher.controller.v1;

import com.javarush.publisher.model.reaction.ReactionRequestTo;
import com.javarush.publisher.model.reaction.ReactionResponseTo;
import com.javarush.publisher.service.CrudService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1.0/reactions")
@AllArgsConstructor
@Slf4j
public class ReactionControllerV1 {
    private final CrudService<ReactionRequestTo, ReactionResponseTo> reactionService;

    @GetMapping
    public List<ReactionResponseTo> getReactions() {
        return reactionService.getAll();
    }

    @GetMapping("/{id}")
    public ReactionResponseTo getReactionById(@PathVariable Long id) {
        return reactionService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReactionResponseTo createReaction(@Valid @RequestBody ReactionRequestTo reaction) {
        log.debug("createReaction(request body: {})", reaction);
        return reactionService.create(reaction);
    }

    @PutMapping({"", "/{id}"})
    public ReactionResponseTo updateReaction(@PathVariable(required = false) Long id, @RequestBody ReactionRequestTo reactionRequestTo) {
        log.debug("updateReaction(id in path: {}, request body: {})", id, reactionRequestTo);
        if (id != null) {
            reactionRequestTo.setId(id);
        } else if (reactionRequestTo.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Reaction ID must be provided in path or request body");
        }
        return reactionService.update(reactionRequestTo);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReactionById(@PathVariable Long id) {
        boolean success = reactionService.deleteById(id);
        if (!success) {
            log.error("Failed to delete reaction with id: {}", id);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reaction not found");
        }
    }
}
