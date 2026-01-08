package com.javarush.reactflow.repository;

import com.javarush.reactflow.model.marker.Marker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class MarkerRepositoryInMemory implements Repository<Marker> {
    private final Map<Long, Marker> markers = new ConcurrentHashMap<>();
    private AtomicLong nextId = new AtomicLong(0);

    @Override
    public Optional<Marker> save(Marker marker) {
        marker.setId(nextId.getAndIncrement());
        markers.put(marker.getId(), marker);
        return Optional.ofNullable(marker);
    }

    @Override
    public Optional<Marker> update(Marker marker) {
        markers.put(marker.getId(), marker);
        return Optional.of(marker);
    }

    @Override
    public boolean delete(Marker marker) {
        if (markers.containsKey(marker.getId())) {
            markers.remove(marker.getId());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<Marker> findById(Long id) {
        return Optional.ofNullable(markers.get(id));
    }

    @Override
    public Collection<Marker> findAll() {
        return markers.values();
    }

    @Override
    public boolean existsById(Long id) {
        return markers.containsKey(id);
    }
}
