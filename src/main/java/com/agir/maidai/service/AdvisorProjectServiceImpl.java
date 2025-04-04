package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.AdvisorProject;
import com.agir.maidai.entity.AdvisorProjectId;
import com.agir.maidai.entity.Project;
import com.agir.maidai.repository.AdvisorProjectRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvisorProjectServiceImpl implements AdvisorProjectService {

    private final AdvisorProjectRepository advisorProjectRepository;
    private final AdvisorService advisorService;
    private final ProjectService projectService;

    public AdvisorProjectServiceImpl(AdvisorProjectRepository advisorProjectRepository,
                                     AdvisorService advisorService,
                                     ProjectService projectService) {
        this.advisorProjectRepository = advisorProjectRepository;
        this.advisorService = advisorService;
        this.projectService = projectService;
    }

    @Transactional
    public List<AdvisorProject> findAll() {
        return advisorProjectRepository.findAll();
    }


    @Transactional
    public AdvisorProject find(AdvisorProjectId id) {
        return advisorProjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AdvisorProject not found with id: " + id));
    }


    @Transactional
    public void create(AdvisorProject entity) {
        validateAdvisorProject(entity);
        advisorProjectRepository.save(entity);
    }


    @Transactional
    public void delete(AdvisorProjectId id) {
        if (!advisorProjectRepository.existsById(id)) {
            throw new EntityNotFoundException("AdvisorProject not found with id: " + id);
        }
        advisorProjectRepository.deleteById(id);
    }

    @Transactional
    public void update(AdvisorProject entity) {
        if (!advisorProjectRepository.existsById(entity.getId())) {
            throw new EntityNotFoundException("AdvisorProject not found with id: " + entity.getId());
        }
        validateAdvisorProject(entity);
        advisorProjectRepository.save(entity);
    }

    // Custom business methods
    @Transactional
    public List<AdvisorProject> findByProject(Project project) {
        return advisorProjectRepository.findByProject(project);
    }

    @Transactional
    public List<AdvisorProject> findByAdvisor(Advisor advisor) {
        return advisorProjectRepository.findByAdvisor(advisor);
    }

    @Transactional
    public void deleteByProjectAndAdvisor(Integer projectId, Integer advisorId) {
        Project project = projectService.find(projectId);
        Advisor advisor = advisorService.find(advisorId);
        advisorProjectRepository.deleteByProjectAndAdvisor(project, advisor);
    }

    @Transactional
    public AdvisorProject createRelationship(Integer projectId, Integer advisorId, Integer coAdvisorId) {
        Project project = projectService.find(projectId);
        Advisor advisor = advisorService.find(advisorId);
        Advisor coAdvisor = (coAdvisorId != null) ? advisorService.find(coAdvisorId) : null;

        // Check if relationship already exists
        if (advisorProjectRepository.existsByProjectAndAdvisor(project, advisor)) {
            throw new IllegalStateException("This advisor is already assigned to the project");
        }

        AdvisorProject relationship = new AdvisorProject();
        relationship.setProject(project);
        relationship.setAdvisor(advisor);
        relationship.setCoAdvisor(coAdvisor);

        return advisorProjectRepository.save(relationship);
    }

    private void validateAdvisorProject(AdvisorProject entity) {
        if (entity.getAdvisor() == null) {
            throw new IllegalArgumentException("Advisor is required");
        }
        if (entity.getProject() == null) {
            throw new IllegalArgumentException("Project is required");
        }
        // Prevent an advisor from being their own co-advisor
        if (entity.getCoAdvisor() != null &&
                entity.getCoAdvisor().getId().equals(entity.getAdvisor().getId())) {
            throw new IllegalArgumentException("An advisor cannot be their own co-advisor");
        }
    }
}