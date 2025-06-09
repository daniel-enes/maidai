package com.agir.maidai.repository;

import com.agir.maidai.entity.Scholarship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScholarshipRepository extends JpaRepository<Scholarship, Integer> {

    Page<Scholarship> findAllByOrderByPersonNameAsc(Pageable pageable);

    @Query("SELECT s FROM Scholarship s WHERE status = :status ORDER BY s.person.name ASC")
    Page<Scholarship> findByStatus(Pageable pageable, String status);

    //@Query ("SELECT s FROM Scholarship s WHERE (s.person.name = :name AND s.person.personType = '2') ORDER BY s.person.name ASC")
    @Query ("SELECT s FROM Scholarship s WHERE s.person.name = :name ORDER BY s.person.name ASC")
    Page<Scholarship> findByScholarshipHolder(Pageable pageable, String name);
}
