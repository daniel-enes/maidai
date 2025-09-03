package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService extends CrudService<Project, Integer> {

    void addCoAdvisorToProject(Integer projectId, Integer personId);
    void removeCoAdvisorFromProject(Integer projectId, Integer personId);

    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Project> findByYearNotice(String yearNotice, Pageable pageable);

    List<String> findDistinctByYearNotice();

    Page<Project> findByYearNoticeIsNull(Pageable page);
}
