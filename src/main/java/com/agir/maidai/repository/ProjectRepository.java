package com.agir.maidai.repository;

import com.agir.maidai.entity.Company;
import com.agir.maidai.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    boolean existsByName(String name);

    Optional<Project> findByName(String name);
}
