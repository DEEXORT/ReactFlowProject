package com.javarush.publisher.repository;

import java.util.Collection;
import java.util.Optional;

public interface Repository<T> {
    Optional<T> save(T entity);
    Optional<T> update(T entity);
    boolean delete(T entity);
    Optional<T> findById(Long id);
    Collection<T> findAll();
    boolean existsById(Long id);
}
