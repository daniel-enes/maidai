package com.agir.maidai.repository;

import com.agir.maidai.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    //boolean existsByName(String name);

    Optional<Project> findByName(String name);

    @Query("SELECT p FROM Project p ORDER BY p.name ASC")
    List<Project> findAll();
}
