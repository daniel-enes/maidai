package com.agir.maidai.service;

import com.agir.maidai.entity.Person;

import java.util.List;

public interface PersonService extends CrudService<Person, Integer> {

    List<Person> findAllScholarshipHolders();
}
