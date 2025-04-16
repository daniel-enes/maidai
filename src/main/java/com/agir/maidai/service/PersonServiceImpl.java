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

import java.util.List;
import java.util.stream.Collectors;

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

        validateSave(person);
        super.create(person);

        PersonType personType = person.getPersonType();

        if("orientador".equals(personType.getType())) {
            createAdvisorRecord(person);
        }

    }

    @Override
    @Transactional
    public void update(Person person) {

        validateSave(person);
        super.update(person);

    }

    private void validateSave(Person person) {

        String trimmedName = person.getName().trim();
        person.setName(trimmedName);

        if(person.getName().isEmpty()) {
            throw new IllegalArgumentException("Nome não pode estar em branco.");
        }

        if(person.getPhone() != null) {
            String trimmedNumber = person.getPhone().trim();
            person.setPhone(trimmedNumber);
            if (!person.getPhone().matches("\\d+")) {
                throw new IllegalArgumentException("O telefone deve conter apenas números.");
            }
        }

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

    @Override
    public List<Person> findAllScholarshipHolders() {
        return personRepository.findAllScholarshipHolders();
    }
}
