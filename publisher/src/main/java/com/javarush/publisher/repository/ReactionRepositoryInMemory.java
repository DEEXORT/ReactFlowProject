package com.javarush.reactflow.repository;

import com.javarush.reactflow.model.reaction.Reaction;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class ReactionRepositoryInMemory implements Repository<Reaction> {
    private Map<Long, Reaction> reactions = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    @Override
    public Optional<Reaction> save(Reaction reaction) {
        reaction.setId(nextId.getAndIncrement());
        reactions.put(reaction.getId(), reaction);
        return Optional.of(reaction);
    }

    @Override
    public Optional<Reaction> update(Reaction reaction) {
        reactions.put(reaction.getId(), reaction);
        return Optional.of(reaction);
    }

    @Override
    public boolean delete(Reaction reaction) {
        if (reactions.containsKey(reaction.getId())) {
            reactions.remove(reaction.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Reaction> findById(Long id) {
        return Optional.ofNullable(reactions.get(id));
    }

    @Override
    public Collection<Reaction> findAll() {
        return reactions.values();
    }

    @Override
    public boolean existsById(Long id) {
        return reactions.containsKey(id);
    }
}
