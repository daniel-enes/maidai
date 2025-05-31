//package com.agir.maidai.service;
//
//import com.agir.maidai.entity.Advisor;
//import com.agir.maidai.entity.PPG;
//import com.agir.maidai.entity.Person;
//import com.agir.maidai.entity.PersonType;
//import com.agir.maidai.repository.AdvisorRepository;
//import com.agir.maidai.repository.PPGRepository;
//import com.agir.maidai.repository.PersonRepository;
//import com.agir.maidai.repository.PersonTypeRepository;
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.transaction.Transactional;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.validation.Errors;
//
//import java.util.List;
//
//@Service
//public class AdvisorServiceImpl implements AdvisorService {
//
//    private final PersonRepository personRepository;
//    private final PPGRepository ppgRepository;
//    private final AdvisorRepository advisorRepository;
//    private final PersonTypeRepository personTypeRepository;
//
//
//    public AdvisorServiceImpl(PersonRepository personRepository,
//                              PPGRepository ppgRepository,
//                              AdvisorRepository advisorRepository,
//                              PersonTypeRepository personTypeRepository) {
//        this.personRepository = personRepository;
//        this.ppgRepository = ppgRepository;
//        this.advisorRepository = advisorRepository;
//        this.personTypeRepository = personTypeRepository;
//    }
//
//    private void validatePersonIsAdvisor(Person person) {
//        PersonType personType = person.getPersonType();
//        if(!"orientador".equals(personType.getType())) {
//            throw new IllegalArgumentException("Apenas pessoas assinadas como 'orientador' podem estar em um PPG");
//        }
//    }
//
//    @Override
//    public List<Advisor> findAll() {
//        return advisorRepository.findAll();
//    }
//
//    @Override
//    public Page<Advisor> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Advisor find(Integer integer) {
//        return advisorRepository.findById(integer).orElseThrow(() -> new EntityNotFoundException("ID não encontrado: " + integer));
//    }
//
//    @Override
//    public void create(Advisor entity) {
//        advisorRepository.save(entity);
//    }
//
//    @Override
//    public void delete(Integer integer) {
//        advisorRepository.deleteById(integer);
//    }
//
//    @Override
//    @Transactional
//    public void update(Advisor entity) {
//
//        Advisor existingAdvisor = advisorRepository.findById(entity.getId())
//                .orElseThrow(() -> new EntityNotFoundException("Advisor not found with id: " + entity.getId()));
//
//        if (entity.getPpg() != null) {
//
//            PPG ppg = ppgRepository.findById(entity.getPpg().getId())
//                    .orElseThrow(() -> new EntityNotFoundException("PPG not found"));
//            existingAdvisor.setPpg(ppg);
//        }
//
//        advisorRepository.save(existingAdvisor);
//
//    }
//
//    @Override
//    public void validateSave(Advisor entity, Errors errors) {
//
//    }
//}
