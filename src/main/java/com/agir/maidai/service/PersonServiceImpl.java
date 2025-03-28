package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import com.agir.maidai.repository.PersonRepository;


public class PersonServiceImpl extends AbstractCrudService<Person, Integer> implements PersonService{

    private final PersonRepository personRepository;

    protected PersonServiceImpl(PersonRepository personRepository) {
        super(personRepository);
        this.personRepository = personRepository;
    }
}
