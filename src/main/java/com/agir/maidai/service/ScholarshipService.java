package com.agir.maidai.service;

import com.agir.maidai.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ScholarshipService extends CrudService<Scholarship, Integer>{

    Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable);
}
