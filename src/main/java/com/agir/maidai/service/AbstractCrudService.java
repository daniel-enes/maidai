package com.agir.maidai.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class AbstractCrudService<T, ID> implements CrudService<T, ID> {

    protected final JpaRepository<T, ID> repository;

    protected AbstractCrudService(JpaRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    public T find(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + id));
    }

    @Override
    public void create(T entity) {
        repository.save(entity);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public void update(T entity) {
        repository.save(entity);
    }
}
