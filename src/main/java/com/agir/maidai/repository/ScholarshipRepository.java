package com.agir.maidai.repository;

import com.agir.maidai.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Integer> {

    Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable);

    @Query("SELECT s FROM Scholarship s WHERE s.status = :status ORDER BY s.person.name ASC")
    Page<Scholarship> findByStatus(Pageable pageable, String status);

    @Query("SELECT s FROM Scholarship s " +
            "WHERE s.person.name LIKE CONCAT('%', :name, '%')" +
            "ORDER BY s.person.name ASC")
    Page<Scholarship> findByScholarshipHolder(Pageable pageable, String name);

    @Query("SELECT s FROM Scholarship s " +
            "WHERE s.project.advisor.name LIKE CONCAT('%', :name, '%')" +
            "ORDER BY s.project.advisor.name ASC")
    Page<Scholarship> findByAdvisor(Pageable pageable, String name);

    @Query("SELECT s FROM Scholarship s " +
            "WHERE s.project.name LIKE CONCAT('%', :name, '%')" +
            "ORDER BY s.project.name ASC")
    Page<Scholarship> findByProject(Pageable pageable, String name);

    @Query("SELECT s FROM Scholarship s " +
            "WHERE s.project.company.name LIKE CONCAT('%', :name, '%')" +
            "ORDER BY s.project.company.name ASC")
    Page<Scholarship> findByCompany(Pageable pageable, String name);
}
