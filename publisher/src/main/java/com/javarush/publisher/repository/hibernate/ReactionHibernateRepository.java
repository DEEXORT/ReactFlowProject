package com.javarush.publisher.repository.hibernate;

import com.javarush.publisher.model.reaction.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionHibernateRepository extends JpaRepository<Reaction, Long> {
}
