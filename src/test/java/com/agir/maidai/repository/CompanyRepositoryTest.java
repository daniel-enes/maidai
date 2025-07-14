package com.agir.maidai.repository;

import com.agir.maidai.entity.Company;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CompanyRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CompanyRepository companyRepository;

    @Test
    void testFindByName() {

        Company company = new Company(null, "Test Company");
        entityManager.persist(company);
        entityManager.flush();

        Optional<Company> found = companyRepository.findByName(company.getName());

        assertTrue(found.isPresent());
        assertEquals(company.getName(), found.get().getName());
    }

    @Test
    void testFindAllOrderByNameAsc() {

        Company company1 = new Company(null, "B Company");
        Company company2 = new Company(null, "A Company");

        entityManager.persist(company1);
        entityManager.persist(company2);
        entityManager.flush();

        var companies = companyRepository.findAll();
        assertEquals(2, companies.size());
        assertEquals("A Company", companies.get(0).getName());
        assertEquals("B Company", companies.get(1).getName());
    }
}
