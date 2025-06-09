package com.agir.maidai.service;

import com.agir.maidai.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScholarshipService extends CrudService<Scholarship, Integer>{

    Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable);

    Page<Scholarship> findByStatus(Pageable pageable, String status);

    Page<Scholarship> findByScholarshipHolder(Pageable pageable, String name);
}
