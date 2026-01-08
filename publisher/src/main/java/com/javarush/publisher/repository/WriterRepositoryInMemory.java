package com.javarush.reactflow.repository;

import com.javarush.reactflow.model.writer.Writer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class WriterRepositoryInMemory implements Repository<Writer> {
    Map<Long, Writer> writers = new HashMap<>();
    AtomicLong id = new AtomicLong();

    @Override
    public Optional<Writer> save(Writer entity) {
        entity.setId(id.incrementAndGet());
        writers.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public Optional<Writer> update(Writer entity) {
        writers.put(entity.getId(), entity);
        return Optional.of(entity);
    }

    @Override
    public boolean delete(Writer entity) {
        if (writers.containsKey(entity.getId())) {
            writers.remove(entity.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Writer> findById(Long id) {
        return Optional.ofNullable(writers.get(id));
    }

    @Override
    public Collection<Writer> findAll() {
        return writers.values();
    }

    public Optional<Writer> find(Writer writer) {
        return writers.values().stream()
                .filter(w -> Objects.equals(w.getLogin(), writer.getLogin()))
                .filter(w -> Objects.equals(w.getPassword(), writer.getPassword()))
                .filter(w -> Objects.equals(w.getFirstname(), writer.getFirstname()))
                .filter(w -> Objects.equals(w.getLastname(), writer.getLastname()))
                .findFirst();
    }

    @Override
    public boolean existsById(Long id) {
        return writers.containsKey(id);
    }
}
