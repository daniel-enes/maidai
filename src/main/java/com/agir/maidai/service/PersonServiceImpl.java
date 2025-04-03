package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.repository.AdvisorRepository;
import com.agir.maidai.repository.PersonRepository;
import com.agir.maidai.repository.PersonTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl extends AbstractCrudService<Person, Integer> implements PersonService{

    private final PersonRepository personRepository;
    private final AdvisorRepository advisorRepository;
    private final PersonTypeRepository personTypeRepository;

    @Autowired
    protected PersonServiceImpl(PersonRepository personRepository, AdvisorRepository advisorRepository, PersonTypeRepository personTypeRepository) {
        super(personRepository);
        this.personRepository = personRepository;
        this.advisorRepository = advisorRepository;
        this.personTypeRepository = personTypeRepository;
    }

    @Override
    @Transactional
    public void create(Person person) {

        validatePersonType(person);
        super.create(person);

        PersonType personType = person.getPersonType();

        if("orientador".equals(personType.getType())) {
            createAdvisorRecord(person);
        }
    }

    /*@Override
    @Transactional
    public void update(Person person) {

        super.update(person);

        PersonType personType = person.getPersonType();

        if("orientador".equals(personType.getType())) {
            createAdvisorRecord(person);
        }
    }*/

    private void validatePersonType(Person person) {
        if (person.getPersonType() == null) {
            throw new IllegalArgumentException("É necessário definir o tipo de pessoa");
        }
    }

    private void createAdvisorRecord(Person person) {

        Advisor advisor = new Advisor();
        advisor.setPerson(person);
        advisorRepository.save(advisor);

        // Set the bidirectional relationship
        person.setAdvisor(advisor);
    }

}
