package com.agir.maidai.controller;

import com.agir.maidai.entity.PPG;
import com.agir.maidai.service.PPGService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ppgs")
public class PPGController extends AbstractCrudController<PPG, Integer> implements CrudController<PPG, Integer>{

    public PPGController(PPGService ppgService) {
        super(ppgService, "ppg", "ppgs");
    }

    @Override
    protected PPG createNewEntity() {
        return new PPG();
    }

    @Override
    protected String getCreateSuccessMessage() {
        return "Programa de Pós-graduação criado com sucesso.";
    }

    @Override
    protected String getUpdateSuccessMessage() {

        return "Programa de Pós-graduação editado com sucesso.";
    }

    @Override
    protected String getDeleteSuccessMessage() {

        return "Programa de Pós-graduação removido";
    }
}
