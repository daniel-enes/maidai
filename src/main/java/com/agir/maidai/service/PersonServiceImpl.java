package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import com.agir.maidai.repository.PersonRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public class PersonServiceImpl extends AbstractCrudService<Person, Integer> implements PersonService{

    protected PersonServiceImpl(PersonRepository personRepository) {
        super(personRepository);
    }
}
