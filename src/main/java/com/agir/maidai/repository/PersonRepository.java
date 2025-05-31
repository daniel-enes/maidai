package com.agir.maidai.repository;

import com.agir.maidai.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'bolsista'")
    List<Person> findAllScholarshipHolders();

    @Query("SELECT p FROM Person p WHERE (:typeId IS NULL OR p.personType.id = :typeId)")
    Page<Person> findByPersonTypeId(Integer typeId, Pageable pageable);

    List<Person> findByPersonTypeId(Integer typeId);

    @Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'orientador' ORDER BY p.name ASC")
    List<Person> findAllAdvisors();
}
