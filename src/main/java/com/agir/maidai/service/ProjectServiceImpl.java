package com.agir.maidai.service;

import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends AbstractCrudService<Project, Integer> implements ProjectService{

    private final ProjectRepository projectRepository;

    @Autowired
    protected ProjectServiceImpl(ProjectRepository projectRepository) {
        super(projectRepository);
        this.projectRepository = projectRepository;
    }

    public void create(Project project) {
        String trimmedName = project.getName().trim();
        project.setName(trimmedName);
        if(projectRepository.existsByName(trimmedName)) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.create(project);
    }

    public void update(Project project) {
        String trimmedName = project.getName().trim();
        project.setName(trimmedName);
        if(projectRepository.existsByName(trimmedName)) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.update(project);
    }
}
