package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PersonService extends CrudService<Person, Integer> {

    List<Person> findAllScholarshipHolders();
    Page<Person> findByPersonType(Integer typeId, Pageable pageable);
    void addPersonToPpg(Integer personId, Integer ppgId);
    void removePersonFromPpg(Integer personId, Integer ppgId);
}
