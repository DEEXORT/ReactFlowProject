package com.javarush.publisher.service;

import com.javarush.publisher.model.reaction.ReactionRequestTo;
import com.javarush.publisher.model.reaction.ReactionResponseTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ReactionRestService implements CrudService<ReactionRequestTo, ReactionResponseTo> {
    private final RestClient restClient;
    public static final ParameterizedTypeReference<List<ReactionResponseTo>> LIST_NOTE_RESPONSE_TO =
            new ParameterizedTypeReference<>() {
            };


    public List<ReactionResponseTo> getAll() {
        return restClient
                .get()
                .retrieve()
                .body(LIST_NOTE_RESPONSE_TO);
    }

    public ReactionResponseTo getById(Long reactionId) {
        return restClient
                .get()
                .uri("/" + reactionId)
                .retrieve()
                .body(ReactionResponseTo.class);
    }

    @Transactional
    public ReactionResponseTo create(ReactionRequestTo dto) {
        return restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(ReactionResponseTo.class);
    }

    @Transactional
    public ReactionResponseTo update(ReactionRequestTo dto) {
        return restClient
                .post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(dto)
                .retrieve()
                .body(ReactionResponseTo.class);
    }

    @Transactional
    public boolean deleteById(Long reactionId) {
        try {
            return restClient
                    .delete()
                    .uri("/" + reactionId)
                    .retrieve()
                    .toBodilessEntity()
                    .getStatusCode()
                    .is2xxSuccessful();
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        }
    }
}
