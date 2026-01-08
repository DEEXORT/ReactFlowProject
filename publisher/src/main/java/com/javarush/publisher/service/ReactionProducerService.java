package com.javarush.reactflow.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.reactflow.model.reaction.ReactionEvent;
import com.javarush.reactflow.model.reaction.ReactionRequestTo;
import com.javarush.reactflow.model.reaction.ReactionResponseTo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReactionProducerService {
    public static final String REQUEST_TOPIC = "reaction-in-topic";
    public static final String RESPONSE_TOPIC = "reaction-out-topic";
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;
    private final ConcurrentHashMap<UUID, Exchanger<ReactionEvent>> kafkaCache = new ConcurrentHashMap<>();

    public List<ReactionResponseTo> send(ReactionEvent event) {
        UUID uuid = UUID.randomUUID();
        Exchanger<ReactionEvent> exchanger = new Exchanger<>();
        kafkaCache.put(uuid, exchanger);
        try {
            String keyRecord = getKeyRecord(event, uuid);
            var record = new ProducerRecord<>(
                    REQUEST_TOPIC,
                    keyRecord,
                    mapper.writeValueAsString(event)
            );
            record.headers().add("uuid", uuid.toString().getBytes());
            log.debug("Sending reaction request to {}", event);
            kafkaTemplate.send(record);
            return exchanger.exchange(event, 1, TimeUnit.SECONDS).reactionResponseTos();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing to JSON: ", e);
        } catch (TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error while sending reaction request: ", e);
        }
    }

    private static String getKeyRecord(ReactionEvent event, UUID uuid) {
        String key;
        if (event.operation() == ReactionEvent.Operation.CREATE ||
                event.operation() == ReactionEvent.Operation.UPDATE) {
            key = event.reactionRequestTo().getTopicId().toString();
        } else {
            key = uuid.toString();
        }
        return key;
    }

    @KafkaListener(topics = RESPONSE_TOPIC, groupId = "publisher")
    private void receive(ConsumerRecord<String, String> record) {
        String uuidStr = new String(record.headers().lastHeader("uuid").value());
        UUID uuid = UUID.fromString(uuidStr);
        String payload = record.value();
        try {
            ReactionEvent event = mapper.readValue(payload, ReactionEvent.class);
            Exchanger<ReactionEvent> exchanger = kafkaCache.remove(uuid);
            if (exchanger != null) {
                exchanger.exchange(event, 1, TimeUnit.SECONDS);
            }
            log.debug("Received reaction event: {}", event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing from JSON to ReactionEvent class: ", e);
        } catch (TimeoutException | InterruptedException e) {
            throw new RuntimeException("Error while sending reaction event: ", e);
        }
    }
}
