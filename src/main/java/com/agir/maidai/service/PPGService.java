package com.agir.maidai.service;

import com.agir.maidai.entity.PPG;

import java.util.List;

public interface PPGService {

    List<PPG> getAll();

    PPG find(int id);

    void create(PPG ppg);

    void delete(int id);
}
