package com.agir.maidai.repository;

import com.agir.maidai.entity.PPG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PPGRepository extends JpaRepository<PPG, Integer> {

    //boolean existsByName(String name);
    boolean existsById(Integer id);
    Optional<PPG> findByName(String name);

    @Query("SELECT p FROM PPG p ORDER BY p.name ASC")
    List<PPG> findAll();
}
