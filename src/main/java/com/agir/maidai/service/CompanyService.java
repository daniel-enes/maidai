package com.agir.maidai.service;

import com.agir.maidai.entity.Company;

import java.util.List;

public interface CompanyService {

    List<Company> getAll();

    Company find(int id);

    void create(Company company);

    void delete(int id);
}
