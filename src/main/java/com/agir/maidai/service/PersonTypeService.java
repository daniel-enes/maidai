package com.agir.maidai.service;

import com.agir.maidai.entity.PersonType;
import com.agir.maidai.repository.PersonTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonTypeService {

    private PersonTypeRepository personTypeRepository;

    public PersonTypeService(PersonTypeRepository personTypeRepository) {
        this.personTypeRepository = personTypeRepository;
    }

    public List<PersonType> findAll() {
        return personTypeRepository.findAll();
    }
}
