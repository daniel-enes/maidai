package com.agir.maidai.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Map;

public interface CrudService<T, ID> {

    List<T> findAll();
    Page<T> findAll(Pageable pageable);
    Page<T> findAll(Pageable pageable, Map<String, String> filters);

    T find(ID id);

    void create(T entity);
    void delete(ID id);
    void update(T entity);

    void validateSave(T entity, Errors errors);
}
