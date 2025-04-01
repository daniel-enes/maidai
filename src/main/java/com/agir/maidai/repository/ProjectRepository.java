package com.agir.maidai.repository;

import com.agir.maidai.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByName(String name);
}
