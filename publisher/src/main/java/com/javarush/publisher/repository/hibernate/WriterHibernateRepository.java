package com.javarush.publisher.repository.hibernate;

import com.javarush.publisher.model.writer.Writer;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WriterHibernateRepository extends JpaRepository<Writer, Long> {
    boolean existsWriterByLogin(@Size(min = 2, max = 64) String login);
    Optional<Writer> findByLogin(@Size(min = 2, max = 64) String login);
}
