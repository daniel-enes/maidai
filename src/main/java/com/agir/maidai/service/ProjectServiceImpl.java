package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.Company;
import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class ProjectServiceImpl extends AbstractCrudService<Project, Integer> implements ProjectService{

    private final ProjectRepository projectRepository;
    private final AdvisorServiceImpl advisorService;

    @Autowired
    protected ProjectServiceImpl(ProjectRepository projectRepository, AdvisorServiceImpl advisorService) {
        super(projectRepository);
        this.projectRepository = projectRepository;
        this.advisorService = advisorService;
    }

    public void create(Project project) {
        //validateSave(project);
        super.create(project);
    }

    public void update(Project project) {
        //validateSave(project);
        super.update(project);
    }

    @Override
    public Errors validateSave(Project project, Errors errors) {

String trimmedName = project.getName().trim();
project.setName(trimmedName);
//
//        if(project.getName().isEmpty()) {
//            throw new IllegalArgumentException("Defina o nome do projeto, não o deixe em branco.");
//        }

        Optional<Project> projectWithSameName = projectRepository.findByName(trimmedName);

        if(projectWithSameName.isPresent()) {
            if(project.getId() == null || !projectWithSameName.get().getId().equals(project.getId())) {
                errors.rejectValue("name", "name.duplicated","Esse nome já está sendo usado por outro projeto.");
            }
        }

        /*
        if(projectRepository.existsByName(trimmedName)) {
            if(project.getId() == null) {
                throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
            }
        }
        */

//        if(project.getStart() == null) {
//            throw new IllegalArgumentException("É necessário definir a data que o projeto inicia.");
//        }
//
//        if (project.getEnd() == null) {
//            throw new IllegalArgumentException("É necessário definir a data que o projeto termina.");
//        }
//
//        if (project.getEnd().isBefore(project.getStart())) {
//            throw new IllegalArgumentException("A data que termina não deve ser anterior a data que inicia.");
//        }
//
//        if (project.getEnd().equals(project.getStart())) {
//            throw new IllegalArgumentException("A data que termina não deve ser igual a data que inicia.");
//        }
//
//        Company company = project.getCompany();
//        if(company == null) {
//            throw new IllegalArgumentException("É necessário definir uma empresa para o projeto.");
//        }

        //if(advisor == null) {
        if(project.getAdvisorId() == null) {
            errors.rejectValue("advisorId","advisorId.notnull","É necessário definir um orientador para o projeto.");
        } else {
            Integer advisorId = project.getAdvisorId();
            Advisor advisor = advisorService.find(advisorId);
            project.setAdvisor(advisor);

            Integer coAdvisorId = project.getCoAdvisorId();

            if(coAdvisorId != null) {
                if(coAdvisorId != advisorId) {
                    Advisor coAdvisor = advisorService.find(project.getCoAdvisorId());
                    project.setCoAdvisor(coAdvisor);
                } else {
                    errors.rejectValue("coAdvisorId","coAdvisorId.equals","Defina um 'Coorientador' diferente do 'Orientador'.");
                }
            }
        }

        return errors;
    }
}