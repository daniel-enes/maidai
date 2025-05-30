package com.agir.maidai.service;

import com.agir.maidai.entity.ScholarshipType;
import com.agir.maidai.repository.ScholarshipTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class ScholarshipTypeServiceImpl extends AbstractCrudService<ScholarshipType, Integer> implements ScholarshipTypeService{

    final private ScholarshipTypeRepository scholarshipTypeRepository;

    @Autowired
    protected ScholarshipTypeServiceImpl(ScholarshipTypeRepository scholarshipTypeRepository) {
        super(scholarshipTypeRepository);
        this.scholarshipTypeRepository = scholarshipTypeRepository;
    }

    @Override
    public void validateSave(ScholarshipType scholarshipType, Errors errors) {

        String trimmedName = scholarshipType.getType().trim();
        scholarshipType.setType(trimmedName);

        Optional<ScholarshipType> scholarshipTypeWithSameName = scholarshipTypeRepository.findByType(trimmedName);

        if(scholarshipTypeWithSameName.isPresent()) {
            if(scholarshipType.getId() == null || !scholarshipTypeWithSameName.get().getId().equals(scholarshipType.getId())) {
                errors.rejectValue("type", "type.duplicated","Esse tipo de bolsa já existe.");
            }
        }
    }

}
