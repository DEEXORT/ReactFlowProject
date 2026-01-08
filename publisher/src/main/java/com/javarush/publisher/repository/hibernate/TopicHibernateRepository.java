package com.javarush.publisher.repository.hibernate;

import com.javarush.publisher.model.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicHibernateRepository extends JpaRepository<Topic, Long> {
    boolean existsTopicByTitle(String title);
}
