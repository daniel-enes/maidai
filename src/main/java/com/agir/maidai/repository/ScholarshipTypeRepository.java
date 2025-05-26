package com.agir.maidai.repository;

import com.agir.maidai.entity.ScholarshipType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScholarshipTypeRepository extends JpaRepository<ScholarshipType, Integer> {

    Optional<ScholarshipType> findByType(String type);
}
