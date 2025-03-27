package com.agir.maidai.service;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> getAll();
    T find(ID id);
    void create(T entity);
    void delete(ID id);
    void update(T entity);
}
