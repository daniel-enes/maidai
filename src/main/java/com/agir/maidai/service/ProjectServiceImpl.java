package com.agir.maidai.service;

import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.ProjectRepository;
import com.agir.maidai.validation.ProjectsPeopleRelationshipsException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import java.util.*;

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

    @Override
    public Page<Project> findAll(Pageable pageable, Map<String, String> parameters) {
        // Verify if it's filtered by status
        if(parameters.containsKey("name")) {
            String name = parameters.get("name");
            return this.findByNameContainingIgnoreCase(name, pageable);
        } else if(parameters.containsKey("yearNotice")) {
            String yearNotice = parameters.get("yearNotice");
            return this.findByYearNotice(yearNotice, pageable);
        } else if(parameters.containsKey("filter") && "null".equals(parameters.get("filter"))) {
            return this.findByYearNoticeIsNull(pageable);
        }else {
            return null;
        }
    }

    @Transactional
    public void addCoAdvisorToProject(Integer projectId, Integer personId) {
        Project project = find(projectId);
        Person coAdvisor = personService.find(personId);

        if (!project.getCoAdvisors().contains(coAdvisor)) {
            if (!project.getAdvisor().getId().equals(coAdvisor.getId())) {
                project.addCoAdvisor(coAdvisor);
                repository.save(project);
            } else {
                throw new ProjectsPeopleRelationshipsException("O orientador não pode ser também o coorientador do projeto");
            }
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
    public Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return projectRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Page<Project> findByYearNotice(String yearNotice, Pageable pageable) {
        return projectRepository.findByYearNotice(yearNotice, pageable);
    }

    @Override
    public List<String> findDistinctByYearNotice() {
        return projectRepository.findDistinctByYearNotice();
    }

    @Override
    public Page<Project> findByYearNoticeIsNull(Pageable pageable) {
        return projectRepository.findByYearNoticeIsNull(pageable);
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

        if(project.getAdvisor() != null && !project.getCoAdvisors().isEmpty()) {
            // Check if advisor is in co-advisors list
            if(project.getCoAdvisors().contains(project.getAdvisor())) {
                errors.rejectValue("coAdvisors", "coAdvisor.duplicate","O orientador não pode ser também coorientador do mesmo projeto.");
            }
            // Check for duplicate co-advisors
            Set<Person> uniqueCoAdvisors = new HashSet<>(project.getCoAdvisors());
            if(uniqueCoAdvisors.size() < project.getCoAdvisors().size()) {
                errors.rejectValue("coAdvisors", "coAdvisor.duplicates",
                        "Há coorientadores duplicados na lista.");
            }
        }
    }
}