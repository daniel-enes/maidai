package com.agir.maidai.repository;

import com.agir.maidai.entity.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonTypeRepository extends JpaRepository<PersonType, Integer> {

    /*@Query("SELECT pt FROM PersonType pt WHERE LOWER(pt.name) = LOWER(:name)")
    Optional<PersonType> findByNameIgnoreCase(@Param("name") String name);*/
}
