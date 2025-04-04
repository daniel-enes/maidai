package com.agir.maidai.repository;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.AdvisorProject;
import com.agir.maidai.entity.AdvisorProjectId;
import com.agir.maidai.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdvisorProjectRepository extends JpaRepository<AdvisorProject, AdvisorProjectId> {

    List<AdvisorProject> findByProject(Project project);
    List<AdvisorProject> findByAdvisor(Advisor advisor);
    void deleteByProjectAndAdvisor(Project project, Advisor advisor);
    boolean existsByProjectAndAdvisor(Project project, Advisor advisor);
}
