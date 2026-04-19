package com.ecosystem.backend.service.impl;

import com.ecosystem.backend.entity.Chara;
import com.ecosystem.backend.repository.CharaRepository;
import com.ecosystem.backend.service.CharaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CharaServiceImpl implements CharaService {

    @Autowired
    CharaRepository repository;


    @Override
    public Chara save(Chara c) {
        return repository.save(c);
    }


    @Override
    public List<Chara> getAll() {
        return repository.findAll();
    }


    @Override
    public Chara getById(Long id) {
        return repository
                .findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                "Chara not found"
                        )
                );
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

}