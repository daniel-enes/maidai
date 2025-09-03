package com.agir.maidai.repository;

import com.agir.maidai.entity.ScholarshipType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ScholarshipTypeRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ScholarshipTypeRepository scholarshipTypeRepository;

    @Test
    void testScholarshipTypeFindByType() {

        ScholarshipType scholarshipType = new ScholarshipType(null, "MAI");
        entityManager.persist(scholarshipType);
        entityManager.flush();

        Optional<ScholarshipType> foundScholarshipType = scholarshipTypeRepository.findByType(scholarshipType.getType());

        assertTrue(foundScholarshipType.isPresent());
        assertEquals(scholarshipType.getType(), foundScholarshipType.get().getType());
    }

    @Test
    void testScholarshipTypeFindAll() {

        ScholarshipType scholarshipType1 = new ScholarshipType(null, "MAI");
        ScholarshipType scholarshipType2 = new ScholarshipType(null, "DAI");
        entityManager.persist(scholarshipType1);
        entityManager.persist(scholarshipType2);
        entityManager.flush();

        var scholarshipTypeList = scholarshipTypeRepository.findAll();

        assertEquals(2, scholarshipTypeList.size());
        assertEquals("DAI", scholarshipTypeList.get(0).getType());
        assertEquals("MAI", scholarshipTypeList.get(1).getType());
    }
}
