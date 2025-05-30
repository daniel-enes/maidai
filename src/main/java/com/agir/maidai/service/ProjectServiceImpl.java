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

    @Override
    public void validateSave(Project project, Errors errors) {

        String trimmedName = project.getName().trim();
        project.setName(trimmedName);

        Optional<Project> projectWithSameName = projectRepository.findByName(trimmedName);

        if(projectWithSameName.isPresent()) {
            if(project.getId() == null || !projectWithSameName.get().getId().equals(project.getId())) {
                errors.rejectValue("name", "name.duplicated","Esse nome já está sendo usado por outro projeto.");
            }
        }

        if(project.getStart() != null && project.getEnd() != null) {
            if(project.getStart().isEqual((project.getEnd()))) {
                errors.rejectValue("end", "dates.equal", "A data final não pode ser igual a data inicial.");
            } else if(project.getEnd().isBefore(project.getStart())) {
                errors.rejectValue("end", ".dates.invalid", "A data final não pode ser anterior à data inicial.");
            }
        }

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
    }
}