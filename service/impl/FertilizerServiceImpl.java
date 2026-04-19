package com.ecosystem.backend.service.impl;

import com.ecosystem.backend.entity.Fertilizer;
import com.ecosystem.backend.repository.FertilizerRepository;
import com.ecosystem.backend.service.FertilizerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FertilizerServiceImpl
        implements FertilizerService {

    @Autowired
    FertilizerRepository repo;

    @Override
    public Fertilizer save(
            Fertilizer fertilizer
    ) {
        return repo.save(fertilizer);
    }

    @Override
    public List<Fertilizer> getAll() {
        return repo.findAll();
    }

    @Override
    public Fertilizer getById(
            Long id
    ) {
        return repo.findById(id)
                .orElseThrow();
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}