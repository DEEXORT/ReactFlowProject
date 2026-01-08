package com.javarush.reactflow.repository;

import com.javarush.reactflow.model.topic.Topic;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TopicRepositoryInMemory implements Repository<Topic> {
    private final Map<Long, Topic> topics = new HashMap<>();
    private final AtomicLong nextId = new AtomicLong(0);

    @Override
    public Optional<Topic> save(Topic topic) {
        topic.setId(nextId.getAndIncrement());
        topics.put(topic.getId(), topic);
        return Optional.of(topic);
    }

    @Override
    public Optional<Topic> update(Topic topic) {
        topics.put(topic.getId(), topic);
        return Optional.of(topic);
    }

    @Override
    public boolean delete(Topic topic) {
        if (topics.containsKey(topic.getId())) {
            topics.remove(topic.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return Optional.ofNullable(topics.get(id));
    }

    @Override
    public Collection<Topic> findAll() {
        return topics.values();
    }

    @Override
    public boolean existsById(Long id) {
        return topics.containsKey(id);
    }
}
