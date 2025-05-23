package com.agir.maidai.repository;

import com.agir.maidai.entity.PPG;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PPGRepository extends JpaRepository<PPG, Integer> {

    boolean existsByName(String name);
    boolean existsById(Integer id);
    Optional<PPG> findByName(String name);
}
