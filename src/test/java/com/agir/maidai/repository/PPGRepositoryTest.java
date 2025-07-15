package com.agir.maidai.repository;

import com.agir.maidai.entity.PPG;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class PPGRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PPGRepository ppgRepository;

    @Test
    void testPpgFindByName() {

        PPG ppg = new PPG(null, "Teste PPG");
        entityManager.persist(ppg);
        entityManager.flush();

        Optional<PPG> foundPpg = ppgRepository.findByName(ppg.getName());

        assertTrue(foundPpg.isPresent());
        assertEquals(ppg.getName(), foundPpg.get().getName());
    }

    @Test
    void testPpgFindAllOrderByNameAsc() {

        PPG ppg1 = new PPG(null, "B PPG");
        PPG ppg2 = new PPG(null, "A PPG");

        entityManager.persist(ppg1);
        entityManager.persist(ppg2);
        entityManager.flush();

        var ppgList = ppgRepository.findAll();

        assertEquals(2, ppgList.size());
        assertEquals("A PPG", ppgList.get(0).getName());
        assertEquals("B PPG", ppgList.get(1).getName());
    }
}
