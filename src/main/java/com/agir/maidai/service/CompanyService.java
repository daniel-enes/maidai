package com.agir.maidai.service;

import com.agir.maidai.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CompanyService extends CrudService<Company, Integer> {

    Page<Company> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
