package com.agir.maidai.repository;

import com.agir.maidai.entity.ScholarshipType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScholarshipTypeRepository extends JpaRepository<ScholarshipType, Integer> {

    boolean existsByType(String type);
}
