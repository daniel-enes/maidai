package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.Company;
import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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

    public void validateSave(Project project, BindingResult bindingResult) {

        String trimmedName = project.getName().trim();
        project.setName(trimmedName);

        if(project.getName().isEmpty()) {
            bindingResult.rejectValue("name", "required", "Defina o nome do projeto, não o deixe em branco.");
            //throw new IllegalArgumentException("Defina o nome do projeto, não o deixe em branco.");
        }

        Optional<Project> projectWithSameName = projectRepository.findByName(trimmedName);

        if(projectWithSameName.isPresent()) {
            if(project.getId() == null || !projectWithSameName.get().getId().equals(project.getId())) {
                bindingResult.rejectValue("name", "duplicate", "Defina a data para quando a projeto inicia.");
                //throw new IllegalArgumentException("Esse nome já está sendo usado por outro projeto.");
            }
        }

        if(project.getStart() == null) {
            bindingResult.rejectValue("start", "required", "Defina a data para quando a projeto inicia.");
            //throw new IllegalArgumentException("É necessário definir a data que o projeto inicia.");
        }

        if (project.getEnd() == null) {
            bindingResult.rejectValue("end", "required", "É necessário definir a data que o projeto termina.");
            //throw new IllegalArgumentException("É necessário definir a data que o projeto termina.");
        }

        if (project.getEnd() != null &&
                project.getStart() !=null &&
                project.getEnd().isBefore(project.getStart())) {
            bindingResult.rejectValue("end", "invalid", "A data de término não pode ser anterior à data de início.");
            //throw new IllegalArgumentException("A data que termina não deve ser anterior a data que inicia.");
        }

        if (project.getEnd() != null &&
                project.getStart() !=null &&
                project.getEnd().equals(project.getStart())) {
            bindingResult.rejectValue("end", "invalid", "A data de término não pode ser igual à data de início.");
            //throw new IllegalArgumentException("A data que termina não deve ser igual a data que inicia.");
        }

        Company company = project.getCompany();
        if(company == null) {
            bindingResult.rejectValue("company", "required", "Selecione uma empresa para o projeto.");
            //throw new IllegalArgumentException("É necessário definir uma empresa para o projeto.");
        }

        Integer advisorId = project.getAdvisorId();
        Advisor advisor = advisorService.find(advisorId);

        if(advisorId == null) {
            bindingResult.rejectValue("advisorId", "required", "Defina um orientador para o projeto.");
            //throw new IllegalArgumentException("É necessário definir um orientador para o projeto.");
        }

        if(advisor == null) {
            bindingResult.rejectValue("advisorId", "invalid", "Orientador selecionado não encontrado.");
            //throw new IllegalArgumentException("É necessário definir um orientador para o projeto.");
        } else {
            project.setAdvisor(advisor);
        }

        Integer coAdvisorId = project.getCoAdvisorId();

        if(coAdvisorId != null) {
            if(coAdvisorId != advisorId) {
                Advisor coAdvisor = advisorService.find(project.getCoAdvisorId());
                project.setCoAdvisor(coAdvisor);
            } else {
                bindingResult.rejectValue("coAdvisorId", "invalid", "O coorientador deve ser diferente do orientador.");
                //throw new IllegalArgumentException("Defina um 'Coorientador' diferente do 'Orientador'.");
            }
        }
    }
}
