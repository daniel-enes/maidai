package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService extends CrudService<Person, Integer> {

    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);

//    Page<Person> findByPersonType(Integer typeId, Pageable pageable);
//    List<Person> findByPersonType(Integer typeId);

    Page<Person> findByPersonType(Pageable pageable, String personType);

    void addPersonToPpg(Integer personId, Integer ppgId);
    void removePersonFromPpg(Integer personId, Integer ppgId);

    List<Person> findAllScholarshipHolders();
    List<Person> findAllAdvisors();
}
