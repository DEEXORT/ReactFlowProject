package com.javarush.reactflow.service;

import com.javarush.reactflow.exception.WriterAlreadyExistsException;
import com.javarush.reactflow.exception.WriterNotFoundException;
import com.javarush.reactflow.mapper.WriterDto;
import com.javarush.reactflow.model.writer.Writer;
import com.javarush.reactflow.model.writer.WriterRequestTo;
import com.javarush.reactflow.model.writer.WriterResponseTo;
import com.javarush.reactflow.repository.hibernate.WriterHibernateRepository;
import com.javarush.reactflow.util.ResourceBundleManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WriterService {
    private WriterHibernateRepository repository;
    private WriterDto mapper;

    public Collection<WriterResponseTo> getAll() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    public WriterResponseTo getById(Long id) {
        return repository.findById(id).map(mapper::toDto)
                .orElseThrow(() -> new WriterNotFoundException("Writer not found"));
    }

    @Transactional
    public WriterResponseTo create(WriterRequestTo writerRequestTo) {
        if (repository.existsWriterByLogin(writerRequestTo.getLogin())) {
            throw new WriterAlreadyExistsException(ResourceBundleManager.BUNDLE_MESSAGES.getString("exception.writer_already_exists"));
        }
        Writer writer = repository.save(mapper.fromDto(writerRequestTo));
        return mapper.toDto(writer);
    }

    @Transactional
    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.delete(Writer.builder().id(id).build());
            return true;
        }
        return false;
    }

    public WriterResponseTo find(WriterRequestTo writerRequestTo) {
        return repository
                .findOne(Example.of(mapper.fromDto(writerRequestTo)))
                .map(mapper::toDto)
                .orElseThrow(() -> new WriterNotFoundException("Writer not found"));
    }

    @Transactional
    public WriterResponseTo update(WriterRequestTo writerRequestTo) {
        Writer writer = repository.save(mapper.fromDto(writerRequestTo));
        return mapper.toDto(writer);
    }
}
