package com.javarush.reactflow.repository.hibernate;

import com.javarush.reactflow.model.writer.Writer;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WriterHibernateRepository extends JpaRepository<Writer, Long> {
    boolean existsWriterByLogin(@Size(min = 2, max = 64) String login);
}
