package com.javarush.publisher.service;

import com.javarush.publisher.exception.TopicNotFoundException;
import com.javarush.publisher.model.topic.Topic;
import com.javarush.publisher.model.writer.Writer;
import com.javarush.publisher.repository.hibernate.TopicHibernateRepository;
import com.javarush.publisher.repository.hibernate.WriterHibernateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("securityService")
@RequiredArgsConstructor
public class SecurityService {
    private final WriterHibernateRepository writerHibernateRepository;
    private final TopicHibernateRepository topicHibernateRepository;

    public boolean isOwner(Long writerId, String username) {
        Writer writer = getWriter(username);
        return writer.getId().equals(writerId);
    }

    private Writer getWriter(String username) {
        return writerHibernateRepository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found"));
    }

    public boolean isTopicOwner(Long topicId, String username) {
        Topic topic = topicHibernateRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException("Topic with id " + topicId + " not found"));
        Writer writer = getWriter(username);
        return topic.getWriter().getId().equals(writer.getId());
    }
}
