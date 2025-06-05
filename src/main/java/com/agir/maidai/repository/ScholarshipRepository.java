package com.agir.maidai.repository;

import com.agir.maidai.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Integer> {

    Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable);
}
