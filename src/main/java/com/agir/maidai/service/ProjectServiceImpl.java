package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.Optional;

@Service
public class ProjectServiceImpl extends AbstractCrudService<Project, Integer> implements ProjectService{

    private final ProjectRepository projectRepository;
    private final PersonService personService;

    @Autowired
    protected ProjectServiceImpl(ProjectRepository projectRepository, PersonService personService) {
        super(projectRepository);
        this.projectRepository = projectRepository;
        this.personService = personService;
    }

    @Transactional
    public void addCoAdvisorToProject(Integer projectId, Integer personId) {
        Project project = find(projectId);
        Person coAdvisor = personService.find(personId);

        if (!project.getCoAdvisors().contains(coAdvisor)) {
            project.addCoAdvisor(coAdvisor);
            repository.save(project);
        }
    }

    @Transactional
    public void removeCoAdvisorFromProject(Integer projectId, Integer personId) {
        Project project = find(projectId);
        Person coAdvisor = personService.find(personId);
        project.removeCoAdvisor(coAdvisor);
        repository.save(project);
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

//            if(coAdvisorId != null) {
//                if(coAdvisorId != advisorId) {
//                    Advisor coAdvisor = advisorService.find(project.getCoAdvisorId());
//                    project.setCoAdvisor(coAdvisor);
//                } else {
//                    errors.rejectValue("coAdvisorId","coAdvisorId.equals","Defina um 'Coorientador' diferente do 'Orientador'.");
//                }
//            }
//        }
    }
}