package com.agir.maidai.repository;


import com.agir.maidai.entity.ScholarshipType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ScholarshipTypeRepository extends JpaRepository<ScholarshipType, Integer> {

    Optional<ScholarshipType> findByType(String type);

    @Query("SELECT s FROM ScholarshipType s ORDER BY s.type ASC")
    List<ScholarshipType> findAll();
}
