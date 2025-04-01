package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.repository.AdvisorRepository;
import com.agir.maidai.repository.PPGRepository;
import com.agir.maidai.repository.PersonRepository;
import com.agir.maidai.repository.PersonTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AdvisorServiceImpl {

    private final PersonRepository personRepository;
    private final PPGRepository ppgRepository;
    private final AdvisorRepository advisorRepository;
    private final PersonTypeRepository personTypeRepository;


    public AdvisorServiceImpl(PersonRepository personRepository,
                              PPGRepository ppgRepository,
                              AdvisorRepository advisorRepository,
                              PersonTypeRepository personTypeRepository) {
        this.personRepository = personRepository;
        this.ppgRepository = ppgRepository;
        this.advisorRepository = advisorRepository;
        this.personTypeRepository = personTypeRepository;
    }

    @Transactional
    public void assignPersonToPPG(Integer personId, Integer ppgId) {
        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new EntityNotFoundException("Person not found"));

        PPG ppg = ppgRepository.findById(ppgId)
                .orElseThrow(() -> new EntityNotFoundException("PPG not found"));

        //validatePersonIsOrientador(person);

        /*
        // Remove existing advisor relationship if exists
        if (person.getAdvisor() != null) {
            advisorRepository.delete(person.getAdvisor());
        }*/

        Advisor advisor = new Advisor(person, ppg);
        person.setAdvisor(advisor);
        advisorRepository.save(advisor);
    }

    /*private void validatePersonIsOrientador(Person person) {
        PersonType adviserType = personTypeRepository.findByNameIgnoreCase("orientador")
                .orElseThrow(() -> new IllegalStateException("Pessoa do tipo 'orientador' não foi encontrado."));

        if (!adviserType.equals(person.getPersonType())) {
            throw new IllegalArgumentException("Apenas pessoas assinadas como 'orientador' podem estar em um PPG");
        }
    }*/
}
