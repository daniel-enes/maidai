package com.agir.maidai.service;

import com.agir.maidai.entity.Advisor;
import com.agir.maidai.entity.Person;
import com.agir.maidai.entity.PersonType;
import com.agir.maidai.repository.AdvisorRepository;
import com.agir.maidai.repository.PersonRepository;
import com.agir.maidai.repository.PersonTypeRepository;
import com.agir.maidai.validation.ValidationResult;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

//    @Override
//    public Page<Person> findAllOrderedByName(Pageable pageable) {
//        return personRepository.findAllByOrderByNameAsc(pageable);
//    }


    @Override
    public Page<Person> findAll(Pageable pageable) {

        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("name").ascending()
            );
        }
        //return personRepository.findAll(pageable);
        return super.findAll(pageable);
    }

    @Override
    @Transactional
    public void create(Person person) {

        super.create(person);

        PersonType personType = person.getPersonType();

        if("orientador".equals(personType.getType())) {
            createAdvisorRecord(person);
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
