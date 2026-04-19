package com.ecosystem.backend.service;

import com.ecosystem.backend.entity.Chara;

import java.util.List;

public interface CharaService {

    Chara save(Chara c);

    List<Chara> getAll();

    Chara getById(Long id);

    void delete(Long id);

}