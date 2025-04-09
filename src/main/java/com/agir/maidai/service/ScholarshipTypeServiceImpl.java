package com.agir.maidai.service;

import com.agir.maidai.entity.ScholarshipType;
import com.agir.maidai.repository.ScholarshipTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScholarshipTypeServiceImpl implements ScholarshipTypeService{

    final private ScholarshipTypeRepository scholarshipTypeRepository;

    @Autowired
    protected ScholarshipTypeServiceImpl(ScholarshipTypeRepository scholarshipTypeRepository) {
        this.scholarshipTypeRepository = scholarshipTypeRepository;
    }

    @Override
    public List<ScholarshipType> findAll() {
        return scholarshipTypeRepository.findAll();
    }

    @Override
    public ScholarshipType find(Integer integer) {
        return scholarshipTypeRepository.findById(integer).orElseThrow(() -> new EntityNotFoundException("ID não encontrado para Scholarship: " + integer));
    }

    @Override
    public void create(ScholarshipType entity) {
        validateSave(entity);
        scholarshipTypeRepository.save(entity);
    }

    @Override
    public void delete(Integer integer) {
        scholarshipTypeRepository.deleteById(integer);
    }

    @Override
    public void update(ScholarshipType entity) {
        validateSave(entity);
        scholarshipTypeRepository.save(entity);
    }

    public void validateSave(ScholarshipType entity) {
        String trimmedType = entity.getType().trim();
        entity.setType(trimmedType);
        if(scholarshipTypeRepository.existsByType(entity.getType())) {
            throw new IllegalArgumentException("Esse tipo de bolsa já existe. Escolha outro nome.");
        }
    }
}
