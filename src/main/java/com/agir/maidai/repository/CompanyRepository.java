package com.agir.maidai.repository;

import com.agir.maidai.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    boolean existsByName(String name);
    Optional<Company> findByName(String name);
}
