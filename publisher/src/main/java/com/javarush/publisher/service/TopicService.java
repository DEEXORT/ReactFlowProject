package com.javarush.publisher.service;

import com.javarush.publisher.exception.TopicAlreadyExistsException;
import com.javarush.publisher.exception.TopicNotFoundException;
import com.javarush.publisher.exception.WriterNotFoundException;
import com.javarush.publisher.mapper.TopicDto;
import com.javarush.publisher.model.topic.Topic;
import com.javarush.publisher.model.topic.TopicRequestTo;
import com.javarush.publisher.model.topic.TopicResponseTo;
import com.javarush.publisher.repository.hibernate.TopicHibernateRepository;
import com.javarush.publisher.repository.hibernate.WriterHibernateRepository;
import com.javarush.publisher.util.ResourceBundleManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TopicService {
    private TopicHibernateRepository topicRepository;
    private WriterHibernateRepository writerRepository;
    private TopicDto mapper;

    public TopicResponseTo getById(Long topicId) {
        return topicRepository
                .findById(topicId)
                .map(mapper::toDto)
                .orElseThrow(() -> new TopicNotFoundException("Topic not found"));
    }

    public List<TopicResponseTo> getAll() {
        return topicRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public TopicResponseTo create(TopicRequestTo topicRequestTo) {
        if (writerRepository.findById(topicRequestTo.getWriterId()).isEmpty()) {
            throw new WriterNotFoundException(ResourceBundleManager.BUNDLE_MESSAGES.getString("exception.writer_not_found"));
        }
        if (topicRepository.existsTopicByTitle(topicRequestTo.getTitle())) {
            throw new TopicAlreadyExistsException(ResourceBundleManager.BUNDLE_MESSAGES.getString("exception.topic_already_exists"));
        }
        Topic topic = topicRepository.save(mapper.fromDto(topicRequestTo));
        return mapper.toDto(topic);
    }

    @Transactional
    public TopicResponseTo update(TopicRequestTo topicRequestTo) {
        Topic topic = topicRepository.save(mapper.fromDto(topicRequestTo));
        return mapper.toDto(topic);
    }

    @Transactional
    public boolean delete(Long topicId) {
        if (topicRepository.existsById(topicId)) {
            topicRepository.deleteById(topicId);
            return true;
        }
        return false;
    }
}
