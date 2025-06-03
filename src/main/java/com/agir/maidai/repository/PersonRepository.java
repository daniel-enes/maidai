package com.agir.maidai.repository;

import com.agir.maidai.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    Page<Person> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE (:typeId IS NULL OR p.personType.id = :typeId)")
    Page<Person> findByPersonType(Integer typeId, Pageable pageable);

    @Query("SELECT p FROM Person p WHERE (:typeId IS NULL OR p.personType.id = :typeId)")
    List<Person> findByPersonType(Integer typeId);

    //List<Person> findByPersonTypeId(Integer typeId);

    @Query("SELECT p FROM Person p ORDER BY p.name ASC")
    List<Person> findAll();

    //@Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'bolsista'")
    @Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'bolsista' ORDER BY p.name ASC")
    List<Person> findAllScholarshipHolders();

    @Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'orientador' ORDER BY p.name ASC")
    List<Person> findAllAdvisors();
}
