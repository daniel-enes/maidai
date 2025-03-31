package com.agir.maidai.service;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.repository.PPGRepository;
import org.springframework.stereotype.Service;

@Service
public class PPGServiceImpl extends AbstractCrudService<PPG, Integer> implements PPGService{

    private final PPGRepository ppgRepository;

    public PPGServiceImpl(PPGRepository ppgRepository) {
        super(ppgRepository);
        this.ppgRepository = ppgRepository;
    }

    public void create(PPG ppg) {
        if(ppgRepository.existsByName(ppg.getName())) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.create(ppg);
    }

    public void update(PPG ppg) {
        if(ppgRepository.existsByName(ppg.getName())) {
            throw new IllegalArgumentException("Esse nome já existe. Tente usar outro.");
        }
        super.update(ppg);
    }
}
