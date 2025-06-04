package com.agir.maidai.repository;

import com.agir.maidai.entity.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Optional<Project> findByName(String name);

    Page<Project> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT p FROM Project p ORDER BY p.name ASC")
    List<Project> findAll();

    @Query("SELECT p FROM Project p WHERE p.yearNotice = :yearNotice")
    Page<Project> findByYearNotice(String yearNotice, Pageable pageable);

    Page<Project> findByYearNoticeIsNull(Pageable page);

    @Query("SELECT DISTINCT p.yearNotice FROM Project p WHERE p.yearNotice IS NOT NULL ORDER BY p.yearNotice")
    List<String> findDistinctByYearNotice();

}
