package com.javarush.publisher.service;

import com.javarush.publisher.exception.WriterAlreadyExistsException;
import com.javarush.publisher.exception.WriterNotFoundException;
import com.javarush.publisher.mapper.WriterDto;
import com.javarush.publisher.model.writer.Writer;
import com.javarush.publisher.model.writer.WriterRequestTo;
import com.javarush.publisher.model.writer.WriterResponseTo;
import com.javarush.publisher.repository.hibernate.WriterHibernateRepository;
import com.javarush.publisher.util.ResourceBundleManager;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class WriterService {
    private WriterHibernateRepository repository;
    private WriterDto mapper;
    private PasswordEncoder passwordEncoder;

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

        encodePassword(writerRequestTo);
        Writer writer = repository.save(mapper.fromDto(writerRequestTo));
        return mapper.toDto(writer);
    }

    private void encodePassword(WriterRequestTo writerRequestTo) {
        writerRequestTo.setPassword(passwordEncoder.encode(writerRequestTo.getPassword()));
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
        encodePassword(writerRequestTo);
        Writer writer = repository.save(mapper.fromDto(writerRequestTo));
        return mapper.toDto(writer);
    }
}
