package com.agir.maidai.service;

import org.springframework.validation.Errors;

import java.util.List;

public interface CrudService<T, ID> {

    List<T> findAll();
    T find(ID id);

    void create(T entity);
    void delete(ID id);
    void update(T entity);
    Errors validateSave(T entity, Errors errors);
}
