package com.agir.maidai.repository;

import com.agir.maidai.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p JOIN p.personType pt WHERE pt.type = 'bolsista'")
    List<Person> findAllScholarshipHolders();

    //Page<Person> findAllByOrderByNameAsc(Pageable pageable);
}
