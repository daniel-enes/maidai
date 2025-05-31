package com.agir.maidai.service;

import com.agir.maidai.entity.Project;

public interface ProjectService extends CrudService<Project, Integer> {

    void addCoAdvisorToProject(Integer projectId, Integer personId);
    void removeCoAdvisorFromProject(Integer projectId, Integer personId);
}
