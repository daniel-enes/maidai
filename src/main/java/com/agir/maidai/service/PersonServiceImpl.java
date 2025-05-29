package com.agir.maidai.service;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.entity.Person;
import com.agir.maidai.repository.PPGRepository;
import com.agir.maidai.repository.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl extends AbstractCrudService<Person, Integer> implements PersonService{

    private final PersonRepository personRepository;
    private final PPGRepository ppgRepository;

    @Autowired
    protected PersonServiceImpl(PersonRepository personRepository, PPGRepository ppgRepository) {
        super(personRepository);
        this.personRepository = personRepository;
        this.ppgRepository = ppgRepository;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {

        if(pageable.getSort().isUnsorted()) {
            pageable = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("name").ascending()
            );
        }

        return super.findAll(pageable);
    }

    @Override
    public Page<Person> findByPersonTypeId(Integer typeId, Pageable pageable) {
        return personRepository.findByPersonTypeId(typeId, pageable);
    }

    @Transactional
    public void addPersonToPpg(Integer personId, Integer ppgId) {
        Person person = find(personId);
        PPG ppg = ppgRepository.findById(ppgId)
                .orElseThrow(() -> new EntityNotFoundException("PPG não encontrado"));
        person.addPpg(ppg);
        repository.save(person);
    }

    @Transactional
    public void removePersonFromPpg(Integer personId, Integer ppgId) {
        Person person = find(personId);
        PPG ppg = ppgRepository.findById(ppgId)
                .orElseThrow(() -> new EntityNotFoundException("PPG não encontrado"));
        person.removePpg(ppg);
        repository.save(person);
    }

//    @Override
//    @Transactional
//    public void create(Person person) {
//
//        super.create(person);
//
//        PersonType personType = person.getPersonType();
//
//        if("orientador".equals(personType.getType())) {
//            createAdvisorRecord(person);
//        }
//
//    }

//    private void createAdvisorRecord(Person person) {
//
//        Advisor advisor = new Advisor();
//        advisor.setPerson(person);
//        advisorRepository.save(advisor);
//
//        // Set the bidirectional relationship
//        person.setAdvisor(advisor);
//    }

    @Override
    public List<Person> findAllScholarshipHolders() {
        return personRepository.findAllScholarshipHolders();
    }
}
