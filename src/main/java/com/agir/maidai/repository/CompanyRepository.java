package com.agir.maidai.repository;

import com.agir.maidai.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    //boolean existsByName(String name);
    Optional<Company> findByName(String name);

    @Query("SELECT c FROM Company c ORDER BY c.name ASC")
    List<Company> findAll();
}
