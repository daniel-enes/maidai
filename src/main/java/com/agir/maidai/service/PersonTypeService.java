package com.agir.maidai.service;

import com.agir.maidai.entity.PersonType;

import java.util.List;

public interface PersonTypeService {

    List<PersonType> getAll();

    PersonType find(int id);

    void create(PersonType personType);

    void delete(int id);
}
