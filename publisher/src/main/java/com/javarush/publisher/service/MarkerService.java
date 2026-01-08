package com.javarush.publisher.service;

import com.javarush.publisher.exception.MarkerAlreadyExistsException;
import com.javarush.publisher.exception.MarkerNotFoundException;
import com.javarush.publisher.mapper.MarkerDto;
import com.javarush.publisher.model.marker.Marker;
import com.javarush.publisher.model.marker.MarkerRequestTo;
import com.javarush.publisher.model.marker.MarkerResponseTo;
import com.javarush.publisher.repository.hibernate.MarkerHibernateRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MarkerService {
    private final MarkerHibernateRepository repository;
    private final MarkerDto mapper;

    @Transactional
    public MarkerResponseTo create(MarkerRequestTo dto) {
        if (repository.existsByName(dto.getName())) {
            throw new MarkerAlreadyExistsException("Marker with name " + dto.getName() + " already exists");
        }
        Marker marker = repository.save(mapper.fromDto(dto));
        return mapper.toDto(marker);
    }

    public MarkerResponseTo getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new MarkerNotFoundException("Marker not found"));
    }

    public Collection<MarkerResponseTo> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Transactional
    public MarkerResponseTo update(MarkerRequestTo dto) {
        Marker marker = repository.save(mapper.fromDto(dto));
        return mapper.toDto(marker);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
