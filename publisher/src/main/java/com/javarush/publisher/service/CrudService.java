package com.javarush.reactflow.service;

import java.util.List;

public interface CrudService<Q, R> {
    R create(Q query);
    R update(Q query);
    boolean deleteById(Long id);
    R getById(Long id);
    List<R> getAll();
}
