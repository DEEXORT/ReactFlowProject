package com.javarush.reactflow.repository.hibernate;

import com.javarush.reactflow.model.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicHibernateRepository extends JpaRepository<Topic, Long> {
    boolean existsTopicByTitle(String title);
}
