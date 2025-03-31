package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import com.agir.maidai.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends AbstractCrudService<Person, Integer> implements PersonService{

    private final PersonRepository personRepository;

    @Autowired
    protected PersonServiceImpl(PersonRepository personRepository) {
        super(personRepository);
        this.personRepository = personRepository;
    }
}
