package com.ecosystem.backend.service;

import com.ecosystem.backend.entity.Fertilizer;

import java.util.List;

public interface FertilizerService {

    Fertilizer save(Fertilizer fertilizer);

    List<Fertilizer> getAll();

    Fertilizer getById(Long id);

    void delete(Long id);


}