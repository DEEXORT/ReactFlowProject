package com.javarush.discussion.reaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.discussion.exception.ReactionNotFoundException;
import com.javarush.discussion.reaction.model.ReactionEvent;
import com.javarush.discussion.reaction.model.ReactionRequestTo;
import com.javarush.discussion.reaction.model.ReactionResponseTo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReactionConsumerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ReactionService reactionService;
    private final ObjectMapper objectMapper;

    public static final String REQUEST_TOPIC = "reaction-in-topic";
    public static final String RESPONSE_TOPIC = "reaction-out-topic";
    public static final String GROUP_ID = "discussion";

    @KafkaListener(topics = REQUEST_TOPIC, groupId = GROUP_ID)
    public void listen(ConsumerRecord<String, String> record) {
        String idMessage = record.key();
        String value = record.value();
        int partition = record.partition();
        Header header = record.headers().lastHeader("uuid");

        log.debug("Kafka consumer received idMessage: {}, value: {}, partition: {}",
                idMessage, value, partition);
        try {
            ReactionEvent event = objectMapper.readValue(value, ReactionEvent.class);
            ReactionRequestTo requestTo = event.reactionRequestTo();
            Locale locale = event.locale();

            List<ReactionResponseTo> response = switch (event.operation()) {
                case CREATE -> List.of(reactionService.create(requestTo, locale));
                case UPDATE -> List.of(reactionService.update(requestTo, locale));
                case DELETE_BY_ID -> {
                    boolean deleted = reactionService.deleteById(event.id(), locale);
                    if (!deleted) {
                        throw new ReactionNotFoundException(idMessage);
                    }
                    yield List.of();
                }
                case GET_ALL -> reactionService.getAll();
                case GET_BY_ID -> List.of(reactionService.getById(event.id(), locale));
            };

            ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                    RESPONSE_TOPIC,
                    idMessage,
                    objectMapper.writeValueAsString(new ReactionEvent(response, locale))
            );
            producerRecord.headers().add(header);

            kafkaTemplate.send(producerRecord);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing reaction request", e);
        } catch (ReactionNotFoundException e) {
            sendErrorNotFoundResponse(idMessage, header);
        }
    }

    private void sendErrorNotFoundResponse(String idMessage, Header header) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(
                RESPONSE_TOPIC,
                idMessage,
                HttpStatus.NOT_FOUND.toString()
        );
        producerRecord.headers().add(header);

        kafkaTemplate.send(producerRecord);
    }
}
