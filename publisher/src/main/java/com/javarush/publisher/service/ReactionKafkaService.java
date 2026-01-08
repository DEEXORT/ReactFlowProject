package com.javarush.reactflow.service;

import com.javarush.reactflow.model.reaction.ReactionEvent;
import com.javarush.reactflow.model.reaction.ReactionRequestTo;
import com.javarush.reactflow.model.reaction.ReactionResponseTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Locale;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class ReactionKafkaService implements CrudService<ReactionRequestTo, ReactionResponseTo> {
    private final ReactionProducerService producerService;
    private final RestClient restClient;
    public static final ParameterizedTypeReference<List<ReactionResponseTo>> LIST_NOTE_RESPONSE_TO =
            new ParameterizedTypeReference<>() {
            };


    public List<ReactionResponseTo> getAll() {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.GET_ALL, Locale.getDefault());
        return producerService.send(event);
    }

    @Transactional
    public ReactionResponseTo create(ReactionRequestTo dto) {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.CREATE, dto, Locale.getDefault());
        return producerService.send(event).getFirst();
    }

    public ReactionResponseTo getById(Long reactionId) {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.GET_BY_ID, reactionId, Locale.getDefault());
        return producerService.send(event).getFirst();
    }

    @Transactional
    public ReactionResponseTo update(ReactionRequestTo dto) {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.UPDATE, dto, Locale.getDefault());
        return producerService.send(event).getFirst();
    }

    @Transactional
    public boolean deleteById(Long reactionId) {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.DELETE_BY_ID, reactionId, Locale.getDefault());
        List<ReactionResponseTo> response = producerService.send(event);
        return response.isEmpty();
    }
}
