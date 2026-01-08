package com.javarush.publisher.service;

import com.javarush.publisher.exception.TopicNotFoundException;
import com.javarush.publisher.model.reaction.ReactionEvent;
import com.javarush.publisher.model.reaction.ReactionRequestTo;
import com.javarush.publisher.model.reaction.ReactionResponseTo;
import com.javarush.publisher.repository.hibernate.TopicHibernateRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Service
@Primary
@AllArgsConstructor
@Slf4j
public class ReactionKafkaService implements CrudService<ReactionRequestTo, ReactionResponseTo> {
    private final TopicHibernateRepository topicHibernateRepository;
    private final ReactionProducerService producerService;

    public List<ReactionResponseTo> getAll() {
        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.GET_ALL, Locale.getDefault());
        return producerService.send(event);
    }

    @Transactional
    public ReactionResponseTo create(ReactionRequestTo dto) {
        if (!topicHibernateRepository.existsById(dto.getTopicId())) {
            throw new TopicNotFoundException("Topic not found with id " + dto.getTopicId());
        }
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
        ReactionResponseTo responseTo = getById(reactionId);
        if (responseTo == null) return false;

        ReactionEvent event = new ReactionEvent(ReactionEvent.Operation.DELETE_BY_ID, reactionId, Locale.getDefault());
        List<ReactionResponseTo> response = producerService.send(event);
        return response.isEmpty();
    }
}
